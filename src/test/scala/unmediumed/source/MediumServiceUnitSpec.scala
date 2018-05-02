package unmediumed.source

import unmediumed.UnitSpec

class MediumServiceUnitSpec extends UnitSpec {
  "MediumService" should "throw an IllegalArgumentException when the scraper returns null" in {
    // given
    val testSubject = new MediumService
    val html: String = null

    // then
    val error = the[IllegalArgumentException] thrownBy {
      testSubject.getPost(html)
    }

    error.getMessage shouldBe "Creating MediumPost with invalid HTML"
  }

  it should "throw an IllegalArgumentException when the scraper returns an empty string" in {
    // given
    val testSubject = new MediumService
    val html: String = ""

    // then
    val error = the[IllegalArgumentException] thrownBy {
      testSubject.getPost(html)
    }

    error.getMessage shouldBe "Creating MediumPost with invalid HTML"
  }
}
