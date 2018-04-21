package unmediumed

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import scala.collection.JavaConverters

class Handler extends RequestHandler[Request, Response] {
  def handleRequest(input: Request, context: Context): Response = {
    val headers = Map("x-custom-response-header" -> "my custom response header value")
    Response(
      200,
      "Go Serverless v1.0! Your function executed successfully!",
      JavaConverters.mapAsJavaMap[String, Object](headers))
  }
}
