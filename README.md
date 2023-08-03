# kafka

docker run -d --name zookeeper-container -e TZ=UTC -p 2181:2181 ubuntu/zookeeper:3.1-22.04_beta

Parameter	    Description
-e TZ=UTC	    Timezone.
-p 2181:2181	Expose Apache Zookeeper service on localhost:2181.
-v /etc/kafka/zookeeper.properties	Zookeeper config (file path within the container can also be set by overriding the CMD).

docker run -it --name kafka-container -e TZ=UTC -p 9092:9092 -e ZOOKEEPER_HOST=host.docker.internal ubuntu/kafka:3.1-22.04_beta

Apache Kafka service is now exposed at localhost:9092 (You first need to run a Zookeeper instance: docker run -d -p 2181:2181 ubuntu/zookeeper:edge).

cd ~/apps/descompactadas/kafka_2.12-3.5.1/

  bin/zookeeper-server-start.sh config/zookeeper.properties 
  bin/kafka-server-start.sh config/server.properties 


  bin/kafka-topics.sh --list --bootstrap-server localhost:9092
  bin/kafka-console-producer.sh --broker-list localhost:9092 --topic ECOMMERCE_NEW_ORDER2

bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic ECOMMERCE_NEW_ORDER2 --from-beginning