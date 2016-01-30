package dmk.storm.spout

import java.util.HashMap
import java.util.Map
import java.util.Random

import backtype.storm.spout.SpoutOutputCollector
import backtype.storm.task.TopologyContext
import backtype.storm.topology.OutputFieldsDeclarer
import backtype.storm.topology.base.BaseRichSpout
import backtype.storm.tuple.Fields
import backtype.storm.tuple.Values

class SampleDataGenSpout extends BaseRichSpout {

  private var spoutOutputCollector: SpoutOutputCollector = null
  
  override def open(conf: Map[_,_], context: TopologyContext, spoutOutputCollector: SpoutOutputCollector): Unit = {
     this.spoutOutputCollector = spoutOutputCollector
  }

  override def nextTuple(): Unit = {
    val rand: Random = new Random()
    val randomNumber = rand.nextInt(SampleDataGenSpout.map.size())
    this.spoutOutputCollector.emit(new Values(randomNumber.toString, SampleDataGenSpout.map.get(randomNumber)))
  }

  override def declareOutputFields(declarer: OutputFieldsDeclarer): Unit = {
    declarer.declare(new Fields("key", "message"))
  }
}

object SampleDataGenSpout {
  val serialVersionUID = 1L

//  private val map = new HashMap[Integer, Array[String]]()
//  map.put(0, Array("message0 tuple0", "message0 tuple1"))
//  map.put(1, Array("message1 tuple0", "message1 tuple1"))
//  map.put(2, Array("message2 tuple0", "message2 tuple1"))
//  map.put(3, Array("message3 tuple0", "message3 tuple1"))

    private val map = new HashMap[Integer, String]()
  map.put(0, "message0 tuple0")
  map.put(1, "message1 tuple0")
  map.put(2, "message2 tuple0")
  map.put(3, "message3 tuple0")

}