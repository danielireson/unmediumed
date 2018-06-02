package unmediumed.core

import org.scalatest.mockito.MockitoSugar
import unmediumed.response.TemplateBuilderComponent
import unmediumed.source._

trait TestComponentRegistry extends MockitoSugar
  with ConfigComponent
  with MediumServiceComponent
  with WebsiteScraperComponent
  with TemplateBuilderComponent {

  val config: Config = new Config
  val mediumService: MediumService = mock[MediumService]
  val websiteScraper: WebsiteScraper = mock[WebsiteScraper]
  val templateBuilder: TemplateBuilder = new TemplateBuilder("<!DOCTYPE html><html><body></body></html>")
}
