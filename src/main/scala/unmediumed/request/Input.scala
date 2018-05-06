package unmediumed.request

import java.{util => ju}

import scala.beans.BeanProperty
import scala.collection.JavaConverters._

class Input(
    @BeanProperty var httpMethod: String,
    @BeanProperty var path: String,
    @BeanProperty var body: String,
    @BeanProperty var headers: ju.Map[String, Object],
    @BeanProperty var pathParameters: ju.Map[String, Object],
    @BeanProperty var queryStringParameters: ju.Map[String, Object]) {

  def this() = this(null, null, null, null, null, null)

  def toRequest: Request =
    Request(
      Option(httpMethod).getOrElse(""),
      Option(path).getOrElse(""),
      Option(body).getOrElse(""),
      Option(headers).map(_.asScala.toMap).getOrElse(Map()),
      Option(pathParameters).map(_.asScala.toMap).getOrElse(Map()),
      Option(queryStringParameters).map(_.asScala.toMap).getOrElse(Map())
    )
}
