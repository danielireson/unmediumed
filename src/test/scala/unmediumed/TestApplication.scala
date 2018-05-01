package unmediumed

import org.scalatest.mockito.MockitoSugar
import unmediumed.components.{MediumService, TemplateBuilder, WebsiteScraper}

trait TestApplication extends MockitoSugar
  with components.Config
  with MediumService
  with WebsiteScraper
  with TemplateBuilder {

  val config = new Config(Map())
  val mediumService = mock[MediumService]
  val websiteScraper = mock[WebsiteScraper]

  val templateBuilder = new TemplateBuilder("<!DOCTYPE html><html><body></body></html>")
}
