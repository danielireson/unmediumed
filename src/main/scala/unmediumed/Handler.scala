package unmediumed

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}

class Handler extends RequestHandler[Input, Output] {
  def handleRequest(input: Input, context: Context): Output = {
    val request = Option(input) match {
      case Some(r: Input) => r.toRequest
      case None => throw new IllegalArgumentException("Request input is invalid")
    }

    val response = Response(200, "Go Serverless v1.0! Your function executed successfully!", Map(
      "x-extra-header" -> "abc"
    ))

    response.toOutput
  }
}
