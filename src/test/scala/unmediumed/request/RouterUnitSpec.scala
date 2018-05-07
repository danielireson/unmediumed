package unmediumed.request

import unmediumed.UnitSpec
import unmediumed.response.TemplateBuilderComponent

class RouterUnitSpec extends UnitSpec {
  private trait ComponentRegistry extends TemplateBuilderComponent {
    val templateBuilder = new TemplateBuilder("<!DOCTYPE html><html><body></body></html>")
  }

  "Router" should "return an api gateway formatted output" in {
    // given
    val testSubject = new Router with ComponentRegistry
    val request = Request("GET", "/")

    // when
    val output = testSubject.routeRequest(request)

    // then
    output.getStatusCode shouldBe a[Integer]
    output.getHeaders shouldBe a[java.util.Map[_, _]]
    output.getBody shouldBe a[String]
  }

  it should "return a http 200 html response for index requests" in {
    // given
    val testSubject = new Router with ComponentRegistry
    val request = Request("GET", "/")

    // when
    val output = testSubject.routeRequest(request)

    // then
    output.statusCode shouldBe 200
    output.headers.get("content-type") shouldBe "text/html"

    List("<!DOCTYPE html>", "<html>", "</html>", "<body>", "</body>")
      .foreach(tag => output.body should include (tag))
  }

  it should "return a http 200 markdown response for non-index requests" in {
    // given
    val testSubject = new Router with ComponentRegistry
    val request = Request("GET", "/other")

    // when
    val output = testSubject.routeRequest(request)

    // then
    output.statusCode shouldBe 200
    output.headers.get("content-type") shouldBe "text/markdown"
  }

  it should "return a http 500 internal server error when request is null" in {
    // given
    val testSubject = new Router with ComponentRegistry
    val request = null

    // when
    val output = testSubject.routeRequest(request)

    output.statusCode shouldBe 500
    output.headers.get("content-type") shouldBe "text/markdown"
  }
}
