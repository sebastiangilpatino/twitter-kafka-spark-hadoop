package com.miu.BDTProjectConsumer;

import java.util.HashMap;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

import scala.Tuple2;

public class BdtProjectConsumerApplication {

	public static void main(String[] args) {
		String ZOOKEEPER = "localhost:2181";
		String KAFKA_SERVER = "localhost:9092";
		String TOPIC1_NAME = "twitter";
		String REGEX = "[^\\x00-\\x7E]|\\r|\\n|\\r\\n";
		Integer SAMPLE_TIME = 10000;

		SparkConf sparkConf = new SparkConf().setAppName("Twitter").setMaster(
				"local[2]");
		JavaSparkContext javaSparkContext = JavaSparkContext
				.fromSparkContext(SparkContext.getOrCreate(sparkConf));
		JavaStreamingContext javaStreamingContext = new JavaStreamingContext(
				javaSparkContext, Durations.milliseconds(SAMPLE_TIME));

		Map<String, String> kafkaParams = new HashMap<>();
		kafkaParams.put("zookeeper.connect", ZOOKEEPER);
		kafkaParams.put("group.id", "group1");
		kafkaParams.put("auto.offset.reset", "smallest");
		kafkaParams.put("metadata.broker.list", KAFKA_SERVER);
		kafkaParams.put("bootstrap.servers", KAFKA_SERVER);
		Map<String, Integer> topicsMap = new HashMap<String, Integer>();
		topicsMap.put(TOPIC1_NAME, 1);
	
		JavaDStream<Tuple2<String, String>> javaStream = KafkaUtils
				.createStream(javaStreamingContext, ZOOKEEPER, TOPIC1_NAME,
						topicsMap).toJavaDStream();

		javaStream.foreachRDD((rdd, time) -> {
			JavaRDD<String> plainRDD = rdd.coalesce(1)
					.map(tuple -> (String) tuple._2())
					.map(s -> s.replaceAll(REGEX, " "));
			plainRDD.saveAsTextFile("hdfs://quickstart.cloudera:8020/user/cloudera/twitter/"
					+ System.currentTimeMillis());
		});
		javaStream.print();
		javaStreamingContext.start();
		javaStreamingContext.awaitTermination();
	}
}