package unmediumed

import com.amazonaws.services.lambda.runtime.Context
import org.mockito.Mockito._
import org.mockito.ArgumentMatchers._
import unmediumed.parse.{HtmlParser, MediumPost}
import unmediumed.request.{Input, PathParser}
import unmediumed.response.Output
import unmediumed.source.WebsiteScraper

class HandlerUnitSpec extends TestHelpers {
  private trait HandlerFixture {
    val pathParser: PathParser = mock[PathParser]
    doReturn("", "").when(pathParser).parse(any())

    val websiteScraper: WebsiteScraper = mock[WebsiteScraper]
    doReturn("", "").when(websiteScraper).scrape(anyString())

    val htmlParser: HtmlParser = mock[HtmlParser]
    val post = MediumPost(Map(), List())
    doReturn(post, post).when(htmlParser).parse(anyString())
  }

  "Handler" should "return an api gateway formatted output" in new HandlerFixture {
    // given
    val testSubject = new Handler(pathParser, websiteScraper, htmlParser)

    val input = new Input
    input.setHttpMethod("GET")
    input.setPath("/")

    // when
    val output: Output = testSubject.handleRequest(input, mock[Context])

    // then
    output.getStatusCode shouldBe a[Integer]
    output.getHeaders shouldBe a[java.util.Map[_, _]]
    output.getBody shouldBe a[String]
  }

  it should "return an internal server error response when request is null" in new HandlerFixture {
    // given
    val testSubject = new Handler(pathParser, websiteScraper, htmlParser)

    val input: Input = null

    // when
    val output: Output = testSubject.handleRequest(input, mock[Context])

    output.statusCode shouldBe 500
    output.headers.get("content-type") shouldBe "text/markdown; charset=utf-8"
  }

  it should "return a ok response when request is valid" in new HandlerFixture {
    // given
    val testSubject = new Handler(pathParser, websiteScraper, htmlParser)

    val input = new Input
    input.setHttpMethod("GET")
    input.setPath("/@author/title")

    // when
    val output: Output = testSubject.handleRequest(input, mock[Context])

    // then
    output.statusCode shouldBe 200
    output.headers.get("content-type") shouldBe "text/markdown; charset=utf-8"
  }
}
