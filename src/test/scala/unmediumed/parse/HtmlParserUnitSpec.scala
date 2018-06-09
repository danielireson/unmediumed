package unmediumed.parse

import unmediumed.UnitSpec

class HtmlParserUnitSpec extends UnitSpec {
  trait HtmlParserFixture {
    val validHtml: String =
      """
        |<html>
        |<title>This is the title</title>
        |<meta name="description" content="This is the description">
        |<link rel="canonical" href="http://example.com">
        |<body></body>
        |</html>
      """.stripMargin
  }

  "MediumParser" should "throw a ParseFailedException when html is null" in new HtmlParserFixture {
    // given
    val testSubject = new HtmlParser
    val html: String = null

    // when
    val error: Exception = the[ParseFailedException] thrownBy {
      testSubject.parse(html)
    }

    // then
    error.getMessage shouldBe "HTML is not a valid medium post"
  }

  it should "throw a ParseFailedException when there's no title tag" in new HtmlParserFixture {
    // given
    val testSubject = new HtmlParser
    val html: String = validHtml.replaceFirst("<title>", "").replaceFirst("</title>", "")

    // when
    val error: Exception = the[ParseFailedException] thrownBy {
      testSubject.parse(html)
    }

    // then
    error.getMessage shouldBe "HTML is not a valid medium post"
  }

  it should "throw a ParseFailedException when there's no opening description meta tag" in new HtmlParserFixture {
    // given
    val testSubject = new HtmlParser
    val html: String = validHtml.replaceFirst("<meta name=\"description\"", "")

    // when
    val error: Exception = the[ParseFailedException] thrownBy {
      testSubject.parse(html)
    }

    // then
    error.getMessage shouldBe "HTML is not a valid medium post"
  }

  it should "throw a ParseFailedException when there's no canonical link tag" in new HtmlParserFixture {
    // given
    val testSubject = new HtmlParser
    val html: String = validHtml.replaceFirst("<link rel=\"canonical\"", "")

    // when
    val error: Exception = the[ParseFailedException] thrownBy {
      testSubject.parse(html)
    }

    // then
    error.getMessage shouldBe "HTML is not a valid medium post"
  }

  it should "return a MediumPost" in new HtmlParserFixture {
    // given
    val testSubject = new HtmlParser

    // when
    val post: MediumPost = testSubject.parse(validHtml)

    // then
    post shouldBe a[MediumPost]
  }

  it should "extract the title" in new HtmlParserFixture {
    // given
    val testSubject = new HtmlParser

    // when
    val post: MediumPost = testSubject.parse(validHtml)

    // then
    post.meta.get("title") shouldBe Some("This is the title")
  }

  it should "extract the description" in new HtmlParserFixture {
    // given
    val testSubject = new HtmlParser

    // when
    val post: MediumPost = testSubject.parse(validHtml)

    // then
    post.meta.get("description") shouldBe Some("This is the description")
  }

  it should "extract the canonical link" in new HtmlParserFixture {
    // given
    val testSubject = new HtmlParser

    // when
    val post: MediumPost = testSubject.parse(validHtml)

    // then
    post.meta.get("canonical") shouldBe Some("http://example.com")
  }
}
