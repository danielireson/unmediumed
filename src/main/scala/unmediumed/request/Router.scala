package unmediumed.request

import unmediumed.core.Config
import unmediumed.response._
import unmediumed.source.MediumService

import scala.util.{Failure, Success, Try}

class Router(config: Config, templateBuilder: TemplateBuilder, mediumService: MediumService) {
  def routeRequest(request: Request): Output = {
    val response = Try {
      Option(request) match {
        case Some(r) if r.path == "/" => buildHomepage(r)
        case Some(r) => buildMarkdownPost(r)
        case None => throw new IllegalArgumentException("Invalid request passed to router")
      }
    }

    response match {
      case Success(r) => r.toOutput
      case Failure(t) => failureResponse(t).toOutput
    }
  }

  private def buildHomepage(request: Request): Response = {
    val responseBody = templateBuilder.build()
    new HtmlResponse(responseBody)
  }

  private def buildMarkdownPost(request: Request): Response = {
    val url = new RequestParser().getPostUrl(request)
    new MarkdownResponse
  }

  private def failureResponse(caught: Throwable): Response = {
    caught match {
      case _: IllegalArgumentException => new InternalServerErrorResponse
      case _: RequestParseFailedException => new UnprocessableEntityResponse
    }
  }
}
