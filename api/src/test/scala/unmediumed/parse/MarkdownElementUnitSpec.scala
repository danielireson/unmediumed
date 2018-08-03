package unmediumed.parse

import unmediumed.TestHelpers

class MarkdownElementUnitSpec extends TestHelpers {
  "HeaderMarkdownElement" should "build h1" in {
    // given
    val testSubject = HeaderMarkdownElement(1, "Header one")

    // then
    testSubject.markdown shouldBe "# Header one"
  }

  it should "build h2" in {
    // given
    val testSubject = HeaderMarkdownElement(2, "Header two")

    // then
    testSubject.markdown shouldBe "## Header two"
  }

  it should "build h3" in {
    // given
    val testSubject = HeaderMarkdownElement(3, "Header three")

    // then
    testSubject.markdown shouldBe "### Header three"
  }

  it should "build h4" in {
    // given
    val testSubject = HeaderMarkdownElement(4, "Header four")

    // then
    testSubject.markdown shouldBe "#### Header four"
  }

  it should "build h5" in {
    // given
    val testSubject = HeaderMarkdownElement(5, "Header five")

    // then
    testSubject.markdown shouldBe "##### Header five"
  }

  it should "build h6" in {
    // given
    val testSubject = HeaderMarkdownElement(6, "Header six")

    // then
    testSubject.markdown shouldBe "###### Header six"
  }

  it should "build headers with links" in {
    // given
    val testSubject = HeaderMarkdownElement(6, "<a href=\"http://example.com\">Example</a>")

    // then
    testSubject.markdown shouldBe "###### [Example](http://example.com)"
  }

  "ParagraphMarkdownElement" should "build paragraphs" in {
    // given
    val testSubject = ParagraphMarkdownElement("Paragraph")

    // then
    testSubject.markdown shouldBe "Paragraph"
  }

  it should "build paragraphs with bold formatting" in {
    // given
    val testSubject = ParagraphMarkdownElement("<strong>Bold</strong>")

    // then
    testSubject.markdown shouldBe "**Bold**"
  }

  it should "build paragraphs with italic formatting" in {
    // given
    val testSubject = ParagraphMarkdownElement("<em>Italic</em>")

    // then
    testSubject.markdown shouldBe "*Italic*"
  }

  it should "build paragraphs with links" in {
    // given
    val testSubject = ParagraphMarkdownElement("<a href=\"http://example.com\">Example</a>")

    // then
    testSubject.markdown shouldBe "[Example](http://example.com)"
  }

  it should "build paragraphs with foreign characters" in {
    // given
    val testSubject = ParagraphMarkdownElement("Ich hiesße unmediumed")

    // then
    testSubject.markdown shouldBe "Ich hiesße unmediumed"
  }

  "ImageMarkdownElement" should "build images" in {
    // given
    val testSubject = ImageMarkdownElement("http://example.com")

    // then
    testSubject.markdown shouldBe "![](http://example.com)"
  }

  "UnorderedMarkdownElement" should "build unordered lists" in {
    // given
    val testSubject = UnorderedMarkdownElement(List("One", "Two", "Three"))

    // then
    testSubject.markdown shouldBe "* One\n* Two\n* Three"
  }

  "OrderedMarkdownElement" should "build ordered lists" in {
    // given
    val testSubject = OrderedMarkdownElement(List("One", "Two", "Three"))

    // then
    testSubject.markdown shouldBe "1. One\n2. Two\n3. Three"
  }

  "BlockquoteMarkdownElement" should "build block quotes" in {
    // given
    val testSubject = BlockquoteMarkdownElement("Quote")

    // then
    testSubject.markdown shouldBe "> Quote"
  }

  "CodeblockMarkdownElement" should "build code blocks" in {
    // given
    val testSubject = CodeblockMarkdownElement("{\n\"message\": \"hello world\"\n}")

    // then
    testSubject.markdown shouldBe "```\n{\n\"message\": \"hello world\"\n}\n```"
  }
}
