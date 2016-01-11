# dmk-kafka-storm
kafka + storm sample rig


To use:
---
* Install Kafka
* Install Docker
* If MACOS, install docker-machine
* If MACOS, start docker machine and call it default-small

Console 0 - start Kafka docker image
---
docker run -p 2181:2181 -p 9092:9092 --env ADVERTISED_HOST=`docker-machine ip default-small` --env ADVERTISED_PORT=9092 spotify/kafka
this has a test topic already created

Console 1 - Produce messages 
---
./bin/kafka-console-producer.sh --broker-list 192.168.99.100:9092 --topic test

Console 2 - Read messages
---
./bin/kafka-console-consumer.sh --zookeeper 192.168.99.100:2181 --topic test --from-beginning

From Eclipse
---
 Run KafkaToBasicEmitTopology.scala, see messages from console producer
