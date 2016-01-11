package dmk.storm.topology

import dmk.storm.spout.SampleDataGenSpout
import backtype.storm.LocalCluster
import backtype.storm.topology.TopologyBuilder
import backtype.storm.Config
import dmk.storm.bolt.BasicEmitBolt

class SampleDataToBasicEmitTopology {
  
  def init(): Unit = {
    val builder: TopologyBuilder = new TopologyBuilder()
    val dataGenSpoutName = "SampleDataGenSpout"
    builder.setSpout(dataGenSpoutName, new SampleDataGenSpout(), 4)
    builder.setBolt("BasicEmitBolt", new BasicEmitBolt(), 2).shuffleGrouping(dataGenSpoutName)

    val conf: Config = new Config()
    val debug = true
    conf.setDebug(debug)
    val cluster: LocalCluster = new LocalCluster()

    val topologyName = "SampleDataToBasicEmitTopology"
    cluster.submitTopology(topologyName, conf, builder.createTopology())
    Thread.sleep(5000)
    cluster.killTopology(topologyName)
    cluster.shutdown()

  }
}
object SampleDataToBasicEmitTopology {
  
  def main(args: Array[String]): Unit = {
    new SampleDataToBasicEmitTopology().init();
  }
}