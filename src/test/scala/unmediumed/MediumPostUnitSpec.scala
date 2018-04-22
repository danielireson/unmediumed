package unmediumed

import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.mockito.MockitoSugar

class MediumPostUnitSpec extends FlatSpec with Matchers with MockitoSugar {
  "MediumPost" should "return the post in html" in {
    // given
    val testSubject = new MediumPost("<h1>Testing</h1>")

    // when
    val html = testSubject.html

    // then
    html shouldBe "<h1>Testing</h1>"
  }
}
