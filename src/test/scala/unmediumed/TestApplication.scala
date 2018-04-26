package unmediumed

import org.scalatest.mockito.MockitoSugar

trait TestApplication extends MockitoSugar
  with Config
  with MediumService
  with WebsiteScraper {

  val config = new Config(Map())
  val mediumService = mock[MediumService]
  val websiteScraper = mock[WebsiteScraper]
}
