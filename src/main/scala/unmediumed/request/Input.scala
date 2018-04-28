package unmediumed.request

import java.{util => ju}

import scala.beans.BeanProperty
import scala.collection.JavaConverters._

class Input(
    @BeanProperty val httpMethod: String,
    @BeanProperty val path: String,
    @BeanProperty val body: String,
    @BeanProperty val headers: ju.Map[String, Object],
    @BeanProperty val pathParameters: ju.Map[String, Object],
    @BeanProperty val queryStringParameters: ju.Map[String, Object]) {

  def this() = this("", "", "", new ju.HashMap(), new ju.HashMap(), new ju.HashMap())

  def toRequest: Request =
    Request(
      httpMethod,
      path,
      body,
      headers.asScala.toMap,
      pathParameters.asScala.toMap,
      queryStringParameters.asScala.toMap
    )
}
