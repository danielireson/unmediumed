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
    val html: String = "invalid"

    // then
    intercept[IllegalArgumentException] {
      testSubject.parse(html)
    }
  }

  it should "throw an IllegalArgumentException when there's no <html> tags" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser
    val html: String = "<!DOCTYPE html><body></body>"

    // then
    intercept[IllegalArgumentException] {
      testSubject.parse(html)
    }
  }

  it should "throw an IllegalArgumentException when there's no <body> tags" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser
    val html: String = "<!DOCTYPE html><html></html>"

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
