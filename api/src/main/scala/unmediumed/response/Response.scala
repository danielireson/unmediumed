package unmediumed.response

import scala.collection.JavaConverters._

sealed case class Response(
    statusCode: Integer,
    body: String,
    headers: Map[String, String] = Map(),
    base64Encoded: Boolean = true) {

  def toOutput: Output = new Output(statusCode, body, headers.asJava, base64Encoded)
}

class OkResponse(body: String)
  extends Response(200, body, Map("content-type" -> "text/markdown"))

class UnprocessableEntityResponse(message: String)
  extends Response(422, message, Map("content-type" -> "text/markdown"))

class InternalServerErrorResponse(message: String)
  extends Response(500, message, Map("content-type" -> "text/markdown"))

class BadGatewayResponse(message: String)
  extends Response(500, message, Map("content-type" -> "text/markdown"))
