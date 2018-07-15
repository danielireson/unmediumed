package unmediumed.source

import unmediumed.TestHelpers

class MediumServiceUnitSpec extends TestHelpers {
  "MediumService" should "throw an IllegalArgumentException when the scraper returns null" in {
    // given
    val websiteScraper = mock[WebsiteScraper]
    val testSubject = new MediumService(websiteScraper)
    val html: String = null

    // when
    val error = the[IllegalArgumentException] thrownBy {
      testSubject.getPost(html)
    }

    // then
    error.getMessage shouldBe "Creating MediumPost with invalid HTML"
  }

  it should "throw an IllegalArgumentException when the scraper returns an empty string" in {
    // given
    val websiteScraper = mock[WebsiteScraper]
    val testSubject = new MediumService(websiteScraper)
    val html: String = ""

    // when
    val error = the[IllegalArgumentException] thrownBy {
      testSubject.getPost(html)
    }

    // then
    error.getMessage shouldBe "Creating MediumPost with invalid HTML"
  }
}
