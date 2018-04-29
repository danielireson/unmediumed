package unmediumed.response

import scala.collection.JavaConverters._

sealed case class Response(
    statusCode: Integer,
    body: String,
    headers: Map[String, String] = Map(),
    base64Encoded: Boolean = true) {

  def toOutput: Output = new Output(statusCode, body, headers.asJava, base64Encoded)
}
