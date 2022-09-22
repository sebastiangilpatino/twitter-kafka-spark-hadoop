# BDT CS523 TWITTER -> KAFKA -> SPARK -> HDFS
## Jorge Sebastian Gil Patino. ID: 612422
### 9/22/22

![Pipeline](https://github.com/sebastiangilpatino/twitter-kafka-spark-hadoop/blob/master/Pipeline.jpg)

This project is to show the knowdledge acquire in the Big Data course in the Maharishi International University. The project consist in two JAR file.

1. Consumer from Twitter and Producer to Kafka topic
2. Consumer of Kafka Topic and Producer to HDFS
3. Import all the data to HIVE table and do analysis

The tech stack was:
1. JAVA 11
2. SpringBoot 2.6
3. Spark core 2.11
4. HIVE CLI

## 1. How to run
### 1.1 SetUp
- Download KAFKA from https://downloads.apache.org/kafka/3.2.3/kafka_2.12-3.2.3.tgz 
- Unzip the files in a place that you will have access
- if you are on Windows use the Windows folder and the .bat files
- if you are on Linux use the default folder and the .sh files
- cd to your kafka folder (eg: cd local/usr/kafka)
- to run KAFKA in your local you must be a super user.
- run the Zookeeper (by default localhost:2181) in Cloudera you dont need to do this step.
- run the Kafka server bin/kafka-server-start.sh config/server.properties (by default localhost:9092)
- create the topic bin/kafka-topics.sh --create --topic twitter --bootstrap-server localhost:\
 
### 1.1 Consumer Twitter - Producer Kafka topic
- This is a java application (DEMO) that is setup to use only in your local if in the future you want to use in a real kafka server the hardcode constants should be move to VM arguments
- Run the jar file  (java -jar BDTProducer.jar)
- You will see in the console the tweets that is coming from the streaming source

### 1.2 Consumer Kafka topic - Spark Kafka Consumer - Spark Producer to HDFS
- This is a java application (DEMO) that is setup to use only in your local if in the future you want to use in a real kafka server the hardcode constants should be move to VM arguments
- Run the jar file  (java -jar BDTConsumer.jar)
- You will see in the console the tweets that is coming from the kafka topic 
### 1.3 Consumer Kafka topic - Spark Kafka Consumer - Spark Producer to HDFS
- This is a java application (DEMO) that is setup to use only in your local if in the future you want to use in a real kafka server the hardcode constants should be move to VM arguments
- Run the jar file  (java -jar BDTConsumer.jar)
- You will see in the console the tweets that is coming from the kafka topic 
### 1.4 HDFS
- The information will be save on: hdfs://quickstart.cloudera:8020/user/cloudera/twitter/
- in (hdfs dfs -ls /twitter) you will be able to see the files the consumer pushed to hdfs
- then we will analyze the data using hive
-     [create table] - CREATE  EXTERNAL  TABLE tweet ( value STRING )
-     [load data] - LOAD DATA INPATH '/user/cloudera/twitter/1663702230013/part-00000' OVERWRITE INTO TABLE tweet;
-     [query 1] - SELECT b.text FROM tweet t LATERAL VIEW json_tuple(t.value, 'id', 'text') b AS id, text WHERE b.id='1572307197765341189';
-     [query 2] - SELECT b.text FROM tweet t LATERAL VIEW json_tuple(t.value, 'id', 'text') b AS id, text WHERE b.text LIKE concat('%', RT'%');
