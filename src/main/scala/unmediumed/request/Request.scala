package unmediumed.request

import scala.collection.JavaConverters._

sealed case class Request(
    httpMethod: String,
    path: String,
    body: String = "",
    headers: Map[String, Object] = Map(),
    pathParameters: Map[String, Object] = Map(),
    queryStringParameters: Map[String, Object] = Map()) {

  def toInput: Input =
    new Input(
      httpMethod,
      path,
      body,
      headers.asJava,
      pathParameters.asJava,
      queryStringParameters.asJava
    )
}
