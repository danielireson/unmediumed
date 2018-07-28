package unmediumed.parse

import unmediumed.TestHelpers

class HtmlParserUnitSpec extends TestHelpers {
  "HtmlParser" should "throw a ParseFailedException when html is null" in {
    // given
    val testSubject = new HtmlParser
    val html = null

    // when
    val error: Exception = the[ParseFailedException] thrownBy {
      testSubject.parse(html)
    }

    // then
    error.getMessage shouldBe "HTML is not a valid Medium post"
  }

  it should "throw a ParseFailedException when there's no title tag" in {
    // given
    val testSubject = new HtmlParser
    val html = buildValidHtml().replaceFirst("<title>", "").replaceFirst("</title>", "")

    // when
    val error: Exception = the[ParseFailedException] thrownBy {
      testSubject.parse(html)
    }

    // then
    error.getMessage shouldBe "HTML is not a valid Medium post"
  }

  it should "throw a ParseFailedException when there's no opening description meta tag" in {
    // given
    val testSubject = new HtmlParser
    val html = buildValidHtml().replaceFirst("<meta name=\"description\"", "")

    // when
    val error: Exception = the[ParseFailedException] thrownBy {
      testSubject.parse(html)
    }

    // then
    error.getMessage shouldBe "HTML is not a valid Medium post"
  }

  it should "throw a ParseFailedException when there's no canonical link tag" in {
    // given
    val testSubject = new HtmlParser
    val html = buildValidHtml().replaceFirst("<link rel=\"canonical\"", "")

    // when
    val error: Exception = the[ParseFailedException] thrownBy {
      testSubject.parse(html)
    }

    // then
    error.getMessage shouldBe "HTML is not a valid Medium post"
  }

  it should "return a MediumPost" in {
    // given
    val testSubject = new HtmlParser
    val html = buildValidHtml()

    // when
    val post: MediumPost = testSubject.parse(html)

    // then
    post shouldBe a[MediumPost]
  }

  it should "extract the title" in {
    // given
    val testSubject = new HtmlParser
    val html = buildValidHtml()

    // when
    val post: MediumPost = testSubject.parse(html)

    // then
    post.meta.get("title") shouldBe Some("This is the title")
  }

  it should "extract the description" in {
    // given
    val testSubject = new HtmlParser
    val html = buildValidHtml()

    // when
    val post: MediumPost = testSubject.parse(html)

    // then
    post.meta.get("description") shouldBe Some("This is the description")
  }

  it should "extract the canonical link" in {
    // given
    val testSubject = new HtmlParser
    val html = buildValidHtml()

    // when
    val post: MediumPost = testSubject.parse(html)

    // then
    post.meta.get("canonical") shouldBe Some("http://example.com")
  }

  it should "parse h1 elements" in {
    // given
    val testSubject = new HtmlParser
    val html = buildValidHtml("<h1>Header one</h1>")

    // when
    val post: MediumPost = testSubject.parse(html)

    // then
    val el: MarkdownElement = post.elements.find(_.isInstanceOf[HeaderMarkdownElement]) getOrElse fail()
    el.markdown shouldBe "# Header one"
  }

  it should "parse h2 elements" in {
    // given
    val testSubject = new HtmlParser
    val html = buildValidHtml("<h2>Header two</h2>")

    // when
    val post: MediumPost = testSubject.parse(html)

    // then
    val el: MarkdownElement = post.elements.find(_.isInstanceOf[HeaderMarkdownElement]) getOrElse fail()
    el.markdown shouldBe "## Header two"
  }

  it should "parse h3 elements" in {
    // given
    val testSubject = new HtmlParser
    val html = buildValidHtml("<h3>Header three</h3>")

    // when
    val post: MediumPost = testSubject.parse(html)

    // then
    val el: MarkdownElement = post.elements.find(_.isInstanceOf[HeaderMarkdownElement]) getOrElse fail()
    el.markdown shouldBe "### Header three"
  }

  it should "parse h4 elements" in {
    // given
    val testSubject = new HtmlParser
    val html = buildValidHtml("<h4>Header four</h4>")

    // when
    val post: MediumPost = testSubject.parse(html)

    // then
    val el: MarkdownElement = post.elements.find(_.isInstanceOf[HeaderMarkdownElement]) getOrElse fail()
    el.markdown shouldBe "#### Header four"
  }

  it should "parse h5 elements" in {
    // given
    val testSubject = new HtmlParser
    val html = buildValidHtml("<h5>Header five</h5>")

    // when
    val post: MediumPost = testSubject.parse(html)

    // then
    val el: MarkdownElement = post.elements.find(_.isInstanceOf[HeaderMarkdownElement]) getOrElse fail()
    el.markdown shouldBe "##### Header five"
  }

  it should "parse h6 elements" in {
    // given
    val testSubject = new HtmlParser
    val html = buildValidHtml("<h6>Header six</h6>")

    // when
    val post: MediumPost = testSubject.parse(html)

    // then
    val el: MarkdownElement = post.elements.find(_.isInstanceOf[HeaderMarkdownElement]) getOrElse fail()
    el.markdown shouldBe "###### Header six"
  }

