package unmediumed.source

import unmediumed.UnitSpec

class MediumServiceUnitSpec extends UnitSpec {
  "MediumService" should "throw an IllegalArgumentException when the scraper returns null" in {
    // given
    val testSubject = new MediumService
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
    val testSubject = new MediumService
    val html: String = ""

    // when
    val error = the[IllegalArgumentException] thrownBy {
      testSubject.getPost(html)
    }

    // then
    error.getMessage shouldBe "Creating MediumPost with invalid HTML"
  }
}
