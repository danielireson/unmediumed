package unmediumed

import com.amazonaws.services.lambda.runtime.Context
import unmediumed.parse.{HtmlParseFailedException, HtmlParseProtectedPostException, HtmlParser}
import unmediumed.request.{Input, PathParseFailedException, PathParser}
import unmediumed.response._
import unmediumed.source.{AnalyticsTracker, WebsiteScrapeFailedException, WebsiteScraper}

import scala.util.{Failure, Success, Try}

class Handler(
    pathParser: PathParser,
    websiteScraper: WebsiteScraper,
    htmlParser: HtmlParser,
    analyticsTracker: AnalyticsTracker) {

  def handleRequest(input: Input, context: Context): Output = {
    Try {
      val request = input.toRequest(context)
      analyticsTracker.track(request)

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
      case _: PathParseFailedException => UnprocessableEntityResponse("Please provide a valid Medium URL or path")
      case _: WebsiteScrapeFailedException => BadGatewayResponse("Unable to fetch Medium post")
      case _: HtmlParseProtectedPostException => UnprocessableEntityResponse("Unable to parse members only Medium post")
      case _: HtmlParseFailedException => InternalServerErrorResponse("Unable to parse Medium post")
      case _ => InternalServerErrorResponse("An unexpected error occurred")
    }
  }
}