  it should "parse paragraph elements" in {
    // given
    val testSubject = new HtmlParser
    val html = buildValidHtml("<p>Paragraph</p>")

    // when
    val post: MediumPost = testSubject.parse(html)

    // then
    val el: MarkdownElement = post.elements.find(_.isInstanceOf[ParagraphMarkdownElement]) getOrElse fail()
    el.markdown shouldBe "Paragraph"
  }

  it should "parse image elements" in {
    // given
    val testSubject = new HtmlParser
    val html = buildValidHtml("<img src=\"http://example.com\" />")

    // when
    val post: MediumPost = testSubject.parse(html)

    // then
    val el: MarkdownElement = post.elements.find(_.isInstanceOf[ImageMarkdownElement]) getOrElse fail()
    el.markdown shouldBe "![](http://example.com)"
  }

  it should "parse unordered list elements" in {
    // given
    val testSubject = new HtmlParser
    val html = buildValidHtml("""
        |<ul>
        |  <li>One</li>
        |  <li>Two</li>
        |  <li>Three</li>
        |</ul>
      """.stripMargin)

    // when
    val post: MediumPost = testSubject.parse(html)

    // then
    val el: MarkdownElement = post.elements.find(_.isInstanceOf[UnorderedMarkdownElement]) getOrElse fail()
    el.markdown shouldBe
      """
        |* One
        |* Two
        |* Three
      """.stripMargin.trim
  }

  it should "parse ordered list elements" in {
    // given
    val testSubject = new HtmlParser
    val html = buildValidHtml("""
        |<ol>
        |  <li>One</li>
        |  <li>Two</li>
        |  <li>Three</li>
        |</ol>
      """.stripMargin)

    // when
    val post: MediumPost = testSubject.parse(html)

    // then
    val el: MarkdownElement = post.elements.find(_.isInstanceOf[OrderedMarkdownElement]) getOrElse fail()
    el.markdown shouldBe
      """
        |1. One
        |2. Two
        |3. Three
      """.stripMargin.trim
  }

  it should "parse blockquote elements" in {
    // given
    val testSubject = new HtmlParser
    val html = buildValidHtml("<blockquote>Quote</blockquote>")

    // when
    val post: MediumPost = testSubject.parse(html)

    // then
    val el: MarkdownElement = post.elements.find(_.isInstanceOf[BlockquoteMarkdownElement]) getOrElse fail()
    el.markdown shouldBe "> Quote"
  }

  it should "parse code block elements" in {
    // given
    val testSubject = new HtmlParser
    val html = buildValidHtml("<pre>{<br>  \"message\": \"hello world\"<br>}</pre>")

    // when
    val post: MediumPost = testSubject.parse(html)

    // then
    val el: MarkdownElement = post.elements.find(_.isInstanceOf[CodeblockMarkdownElement]) getOrElse fail()
    el.markdown shouldBe
      """
        |```
        |{
        |  "message": "hello world"
        |}
        |```
      """.stripMargin.trim
  }

  it should "parse foreign characters" in {
    // given
    val testSubject = new HtmlParser
    val html = buildValidHtml("<p>Ich hiesße unmediumed</p>")

    // when
    val post: MediumPost = testSubject.parse(html)

    // then
    val el: MarkdownElement = post.elements.find(_.isInstanceOf[ParagraphMarkdownElement]) getOrElse fail()
    el.markdown shouldBe "Ich hiesße unmediumed"
  }

  it should "parse bold elements" in {
    // given
    val testSubject = new HtmlParser
    val html = buildValidHtml("<p><strong>Bold</strong></p>")

    // when
    val post: MediumPost = testSubject.parse(html)

    // then
    val el: MarkdownElement = post.elements.find(_.isInstanceOf[ParagraphMarkdownElement]) getOrElse fail()
    el.markdown shouldBe "**Bold**"
  }

  it should "parse italic elements" in {
    // given
    val testSubject = new HtmlParser
    val html = buildValidHtml("<p><em>Italics</em></p>")

    // when
    val post: MediumPost = testSubject.parse(html)

    // then
    val el: MarkdownElement = post.elements.find(_.isInstanceOf[ParagraphMarkdownElement]) getOrElse fail()
    el.markdown shouldBe "*Italics*"
  }

  it should "remove the footer" in {
    // given
    val testSubject = new HtmlParser

    val footerText = "From a quick cheer to a standing ovation, clap to show how much you enjoyed this story."
    val html = buildValidHtml(s"""
        |<p>Content</p>
        |<p>$footerText</p>
      """.stripMargin)

    // when
    val post: MediumPost = testSubject.parse(html)

    // then
    post.markdown contains footerText shouldBe false
  }

  private def buildValidHtml(innerHtml: String = "<p>Content</p>"): String = {
    s"""
      |<html>
      |<head>
      |  <title>This is the title</title>
      |  <meta name="description" content="This is the description">
      |  <link rel="canonical" href="http://example.com">
      |</head>
      |<body>
      |  <main>
      |    <article>
      |      <section>$innerHtml</section>
      |    </article>
      |  </main>
      |</body>
      |</html>
    """.stripMargin
  }
}
