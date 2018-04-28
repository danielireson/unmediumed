package unmediumed

class WebsiteScraperUnitSpec extends UnitSpec {
  "WebsiteScraper" should "throw an IllegalArgumentException when url is null" in {
    // given
    val testSubject: WebsiteScraperLocal = new WebsiteScraper
    val url: String = null

    // then
    val error = the[IllegalArgumentException] thrownBy {
      testSubject.scrape(url)
    }

    error.getMessage shouldBe "Creating input stream from invalid URL"
  }

  it should "throw an IllegalArgumentException when url is an empty string" in {
    // given
    val testSubject: WebsiteScraperLocal = new WebsiteScraper
    val url: String = ""

    // then
    val error = the[IllegalArgumentException] thrownBy {
      testSubject.scrape(url)
    }

    error.getMessage shouldBe "Creating input stream from invalid URL"
  }

  it should "throw an IllegalArgumentException when url is invalid" in {
    // given
    val testSubject: WebsiteScraperLocal = new WebsiteScraper
    val url: String = "example.com"

    // then
    val error = the[IllegalArgumentException] thrownBy {
      testSubject.scrape(url)
    }

    error.getMessage shouldBe "Creating input stream from invalid URL"
  }

  it should "throw a WebsiteScrapeFailedException when an input stream can't be created" in {
    // given
    val testSubject: WebsiteScraperLocal = new WebsiteScraper
    val url: String = "http://example.com"

    // then
    val error = the[WebsiteScrapeFailedException] thrownBy {
      testSubject.scrape(url)
    }

    error.getMessage shouldBe "Unable to create input stream"
  }
}
