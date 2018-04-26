package unmediumed

class WebsiteScraperUnitSpec extends UnitSpec {
  "WebsiteScraper" should "throw an IllegalArgumentException when url is null" in {
    // given
    val testSubject: WebsiteScraperLocal = new WebsiteScraper
    val url: String = null

    // then
    intercept[IllegalArgumentException] {
      testSubject.scrape(url)
    }
  }

  it should "throw an IllegalArgumentException when url is an empty string" in {
    // given
    val testSubject: WebsiteScraperLocal = new WebsiteScraper
    val url: String = ""

    // then
    intercept[IllegalArgumentException] {
      testSubject.scrape(url)
    }
  }
}
