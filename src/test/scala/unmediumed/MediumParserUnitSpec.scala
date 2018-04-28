package unmediumed

class MediumParserUnitSpec extends UnitSpec {
  trait MediumParserFixture {
    val validHtml: String =
      """
        |<!DOCTYPE html>
        |<html>
        |<title>This is the title</title>
        |<meta name="description" content="This is the description">
        |<link rel="canonical" href="http://example.com">
        |<body></body>
        |</html>
      """.stripMargin
  }

  "MediumParser" should "throw an IllegalArgumentException when html is null" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser
    val html: String = null

    // then
    val error: Exception = the[IllegalArgumentException] thrownBy {
      testSubject.parse(html)
    }

    error.getMessage shouldBe "HTML is not a valid medium post"
  }

  it should "throw an IllegalArgumentException when there's no doctype tag" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser
    val html: String = validHtml.replaceFirst("<!DOCTYPE html>", "")

    // then
    val error: Exception = the[IllegalArgumentException] thrownBy {
      testSubject.parse(html)
    }

    error.getMessage shouldBe "HTML is not a valid medium post"
  }

  it should "throw an IllegalArgumentException when there's no html tag" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser
    val html: String = validHtml.replaceFirst("<html>", "").replaceFirst("</html>", "")

    // then
    val error: Exception = the[IllegalArgumentException] thrownBy {
      testSubject.parse(html)
    }

    error.getMessage shouldBe "HTML is not a valid medium post"
  }

  it should "throw an IllegalArgumentException when there's no body tag" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser
    val html: String = validHtml.replaceFirst("<body>", "").replaceFirst("</body>", "")

    // then
    val error: Exception = the[IllegalArgumentException] thrownBy {
      testSubject.parse(html)
    }

    error.getMessage shouldBe "HTML is not a valid medium post"
  }

  it should "throw an IllegalArgumentException when there's no title tag" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser
    val html: String = validHtml.replaceFirst("<title>", "").replaceFirst("</title>", "")

    // then
    val error: Exception = the[IllegalArgumentException] thrownBy {
      testSubject.parse(html)
    }

    error.getMessage shouldBe "HTML is not a valid medium post"
  }

  it should "throw an IllegalArgumentException when there's no opening description meta tag" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser
    val html: String = validHtml.replaceFirst("<meta name=\"description\"", "")

    // then
    val error: Exception = the[IllegalArgumentException] thrownBy {
      testSubject.parse(html)
    }

    error.getMessage shouldBe "HTML is not a valid medium post"
  }

  it should "throw an IllegalArgumentException when there's no canonical link tag" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser
    val html: String = validHtml.replaceFirst("<link rel=\"canonical\"", "")

    // then
    val error: Exception = the[IllegalArgumentException] thrownBy {
      testSubject.parse(html)
    }

    error.getMessage shouldBe "HTML is not a valid medium post"
  }

  it should "return a MediumPost" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser

    // when
    val post: MediumPost = testSubject.parse(validHtml)

    // then
    post shouldBe a[MediumPost]
  }

  it should "extract the title" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser

    // when
    val post: MediumPost = testSubject.parse(validHtml)

    // then
    post.meta.get("title") shouldBe Some("This is the title")
  }

  it should "extract the description" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser

    // when
    val post: MediumPost = testSubject.parse(validHtml)

    // then
    post.meta.get("description") shouldBe Some("This is the description")
  }

  it should "extract the canonical link" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser

    // when
    val post: MediumPost = testSubject.parse(validHtml)

    // then
    post.meta.get("canonical") shouldBe Some("http://example.com")
  }
}
