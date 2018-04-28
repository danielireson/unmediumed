package unmediumed

import java.io.ByteArrayInputStream

class WebsiteScraperUnitSpec extends UnitSpec {
  "WebsiteScraper" should "throw a WebsiteScrapeFailedException when url is null" in {
    // given
    val testSubject: WebsiteScraperLocal = new WebsiteScraper
    val url: String = null

    // then
    val error = the[WebsiteScrapeFailedException] thrownBy {
      testSubject.scrape(url)
    }

    error.getMessage shouldBe "Unable to create input stream"
  }

  it should "scrape the html for a valid input stream" in {
    // given
    val testSubject: WebsiteScraperLocal = new WebsiteScraper
    val inputStream = new ByteArrayInputStream("abcdef".getBytes)

    // then
    val html = testSubject.scrapeFromInputStream(inputStream)

    html shouldBe "abcdef"
  }
}
