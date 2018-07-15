package unmediumed

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import unmediumed.request.{Input, Router}
import unmediumed.response.Output
import unmediumed.source.{MediumService, WebsiteScraper}

import scala.util.Try

class Handler extends RequestHandler[Input, Output] {
  val params: String = getParams

  val websiteScraper = new WebsiteScraper
  val mediumService = new MediumService(websiteScraper)
  val router = new Router(mediumService)

  def handleRequest(input: Input, context: Context): Output = {
    Option(input).map(_.toRequest).map(router.routeRequest).getOrElse {
      throw new IllegalArgumentException("Invalid input passed to application handler")
    }
  }

  private def getParams: String = {
    Try(System.getenv("PARAMS")).getOrElse {
      throw new IllegalArgumentException("Unable to load application parameters")
    }
  }
}
