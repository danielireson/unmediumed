package unmediumed

import java.io.FileNotFoundException

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import unmediumed.core.Config
import unmediumed.request.{Input, Router}
import unmediumed.response.{Output, TemplateBuilder}
import unmediumed.source.{MediumService, WebsiteScraper}

import scala.io.Source
import scala.util.Try

class Handler extends RequestHandler[Input, Output] {
  val params: String = getParams
  val template: String = getTemplate

  val config = new Config(params)
  val templateBuilder = new TemplateBuilder(config, template)
  val websiteScraper = new WebsiteScraper(config)
  val mediumService = new MediumService(config, websiteScraper)
  val router = new Router(config, templateBuilder, mediumService)

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

  private def getTemplate: String = {
    Try(Source.fromResource("template.html").mkString).getOrElse {
      throw new FileNotFoundException("Unable to load base template")
    }
  }
}
