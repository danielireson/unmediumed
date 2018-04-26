package unmediumed

class MediumPostUnitSpec extends UnitSpec {
  "MediumPost" should "return the post in html" in {
    // given
    val testSubject = new MediumPost("<h1>Testing</h1>")

    // when
    val html = testSubject.html

    // then
    html shouldBe "<h1>Testing</h1>"
  }
}
