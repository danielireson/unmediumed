package unmediumed.request

import unmediumed.TestHelpers

class PathParserUnitSpec extends TestHelpers {
  "PathParser" should "throw a PathParseFailedException for an invalid url" in {
    // given
    val testSubject = new PathParser

    // when
    val error = the[PathParseFailedException] thrownBy {
      testSubject.parse("$$$")
    }

    // then
    error.getMessage shouldBe "Invalid Medium URL provided"
  }

  it should "throw a PathParseFailedException when no url has been provided" in {
    // given
    val testSubject = new PathParser

    // when
    val error = the[PathParseFailedException] thrownBy {
      testSubject.parse("/")
    }

    // then
    error.getMessage shouldBe "Please provide a Medium URL or path"
  }

  it should "get the post url for a custom domain path with http" in {
    // given
    val testSubject = new PathParser

    // when
    val url = testSubject.parse("/http://example.com/title")

    // then
    url shouldBe "http://example.com/title"
  }

  it should "get the post url for a custom domain path with https" in {
    // given
    val testSubject = new PathParser

    // when
    val url = testSubject.parse("/https://example.com/title")

    // then
    url shouldBe "https://example.com/title"
  }

  it should "get the post url for a medium domain path with http" in {
    // given
    val testSubject = new PathParser

    // when
    val url = testSubject.parse("/http://medium.com/author/title")

    // then
    url shouldBe "http://medium.com/author/title"
  }

  it should "get the post url for a medium domain path with https" in {
    // given
    val testSubject = new PathParser

    // when
    val url = testSubject.parse("/https://medium.com/author/title")

    // then
    url shouldBe "https://medium.com/author/title"
  }

  it should "get the post url for an author path" in {
    // given
    val testSubject = new PathParser

    // when
    val url = testSubject.parse("/@author/title")

    // then
    url shouldBe "https://medium.com/@author/title"
  }

  it should "get the post url for a publication path" in {
    // given
    val testSubject = new PathParser

    // when
    val url = testSubject.parse("/publication/title")

    // then
    url shouldBe "https://medium.com/publication/title"
  }
}
