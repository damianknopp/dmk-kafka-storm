# dmk-kafka-storm
kafka + storm sample rig


To use:
---
* Download and unzip Kafka
* Install Docker
* If MACOS, install docker-machine
* If MACOS, start docker machine and call it default-small

	docker-machine create --driver=virtualbox --virtualbox-cpu-count 1 --virtualbox-memory "512" default-small

Console 0 - start Kafka docker image
---
Start Kafka docker image.  The spotify one has a test topic already created

	docker run -p 2181:2181 -p 9092:9092 --env ADVERTISED_HOST=`docker-machine ip default-small` --env ADVERTISED_PORT=9092 spotify/kafka

Console 1 - Produce messages 
---
Produce some messages using the Kafka command line tool

	./bin/kafka-console-producer.sh --broker-list 192.168.99.100:9092 --topic test

Console 2 - Read messages
---
Read messages using the Kafka command line tool

	./bin/kafka-console-consumer.sh --zookeeper 192.168.99.100:2181 --topic test --from-beginning

From Eclipse
---
 Run KafkaToBasicEmitTopology.scala, see messages from console producer
