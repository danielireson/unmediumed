package unmediumed.parse

import unmediumed.TestHelpers

class HtmlParserUnitSpec extends TestHelpers {
  trait HtmlParserFixture {
    val validHtml: String =
      """
        |<html>
        |<head>
        |  <title>This is the title</title>
        |  <meta name="description" content="This is the description">
        |  <link rel="canonical" href="http://example.com">
        |</head>
        |<body>
        |  <h1>Header one</h1>
        |  <h2>Header two</h2>
        |  <h3>Header three</h3>
        |  <h4>Header four</h4>
        |  <h5>Header five</h5>
        |  <h6>Header six</h6>
        |  <p>Paragraph</p>
        |</body>
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

  it should "parse h1 elements" in new HtmlParserFixture {
    // given
    val testSubject = new HtmlParser

    // when
    val post: MediumPost = testSubject.parse(validHtml)

    // then
    post.elements should contain (HeaderMarkdownElement(1, "Header one"))
  }

  it should "parse h2 elements" in new HtmlParserFixture {
    // given
    val testSubject = new HtmlParser

    // when
    val post: MediumPost = testSubject.parse(validHtml)

    // then
    post.elements should contain (HeaderMarkdownElement(2, "Header two"))
  }

  it should "parse h3 elements" in new HtmlParserFixture {
    // given
    val testSubject = new HtmlParser

    // when
    val post: MediumPost = testSubject.parse(validHtml)

    // then
    post.elements should contain (HeaderMarkdownElement(3, "Header three"))
  }

  it should "parse h4 elements" in new HtmlParserFixture {
    // given
    val testSubject = new HtmlParser

    // when
    val post: MediumPost = testSubject.parse(validHtml)

    // then
    post.elements should contain (HeaderMarkdownElement(4, "Header four"))
  }

  it should "parse h5 elements" in new HtmlParserFixture {
    // given
    val testSubject = new HtmlParser

    // when
    val post: MediumPost = testSubject.parse(validHtml)

    // then
    post.elements should contain (HeaderMarkdownElement(5, "Header five"))
  }

  it should "parse h6 elements" in new HtmlParserFixture {
    // given
    val testSubject = new HtmlParser

    // when
    val post: MediumPost = testSubject.parse(validHtml)

    // then
    post.elements should contain (HeaderMarkdownElement(6, "Header six"))
  }

  it should "parse paragraph elements" in new HtmlParserFixture {
    // given
    val testSubject = new HtmlParser

    // when
    val post: MediumPost = testSubject.parse(validHtml)

    // then
    post.elements should contain (ParagraphMarkdownElement("Paragraph"))
  }
}
