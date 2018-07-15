package unmediumed.request

import unmediumed.response._
import unmediumed.source.MediumService

import scala.util.{Failure, Success, Try}

class Router(mediumService: MediumService) {
  def routeRequest(request: Request): Output = {
    val response = Try {
      Option(request) match {
        case Some(r) => buildMarkdownPost(r)
        case None => throw new IllegalArgumentException("Invalid request passed to router")
      }
    }

    response match {
      case Success(r) => r.toOutput
      case Failure(t) => failureResponse(t).toOutput
    }
  }

  private def buildMarkdownPost(request: Request): Response = {
    val url = new RequestParser().getPostUrl(request)
    val post = mediumService.getPost(url)

    new MarkdownResponse(post.markdown)
  }

  private def failureResponse(caught: Throwable): Response = {
    caught match {
      case _: IllegalArgumentException => new InternalServerErrorResponse
      case _: RequestParseFailedException => new UnprocessableEntityResponse
    }
  }
}
