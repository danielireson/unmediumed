package unmediumed

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class WebsiteScraperUnitSpec extends FlatSpec with Matchers with MockitoSugar {
  "WebsiteScraper" should "throw an IllegalArgumentException when url is null" in {
    // given
    val testSubject = new WebsiteScraper
    val url = null

    // then
    intercept[IllegalArgumentException] {
      testSubject.scrape(url)
    }
  }

  it should "throw an IllegalArgumentException when url is an empty string" in {
    // given
    val testSubject = new WebsiteScraper
    val url = ""

    // then
    intercept[IllegalArgumentException] {
      testSubject.scrape(url)
    }
  }
}
