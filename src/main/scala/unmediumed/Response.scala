package unmediumed

import scala.collection.JavaConverters._

case class Response(
    statusCode: Integer,
    body: String,
    headers: Map[String, String] = Map(),
    base64Encoded: Boolean = true) {

  def toOutput: Output = new Output(statusCode, body, headers.asJava, base64Encoded)
}
