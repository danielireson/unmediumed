package unmediumed.parse

import unmediumed.TestHelpers

class HtmlParserUnitSpec extends TestHelpers {
  private trait HtmlParserFixture {
    val validHtml: String =
      """
        |<html>
        |<head>
        |  <title>This is the title</title>
        |  <meta name="description" content="This is the description">
        |  <link rel="canonical" href="http://example.com">
        |</head>
        |<body>
        |  <main>
        |    <article>
        |      <section>
        |        <h1>Header one</h1>
        |        <h2>Header two</h2>
        |        <h3>Header three</h3>
        |        <h4>Header four</h4>
        |        <h5>Header five</h5>
        |        <h6>Header six</h6>
        |        <p>Paragraph</p>
        |        <img src="http://example.com" />
        |      </section>
        |    </article>
        |  </main>
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
    val el: MarkdownElement = post.findElement(HeaderMarkdownElement(1, "Header one")) getOrElse fail()
    el.markdown shouldBe "# Header one"
  }

  it should "parse h2 elements" in new HtmlParserFixture {
    // given
    val testSubject = new HtmlParser

    // when
    val post: MediumPost = testSubject.parse(validHtml)

    // then
    val el: MarkdownElement = post.findElement(HeaderMarkdownElement(2, "Header two")) getOrElse fail()
    el.markdown shouldBe "## Header two"
  }

  it should "parse h3 elements" in new HtmlParserFixture {
    // given
    val testSubject = new HtmlParser

    // when
    val post: MediumPost = testSubject.parse(validHtml)

    // then
    val el: MarkdownElement = post.findElement(HeaderMarkdownElement(3, "Header three")) getOrElse fail()
    el.markdown shouldBe "### Header three"
  }

  it should "parse h4 elements" in new HtmlParserFixture {
    // given
    val testSubject = new HtmlParser

    // when
    val post: MediumPost = testSubject.parse(validHtml)

    // then
    val el: MarkdownElement = post.findElement(HeaderMarkdownElement(4, "Header four")) getOrElse fail()
    el.markdown shouldBe "#### Header four"
  }

  it should "parse h5 elements" in new HtmlParserFixture {
    // given
    val testSubject = new HtmlParser

    // when
    val post: MediumPost = testSubject.parse(validHtml)

    // then
    val el: MarkdownElement = post.findElement(HeaderMarkdownElement(5, "Header five")) getOrElse fail()
    el.markdown shouldBe "##### Header five"
  }

  it should "parse h6 elements" in new HtmlParserFixture {
    // given
    val testSubject = new HtmlParser

    // when
    val post: MediumPost = testSubject.parse(validHtml)

    // then
    val el: MarkdownElement = post.findElement(HeaderMarkdownElement(6, "Header six")) getOrElse fail()
    el.markdown shouldBe "###### Header six"
  }

  it should "parse paragraph elements" in new HtmlParserFixture {
    // given
    val testSubject = new HtmlParser

    // when
    val post: MediumPost = testSubject.parse(validHtml)

    // then
    val el: MarkdownElement = post.findElement(ParagraphMarkdownElement("Paragraph")) getOrElse fail()
    el.markdown shouldBe "Paragraph"
  }

  it should "parse image elements" in new HtmlParserFixture {
    // given
    val testSubject = new HtmlParser

    // when
    val post: MediumPost = testSubject.parse(validHtml)

    // then
    val el: MarkdownElement = post.findElement(ImageMarkdownElement("http://example.com")) getOrElse fail()
    el.markdown shouldBe "![](http://example.com)"
  }
}
