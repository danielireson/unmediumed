package unmediumed

import java.io.ByteArrayInputStream

import org.mockito.Mockito.doThrow

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

  it should "scrape the html from an input stream" in {
    // given
    val testSubject: WebsiteScraperLocal = new WebsiteScraper
    val inputStream = new ByteArrayInputStream("abcdef".getBytes)

    // then
    val html = testSubject.scrapeFromInputStream(inputStream)

    html shouldBe "abcdef"
  }

  it should "throw a WebsiteScrapeFailedException when the input stream can't be read" in {
    // given
    val testSubject: WebsiteScraperLocal = new WebsiteScraper
    val inputStream = mock[ByteArrayInputStream]

    doThrow(new RuntimeException).when(inputStream).read()

    // then
    val error = the[WebsiteScrapeFailedException] thrownBy {
      testSubject.scrapeFromInputStream(inputStream)
    }

    error.getMessage shouldBe "Unable to read input stream"
  }
}
