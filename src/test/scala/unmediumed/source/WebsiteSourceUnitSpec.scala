package unmediumed.source

import java.io.ByteArrayInputStream

import org.mockito.Mockito.doThrow
import unmediumed.UnitSpec

class WebsiteSourceUnitSpec extends UnitSpec {
  "WebsiteSource" should "throw a WebsiteScrapeFailedException when url is null" in {
    // given
    val testSubject = new WebsiteSource
    val url: String = null

    // then
    val error = the[WebsiteScrapeFailedException] thrownBy {
      testSubject.scrape(url)
    }

    error.getMessage shouldBe "Unable to create input stream"
  }

  it should "scrape the html from an input stream" in {
    // given
    val testSubject = new WebsiteSource
    val inputStream = new ByteArrayInputStream("abcdef".getBytes)

    // then
    val html = testSubject.scrapeFromInputStream(inputStream)

    html shouldBe "abcdef"
  }

  it should "throw a WebsiteScrapeFailedException when the input stream can't be read" in {
    // given
    val testSubject = new WebsiteSource
    val inputStream = mock[ByteArrayInputStream]

    doThrow(new RuntimeException).when(inputStream).read()

    // then
    val error = the[WebsiteScrapeFailedException] thrownBy {
      testSubject.scrapeFromInputStream(inputStream)
    }

    error.getMessage shouldBe "Unable to read input stream"
  }
}
