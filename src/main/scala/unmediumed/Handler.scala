package unmediumed

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import unmediumed.request.{Input, Request}
import unmediumed.response.{Output, Response}

class Handler extends RequestHandler[Input, Output] {
  def handleRequest(input: Input, context: Context): Output = {
    getRequest(input) match {
      case r if r.path == "/" => renderHomepage(r)
      case r => renderMarkdownPost(r)
    }
  }

  private def getRequest(input: Input): Request = {
    Option(input).map(_.toRequest).getOrElse {
      throw new IllegalArgumentException("Request input is invalid")
    }
  }

  private def renderHomepage(request: Request): Output = {
    Response(200, "<!DOCTYPE html><html></html><body></body>", Map(
      "content-type" -> "text/html"
    )).toOutput
  }

  private def renderMarkdownPost(request: Request): Output = {
    Response(200, "", Map(
      "content-type" -> "text/markdown"
    )).toOutput
  }
}
