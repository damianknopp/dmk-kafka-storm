package dmk.storm.topology

import dmk.storm.bolt.BasicEmitBolt
import dmk.storm.spout.SampleDataGenSpout
import dmk.storm.bolt.kafka.RoundRobinKafkaTopicSelector
import backtype.storm.LocalCluster
import backtype.storm.topology.TopologyBuilder
import backtype.storm.Config
import storm.kafka.bolt.KafkaBolt
import storm.kafka.bolt.mapper.FieldNameBasedTupleToKafkaMapper
import storm.kafka.trident.TridentKafkaState
import java.util.Properties

class SampleDataToKafkaTopology {
  
  def init(): Unit = {
    val builder: TopologyBuilder = new TopologyBuilder()
    val dataGenSpoutName = "SampleDataGenSpout"
    builder.setSpout(dataGenSpoutName, new SampleDataGenSpout(), 4)
    
    val kafkaWriterBolt = new KafkaBolt()
          .withTopicSelector(new RoundRobinKafkaTopicSelector())
          .withTupleToKafkaMapper(new FieldNameBasedTupleToKafkaMapper[String, String]())
    builder.setBolt("KafkaWriterBolt", kafkaWriterBolt, 2)
            .shuffleGrouping(dataGenSpoutName)

    val conf: Config = new Config()
    conf.put(TridentKafkaState.KAFKA_BROKER_PROPERTIES, buildKafkaConfig())
    val debug = true
    conf.setDebug(debug)
    val cluster: LocalCluster = new LocalCluster()

    val topologyName = "SampleDataToKafkaTopology"
    cluster.submitTopology(topologyName, conf, builder.createTopology())
    Thread.sleep(5000)
    cluster.killTopology(topologyName)
    cluster.shutdown()

  }
  
  def buildKafkaConfig(): java.util.Map[_, _] = {
   val props = new Properties()
   val hosts = "192.168.99.100:9092"
   props.put("metadata.broker.list", hosts)
   props.put("request.required.acks", "0")
   props.put("serializer.class", "kafka.serializer.StringEncoder")
   props
  }
  
}
object SampleDataToKafkaTopology {
  
  def main(args: Array[String]): Unit = {
    new SampleDataToKafkaTopology().init();
  }
}