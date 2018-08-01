package unmediumed.request

import unmediumed.parse.ParseFailedException
import unmediumed.response._
import unmediumed.source.{MediumService, WebsiteScrapeFailedException}

import scala.util.{Failure, Success, Try}

class Router(mediumService: MediumService) {
  def routeRequest(request: Request): Output = {
    val response = Try {
      Option(request).map(buildMarkdownPost).getOrElse {
        throw new IllegalArgumentException("Invalid request passed to router")
      }
    }

    response match {
      case Success(r) => r.toOutput
      case Failure(t) => mapFailure(t).toOutput
    }
  }

  private def buildMarkdownPost(request: Request): Response = {
    val url = new PathParser().getPostUrl(request)
    val post = mediumService.getPost(url)

    OkResponse(post)
  }

  private def mapFailure(caught: Throwable): Response = {
    caught match {
      case t: PathParseFailedException => UnprocessableEntityResponse(t.getMessage)
      case _: WebsiteScrapeFailedException => BadGatewayResponse("Unable to fetch Medium post")
      case _: ParseFailedException => InternalServerErrorResponse("Unable to parse Medium post")
      case _ => InternalServerErrorResponse("An unexpected error occurred")
    }
  }
}
