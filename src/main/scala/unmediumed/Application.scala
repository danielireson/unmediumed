package unmediumed

import java.io.FileNotFoundException

import unmediumed.components.{Config, MediumService, TemplateBuilder, WebsiteScraper}

import scala.io.Source
import scala.util.Try

object Application extends Config
  with MediumService
  with WebsiteScraper
  with TemplateBuilder {

  val config = new Config(Map())
  val mediumService = new MediumService
  val websiteScraper = new WebsiteScraper

  lazy val templateBuilder = new TemplateBuilder(loadBaseTemplate())

  private def loadBaseTemplate(): String = {
    Try(Source.fromResource("template.html").mkString).getOrElse {
      throw new FileNotFoundException("Unable to load base template")
    }
  }
}
