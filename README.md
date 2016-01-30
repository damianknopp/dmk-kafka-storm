# dmk-kafka-storm
kafka + storm sample rig


To use:
---
* Download and unzip Kafka
* Install Docker
* If MACOS, install docker-machine
* If MACOS, start docker machine and call it default-small

	docker-machine create --driver=virtualbox --virtualbox-cpu-count 1 --virtualbox-memory "512" default-small

	docker-machine ls

	docker-machine start default-small

	eval "$(docker-machine env default-small)"


Console 0 - start Kafka docker image
---
Start Kafka docker image.  The spotify one has a test topic already created

	docker run -p 2181:2181 -p 9092:9092 --env ADVERTISED_HOST=`docker-machine ip default-small` --env ADVERTISED_PORT=9092 spotify/kafka
	./bin/kafka-topics.sh --zookeeper 192.168.99.100:2181 --list

Console 1 - Produce messages 
---
Produce some messages using the Kafka command line tool

	~/kafka/bin/kafka-console-producer.sh --broker-list 192.168.99.100:9092 --topic test

Console 2 - Read messages
---
Read messages using the Kafka command line tool

	~/kafka/bin/kafka-console-consumer.sh --zookeeper 192.168.99.100:2181 --topic test --from-beginning

From Eclipse
---
 Run SampleDataToBasicEmitTopology.scala, see sample messages generated in storm, send to a bolt and print to the console, plus emited to nowhere

 Run KafkaToBasicEmitTopology.scala, see messages from console producer against topic named test

 Run SampleDataToKafkaTopology.scala, 
 List new topics created

 ~/kafka/bin/kafka-topics.sh --list --zookeeper 192.168.99.100:2181
 
 Pick a topic and see the key and messge written

 ~/kafka/./bin/kafka-console-consumer.sh --zookeeper 192.168.99.100:2181 --topic topic_04 --from-beginning --property print.key=true --property key.separator="->"
