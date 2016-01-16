package dmk.storm.bolt.kafka

import storm.kafka.bolt.selector.KafkaTopicSelector
import backtype.storm.tuple.Tuple

class RoundRobinKafkaTopicSelector(maxTopics: Int = 4, topicPrefix: String = "topic")
  extends KafkaTopicSelector {

  var curIndex = 0

  def getTopic(tuple: Tuple): String = {
    String.format("%s%02d", topicPrefix, maxTopics.toString)
  }

  def cycleNext(): Int = {
    curIndex = curIndex + 1
    if (curIndex > maxTopics) 0 else curIndex
  }

}