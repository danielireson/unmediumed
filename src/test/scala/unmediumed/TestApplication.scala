package unmediumed

import org.scalatest.mockito.MockitoSugar
import unmediumed.components.{MediumService, TemplateBuilder, WebsiteScraper}

trait TestApplication extends MockitoSugar
  with components.Config
  with MediumService
  with TemplateBuilder
  with WebsiteScraper {

  val config = new Config(Map())
  val mediumService = mock[MediumService]
  val templateBuilder = new TemplateBuilder("<!DOCTYPE html><html><body></body></html>")
  val websiteScraper = mock[WebsiteScraper]
}
