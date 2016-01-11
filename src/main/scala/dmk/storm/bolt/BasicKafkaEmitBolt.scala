package dmk.storm.bolt

import backtype.storm.topology.OutputFieldsDeclarer
import backtype.storm.topology.BasicOutputCollector
import backtype.storm.tuple.Tuple
import backtype.storm.topology.base.BaseBasicBolt
import backtype.storm.tuple.Fields

/**
 * Just print tuples to sysout
 */
class KafkaBasicEmitBolt extends BaseBasicBolt {

  override def execute(input: Tuple, collector: BasicOutputCollector): Unit = {
    val fields: Fields = input.getFields
    val message = input.getValueByField("str").asInstanceOf[String]
    println("sourceTaskId: " + input.getSourceTask)
    println("messageId: " + input.getMessageId)
    println("fields: " + fields.toList())
    println("message from kafka: " + message)
    
    // acks are handled for us by BaseBasicBolt
    
  }

  override def declareOutputFields(declarer: OutputFieldsDeclarer): Unit = { 
    //noop
  }

}
  
object KafkaBasicEmitBolt {
    private val serialVersionUID = 1L
}