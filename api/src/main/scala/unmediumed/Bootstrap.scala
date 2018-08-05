package unmediumed

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import unmediumed.parse.HtmlParser
import unmediumed.request.{Input, PathParser}
import unmediumed.response.Output
import unmediumed.source.{AnalyticsTracker, WebsiteScraper}

class Bootstrap extends RequestHandler[Input, Output] {
  val pathParser: PathParser = new PathParser
  val websiteScraper: WebsiteScraper = new WebsiteScraper
  val htmlParser: HtmlParser = new HtmlParser
  val analyticsTracker: AnalyticsTracker = new AnalyticsTracker

  def handleRequest(input: Input, context: Context): Output =
    new Handler(pathParser, websiteScraper, htmlParser, analyticsTracker).handleRequest(input, context)
}
