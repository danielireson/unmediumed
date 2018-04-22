package unmediumed

import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.mockito.MockitoSugar

class MediumServiceUnitSpec extends FlatSpec with Matchers with MockitoSugar {
  "MediumService" should "throw an IllegalArgumentException when the scraper returns null" in {
    // given
    val testSubject = new MediumService
    val html = null

    // then
    intercept[IllegalArgumentException] {
      testSubject.getPost(html)
    }
  }

  it should "throw an IllegalArgumentException when the scraper returns an empty string" in {
    // given
    val testSubject = new MediumService
    val html = ""

    // then
    intercept[IllegalArgumentException] {
      testSubject.getPost(html)
    }
  }
}
