package dmk.storm.encoder

import kafka.serializer.Encoder
import kafka.utils.VerifiableProperties
import java.io.ByteArrayOutputStream
import org.apache.avro.io.EncoderFactory

class AvroEncoder(props: VerifiableProperties) extends Encoder[String] {

  def toBytes(str: String) : Array[Byte] = {
    val out = new ByteArrayOutputStream()
    val encoder = EncoderFactory.get().binaryEncoder(out, null)
    try {
      val bytes = str.getBytes("UTF-8")
      encoder.writeBytes(bytes)
      encoder.flush()
      out.toByteArray()
    } finally {
      if(out != null)
        out.close
    }
  }
}