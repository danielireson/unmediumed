package unmediumed

import org.scalatest.mockito.MockitoSugar
import unmediumed.components.{MediumService, WebsiteScraper}

trait TestApplication extends MockitoSugar
  with components.Config
  with MediumService
  with WebsiteScraper {

  val config = new Config(Map())
  val mediumService = mock[MediumService]
  val websiteScraper = mock[WebsiteScraper]
}
