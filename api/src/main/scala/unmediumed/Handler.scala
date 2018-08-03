package unmediumed

import com.amazonaws.services.lambda.runtime.Context
import unmediumed.parse.{HtmlParseFailedException, HtmlParser}
import unmediumed.request.{Input, PathParseFailedException, PathParser}
import unmediumed.response._
import unmediumed.source.{WebsiteScrapeFailedException, WebsiteScraper}

import scala.util.{Failure, Success, Try}

class Handler(pathParser: PathParser, websiteScraper: WebsiteScraper, htmlParser: HtmlParser) {
  def handleRequest(input: Input, context: Context): Output = {
    Try {
      val request = input.toRequest
      val postUrl = pathParser.parse(request.path)
      val postHtml = websiteScraper.scrape(postUrl)
      val post = htmlParser.parse(postHtml)
      OkResponse(post)
    } match {
      case Success(r) => r.toOutput
      case Failure(t) => mapFailure(t).toOutput
    }
  }

  private def mapFailure(caught: Throwable): Response = {
    caught match {
      case t: PathParseFailedException => UnprocessableEntityResponse(t.getMessage)
      case _: WebsiteScrapeFailedException => BadGatewayResponse("Unable to fetch Medium post")
      case _: HtmlParseFailedException => InternalServerErrorResponse("Unable to parse Medium post")
      case _ => InternalServerErrorResponse("An unexpected error occurred")
    }
  }
}
