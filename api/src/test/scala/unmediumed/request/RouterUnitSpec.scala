package unmediumed.request

import unmediumed.TestHelpers
import unmediumed.response.Output
import unmediumed.source.MediumService
import unmediumed.parse.MediumPost

import org.mockito.Mockito._

class RouterUnitSpec extends TestHelpers {
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
    output.headers.get("content-type") shouldBe "text/markdown; charset=utf-8"
  }

  it should "return a markdown response when request is valid" in {
    // given
    val mediumService = mock[MediumService]
    val testSubject = new Router(mediumService)
    val request = Request("GET", "/@author/title")

    val post = new MediumPost(Map(), List())
    doReturn(post, post).when(mediumService).getPost("https://medium.com/@author/title")

    // when
    val output: Output = testSubject.routeRequest(request)

    // then
    output.statusCode shouldBe 200
    output.headers.get("content-type") shouldBe "text/markdown; charset=utf-8"
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
    output.headers.get("content-type") shouldBe "text/markdown; charset=utf-8"
  }
}
