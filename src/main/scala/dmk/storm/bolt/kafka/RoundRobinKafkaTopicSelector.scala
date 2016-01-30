package dmk.storm.bolt.kafka

import storm.kafka.bolt.selector.KafkaTopicSelector
import backtype.storm.tuple.Tuple

class RoundRobinKafkaTopicSelector(maxTopics: Int = 4, topicPrefix: String = "topic")
  extends KafkaTopicSelector {

  var curIndex = -1

  def getTopic(tuple: Tuple): String = {
    curIndex = cycleNext
    // topic is created in kafka if it does not already exist
    String.format("%s_%02d", topicPrefix, curIndex.asInstanceOf[Integer])
  }

  def cycleNext(): Int = {
    curIndex = curIndex + 1
    if (curIndex > maxTopics) 0 else curIndex
  }

}