package unmediumed.core

import org.scalatest.mockito.MockitoSugar
import unmediumed.response.TemplateBuilderComponent
import unmediumed.source._

trait TestComponentRegistry extends MockitoSugar
  with ConfigComponent
  with MediumServiceComponent
  with WebsiteSourceComponent
  with TemplateBuilderComponent {

  val config: Config = new Config(Map())
  val mediumService: MediumService = mock[MediumService]
  val websiteSource: WebsiteSource = mock[WebsiteSource]
  val templateBuilder: TemplateBuilder = new TemplateBuilder("<!DOCTYPE html><html><body></body></html>")
}
