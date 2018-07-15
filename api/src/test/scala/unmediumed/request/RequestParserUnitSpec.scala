package unmediumed.request

import unmediumed.UnitSpec

class RequestParserUnitSpec extends UnitSpec {
  it should "throw a RequestParseFailedException for an invalid url" in {
    // given
    val testSubject = new RequestParser
    val request = Request("GET", "$$$")

    // when
    val error = the[RequestParseFailedException] thrownBy {
      testSubject.getPostUrl(request)
    }

    // then
    error.getMessage shouldBe "Invalid medium url provided"
  }

  it should "get the post url for a custom domain path with http" in {
    // given
    val testSubject = new RequestParser
    val request = Request("GET", "/http://example.com/title")

    // when
    val url = testSubject.getPostUrl(request)

    // then
    url shouldBe "http://example.com/title"
  }

  it should "get the post url for a custom domain path with https" in {
    // given
    val testSubject = new RequestParser
    val request = Request("GET", "/https://example.com/title")

    // when
    val url = testSubject.getPostUrl(request)

    // then
    url shouldBe "https://example.com/title"
  }

  it should "get the post url for a medium domain path with http" in {
    // given
    val testSubject = new RequestParser
    val request = Request("GET", "/http://medium.com/author/title")

    // when
    val url = testSubject.getPostUrl(request)

    // then
    url shouldBe "http://medium.com/author/title"
  }

  it should "get the post url for a medium domain path with https" in {
    // given
    val testSubject = new RequestParser
    val request = Request("GET", "/https://medium.com/author/title")

    // when
    val url = testSubject.getPostUrl(request)

    // then
    url shouldBe "https://medium.com/author/title"
  }

  it should "get the post url for an author path" in {
    // given
    val testSubject = new RequestParser
    val request = Request("GET", "/@author/title")

    // when
    val url = testSubject.getPostUrl(request)

    // then
    url shouldBe "https://medium.com/@author/title"
  }

  it should "get the post url for a publication path" in {
    // given
    val testSubject = new RequestParser
    val request = Request("GET", "/publication/title")

    // when
    val url = testSubject.getPostUrl(request)

    // then
    url shouldBe "https://medium.com/publication/title"
  }
}
