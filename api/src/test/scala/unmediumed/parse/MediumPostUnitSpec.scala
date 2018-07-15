package unmediumed.parse

import unmediumed.TestHelpers

class MediumPostUnitSpec extends TestHelpers {
  "MediumPost" should "return the post in html" in {
    // given
    val testSubject = new MediumPost(
      meta = Map(),
      markdown = List(),
      html = "<h1>Testing</h1>"
    )

    // when
    val html: String = testSubject.html

    // then
    html shouldBe "<h1>Testing</h1>"
  }
}
