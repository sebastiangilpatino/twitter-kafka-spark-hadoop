# BDT CS523 TWITTER -> KAFKA -> SPARK -> HDFS
## Jorge Sebastian Gil Patino. ID: 612422
### 9/22/22
![Pipeline](https://github.com/sebastiangilpatino/twitter-kafka-spark-hadoop/blob/master/Pipeline.jpg)  
Vide link : https://mum0-my.sharepoint.com/:v:/g/personal/jgilpatino_miu_edu/EejydkVN27BJp0NvnOKG2B0BQsc6KpBAs7PQTgvGM4PPZw?e=Kl5QeP

This project is to show the knowdledge acquire in the Big Data course in the Maharishi International University. The project consist in two JAVA applications.
1. Consumer from Twitter and Producer to Kafka topic
2. Consumer of Kafka Topic and Producer to HDFS
3. Import all the data to HIVE table and do analysis

The tech stack was:
1. JAVA 11
2. SpringBoot 2.6
3. Spark core 2.11
4. HIVE CLI
## 1 KAFKA SetUp
- Download KAFKA from https://downloads.apache.org/kafka/3.2.3/kafka_2.12-3.2.3.tgz 
- Unzip the files in a place that you will have access
- if you are on Windows use the Windows folder and the .bat files
- if you are on Linux use the default folder and the .sh files
- cd to your kafka folder (eg: cd local/usr/kafka)
- to run KAFKA in your local you must be a super user.
- run the Zookeeper (by default localhost:2181) in Cloudera you dont need to do this step.
- run the Kafka server bin/kafka-server-start.sh config/server.properties (by default localhost:9092)
- create the topic bin/kafka-topics.sh --create --topic twitter --bootstrap-server localhost:9092
![Kakfa-server](https://github.com/sebastiangilpatino/twitter-kafka-spark-hadoop/blob/master/kafka-server-run.jpg) 
## 2 Consumer Twitter - Producer Kafka topic
- This is a java application (DEMO) which setup is to use only in your local if in the future you want to use in a real kafka server the hardcode constants should be move to program arguments
- Run the maven project BDTProject 
- You will see in the console the tweets that is coming from the streaming source
![twitter-streaming](https://github.com/sebastiangilpatino/twitter-kafka-spark-hadoop/blob/master/twitter-streaming.jpg)
## 3 Consumer Kafka topic - Spark Kafka Consumer - Spark Producer to HDFS
- This is a java application (DEMO) which setup is to use only in your local if in the future you want to use in a real kafka server the hardcode constants should be move to program arguments
- Run the maven project BDTConsumer
- You will see in the console the tweets that is coming from the kafka topic
![Spark-commit](https://github.com/sebastiangilpatino/twitter-kafka-spark-hadoop/blob/master/spark-commit.jpg)
## 4 HDFS
- The Consumer will save the information on: hdfs://quickstart.cloudera:8020/user/cloudera/twitter/[systemTimeInMillis]
- in (hdfs dfs -ls /twitter) you will be able to see the files the kafka consumer - spark producer pushed to hdfs
- then we will analyze the data using hive
-     [create table] - CREATE  EXTERNAL  TABLE tweet ( value STRING )
-     [load data] - LOAD DATA INPATH '/user/cloudera/twitter/1663702230013/part-00000' OVERWRITE INTO TABLE tweet;
-     [query 1] - SELECT b.text FROM tweet t LATERAL VIEW json_tuple(t.value, 'id', 'text') b AS id, text WHERE b.id='1572307197765341189';
-     [query 2] - SELECT b.text FROM tweet t LATERAL VIEW json_tuple(t.value, 'id', 'text') b AS id, text WHERE b.text LIKE concat('RT','%');
-     [query 3] - SELECT b.text FROM tweet t LATERAL VIEW json_tuple(t.value, 'id', 'text') b AS id, text WHERE b.text LIKE concat('RT @guzznft','%');
![hive-1](https://github.com/sebastiangilpatino/twitter-kafka-spark-hadoop/blob/master/hive-1.jpg)
![hive-2](https://github.com/sebastiangilpatino/twitter-kafka-spark-hadoop/blob/master/hive-2.jpg)
