package unmediumed.request

import com.amazonaws.services.lambda.runtime.Context

import scala.beans.BeanProperty
import scala.collection.JavaConverters._

class Input(
    @BeanProperty var httpMethod: String,
    @BeanProperty var path: String,
    @BeanProperty var body: String,
    @BeanProperty var headers: java.util.Map[String, Object],
    @BeanProperty var pathParameters: java.util.Map[String, Object],
    @BeanProperty var queryStringParameters: java.util.Map[String, Object]) {

  def this() = this(null, null, null, null, null, null)

  def toRequest(context: Context): Request =
    Request(
      Option(httpMethod).getOrElse(""),
      Option(path).getOrElse(""),
      Option(body).getOrElse(""),
      Option(context.getAwsRequestId).getOrElse(""),
      Option(headers).map(_.asScala.toMap).getOrElse(Map()),
      Option(pathParameters).map(_.asScala.toMap).getOrElse(Map()),
      Option(queryStringParameters).map(_.asScala.toMap).getOrElse(Map())
    )
}
