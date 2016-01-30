package dmk.storm.bolt

import backtype.storm.topology.OutputFieldsDeclarer
import backtype.storm.topology.BasicOutputCollector
import backtype.storm.tuple.Tuple
import backtype.storm.topology.base.BaseBasicBolt
import backtype.storm.tuple.Fields

/**
 * Just print tuples to sysout
 */
class BasicEmitBolt extends BaseBasicBolt {

  override def execute(input: Tuple, collector: BasicOutputCollector): Unit = {
    val fields: Fields = input.getFields
    val key = input.getValueByField("key")
    //val message = input.getValueByField("message").asInstanceOf[Array[String]]
    val message = input.getValueByField("message").asInstanceOf[String]
    println("sourceTaskId: " + input.getSourceTask)
    println("messageId: " + input.getMessageId)
    println("fields: " + fields.toList())
    println("key:" + key.toString)
    println("message: " + message)
    //println("messages size: " + message.size.toString)
    // message.foreach { x => println(x) }
    
    // acks are handled for us by BaseBasicBolt
    
  }

  override def declareOutputFields(declarer: OutputFieldsDeclarer): Unit = { 
    //noop
  }

}
  
object BasicEmitBolt {
    private val serialVersionUID = 1L
}