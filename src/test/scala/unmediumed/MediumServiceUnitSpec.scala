package unmediumed

class MediumServiceUnitSpec extends UnitSpec {
  "MediumService" should "throw an IllegalArgumentException when the scraper returns null" in {
    // given
    val testSubject: MediumServiceLocal = new MediumService
    val html: String = null

    // then
    intercept[IllegalArgumentException] {
      testSubject.getPost(html)
    }
  }

  it should "throw an IllegalArgumentException when the scraper returns an empty string" in {
    // given
    val testSubject: MediumServiceLocal = new MediumService
    val html: String = ""

    // then
    intercept[IllegalArgumentException] {
      testSubject.getPost(html)
    }
  }
}
