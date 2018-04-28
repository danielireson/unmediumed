package unmediumed

class MediumParserUnitSpec extends UnitSpec {
  trait MediumParserFixture {
    val validHtml: String =
      """
        |<!DOCTYPE html>
        |<html>
        |<body></body>
        |</html>
      """.stripMargin
  }

  "MediumParser" should "throw an IllegalArgumentException when html is null" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser
    val html: String = null

    // then
    intercept[IllegalArgumentException] {
      testSubject.parse(html)
    }
  }

  it should "throw an IllegalArgumentException when there's no doctype tag" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser
    val html: String = "<html><html><body></body></html>"

    // then
    intercept[IllegalArgumentException] {
      testSubject.parse(html)
    }
  }

  it should "throw an IllegalArgumentException when there's no opening html tag" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser
    val html: String = "<!DOCTYPE html><body></body></html>"

    // then
    intercept[IllegalArgumentException] {
      testSubject.parse(html)
    }
  }

  it should "throw an IllegalArgumentException when there's no closing html tag" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser
    val html: String = "<!DOCTYPE html><html><body></body>"

    // then
    intercept[IllegalArgumentException] {
      testSubject.parse(html)
    }
  }

  it should "throw an IllegalArgumentException when there's no opening body tag" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser
    val html: String = "<!DOCTYPE html><html></body></html>"

    // then
    intercept[IllegalArgumentException] {
      testSubject.parse(html)
    }
  }

  it should "throw an IllegalArgumentException when there's no closing body tag" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser
    val html: String = "<!DOCTYPE html><html><body></html>"

    // then
    intercept[IllegalArgumentException] {
      testSubject.parse(html)
    }
  }

  it should "return a list of MarkdownElement" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser

    // when
    val elements: List[MarkdownElement] = testSubject.parse(validHtml)

    // then
    elements shouldBe List()
  }
}
