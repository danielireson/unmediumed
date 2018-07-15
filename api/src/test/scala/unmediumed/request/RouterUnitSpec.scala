package unmediumed.request

import unmediumed.UnitSpec
import unmediumed.response.Output
import unmediumed.source.MediumService

class RouterUnitSpec extends UnitSpec {
  "Router" should "return an api gateway formatted output" in {
    // given
    val mediumService = mock[MediumService]
    val testSubject = new Router(mediumService)
    val request = Request("GET", "/")

    // when
    val output: Output = testSubject.routeRequest(request)

    // then
    output.getStatusCode shouldBe a[Integer]
    output.getHeaders shouldBe a[java.util.Map[_, _]]
    output.getBody shouldBe a[String]
  }

  it should "return an internal server error response when request is null" in {
    // given
    val mediumService = mock[MediumService]
    val testSubject = new Router(mediumService)
    val request: Request = null

    // when
    val output: Output = testSubject.routeRequest(request)

    output.statusCode shouldBe 500
    output.headers.get("content-type") shouldBe "text/html"
  }

  it should "return a markdown response for a post route" in {
    // given
    val mediumService = mock[MediumService]
    val testSubject = new Router(mediumService)
    val request = Request("GET", "/author/title")

    // when
    val output: Output = testSubject.routeRequest(request)

    // then
    output.statusCode shouldBe 200
    output.headers.get("content-type") shouldBe "text/markdown"
  }

  it should "return an unprocessable entity response for an invalid medium url" in {
    // given
    val mediumService = mock[MediumService]
    val testSubject = new Router(mediumService)
    val request = Request("GET", "/$$$")

    // when
    val output: Output = testSubject.routeRequest(request)

    // then
    output.statusCode shouldBe 422
    output.headers.get("content-type") shouldBe "text/html"
  }
}
