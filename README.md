# Apache Kafka

## Commands

Start zookeeper 

 ```sh
 $ bin/zookeeper-server-start.sh config/zookeeper.properties
 ```

Start kafka server 

 ```sh
 $ bin/kafka-server-start.sh config/server.properties
 ```

List topics

 ```sh
 $ bin/kafka-topics.sh --list --bootstrap-server localhost:9092
 ```

Consume topic messages

 ```sh
 $ bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic <topic-name> --from-beginning
 ```
