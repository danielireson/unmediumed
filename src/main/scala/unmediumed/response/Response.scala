package unmediumed.response

import scala.collection.JavaConverters._

sealed case class Response(
    statusCode: Integer,
    body: String,
    headers: Map[String, String] = Map(),
    base64Encoded: Boolean = true) {

  def toOutput: Output = new Output(statusCode, body, headers.asJava, base64Encoded)
}

class HtmlResponse(body: String)
  extends Response(200, body, Map("content-type" -> "text/html"))

class MarkdownResponse
  extends Response(200, "", Map("content-type" -> "text/markdown"))

class UnprocessableEntityResponse
  extends Response(422, "", Map("content-type" -> "text/markdown"))

class InternalServerErrorResponse
  extends Response(500, "", Map("content-type" -> "text/markdown"))
