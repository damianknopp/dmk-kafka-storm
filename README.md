# dmk-kafka-storm
kafka + storm sample rig



docker run -p 2181:2181 -p 9092:9092 --env ADVERTISED_HOST=`docker-machine ip default-small` --env ADVERTISED_PORT=9092 spotify/kafka
