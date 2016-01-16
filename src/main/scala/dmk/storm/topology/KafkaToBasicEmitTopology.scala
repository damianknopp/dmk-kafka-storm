package dmk.storm.topology

import backtype.storm.LocalCluster
import backtype.storm.topology.TopologyBuilder
import backtype.storm.Config
import dmk.storm.bolt.BasicEmitBolt
import storm.kafka.KafkaSpout
import storm.kafka.SpoutConfig
import storm.kafka.KafkaConfig
import storm.kafka.StringScheme
import backtype.storm.spout.SchemeAsMultiScheme
import java.util.Arrays
import storm.kafka.Broker
import storm.kafka.ZkHosts
import dmk.storm.bolt.KafkaBasicEmitBolt

class KafkaToBasicEmitTopology {
  
  def init(): Unit = {
    val builder: TopologyBuilder = new TopologyBuilder()
    val inputSpoutName = "KafkaSpout"
//    val host1 = new Broker("localhost", 9092)
      //WARNING: use the docker-machine ip instead of localhost
    val host1 = new ZkHosts("192.168.99.100")      
      
   val spoutConfig = new SpoutConfig(
      host1,
      "test", // Topic to read from
      "/brokers", // The root path in Zookeeper for the spout to store the consumer offsets
      "kafkastormtest") // An id for this consumer for storing the consumer offsets in Zookeeper
    spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme())
    builder.setSpout(inputSpoutName, new KafkaSpout(spoutConfig), 1) //pass in number equal to partitions of the kafka topic
    builder.setBolt("KafkaBasicEmitBolt", new KafkaBasicEmitBolt(), 2).shuffleGrouping(inputSpoutName)

    val conf: Config = new Config()
    val debug = false
    conf.setDebug(debug)
    val cluster: LocalCluster = new LocalCluster()

    val topologyName = "KafkaToBasicEmitTopology"
    cluster.submitTopology(topologyName, conf, builder.createTopology())
    Thread.sleep(30000)
    cluster.killTopology(topologyName)
    cluster.shutdown()

  }
}
object KafkaToBasicEmitTopology {
  
  def main(args: Array[String]): Unit = {
    new KafkaToBasicEmitTopology().init();
  }
}