package unmediumed

import java.{util => ju}

import scala.beans.BeanProperty
import scala.collection.JavaConverters

case class Response(
    @BeanProperty statusCode: Integer,
    @BeanProperty body: String,
    @BeanProperty headers: ju.Map[String, Object],
    @BeanProperty base64Encoded: Boolean)

object Response {
  def apply(
      statusCode: Integer,
      body: String,
      headers: Map[String, Object] = Map(),
      base64Encoded: Boolean = true): Response = {

    new Response(statusCode, body, JavaConverters.mapAsJavaMap[String, Object](headers), base64Encoded)
  }
}
