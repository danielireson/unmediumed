package unmediumed

import java.io.FileNotFoundException

import unmediumed.components.{Config, MediumService, TemplateBuilder, WebsiteScraper}

import scala.io.Source
import scala.util.Try

object Application extends Config
  with MediumService
  with TemplateBuilder
  with WebsiteScraper {

  lazy val config = new Config(Map())
  lazy val mediumService = new MediumService
  lazy val templateBuilder = new TemplateBuilder(loadBaseTemplate())
  lazy val websiteScraper = new WebsiteScraper

  private def loadBaseTemplate(): String = {
    Try(Source.fromResource("template.html").mkString).getOrElse {
      throw new FileNotFoundException("Unable to load base template")
    }
  }
}
