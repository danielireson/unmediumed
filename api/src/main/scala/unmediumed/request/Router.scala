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
    val url = new RequestParser().getPostUrl(request)
    val post = mediumService.getPost(url)

    new OkResponse(post.markdown)
  }

  private def mapFailure(caught: Throwable): Response = {
    caught match {
      case t: RequestParseFailedException => new UnprocessableEntityResponse(t.getMessage)
      case _: WebsiteScrapeFailedException => new BadGatewayResponse("Unable to fetch Medium post")
      case _: ParseFailedException => new InternalServerErrorResponse("Unable to parse Medium post")
      case _ => new InternalServerErrorResponse("An unexpected error occurred")
    }
  }
}
