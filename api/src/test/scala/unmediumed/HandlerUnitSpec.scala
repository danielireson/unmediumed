package unmediumed

import com.amazonaws.services.lambda.runtime.Context
import org.mockito.Mockito.{doReturn, doThrow}
import unmediumed.parse.{HtmlParseFailedException, HtmlParser, MediumPost}
import unmediumed.request.{Input, PathParseFailedException, PathParser}
import unmediumed.response.Output
import unmediumed.source.{AnalyticsTracker, WebsiteScrapeFailedException, WebsiteScraper}

class HandlerUnitSpec extends TestHelpers {
  "Handler" should "return an api gateway formatted output" in {
    // given
    val pathParser = mock[PathParser]
    doReturn("http://example.com", "http://example.com").when(pathParser).parse("/http://example.com")

    val websiteScraper = mock[WebsiteScraper]
    doReturn("", "").when(websiteScraper).scrape("http://example.com")

    val htmlParser = mock[HtmlParser]
    val post = MediumPost(Map(), List())
    doReturn(post, post).when(htmlParser).parse("")

    val testSubject = new Handler(pathParser, websiteScraper, htmlParser, mock[AnalyticsTracker])

    val input = new Input
    input.setHttpMethod("GET")
    input.setPath("/http://example.com")

    // when
    val output: Output = testSubject.handleRequest(input, mock[Context])

    // then
    output.getStatusCode shouldBe a[Integer]
    output.getHeaders shouldBe a[java.util.Map[_, _]]
    output.getBody shouldBe a[String]
  }

  it should "return an ok response" in {
    // given
    val pathParser = mock[PathParser]
    doReturn("http://example.com", "http://example.com").when(pathParser).parse("/http://example.com")

    val websiteScraper = mock[WebsiteScraper]
    doReturn("", "").when(websiteScraper).scrape("http://example.com")

    val htmlParser = mock[HtmlParser]
    val post = MediumPost(Map(), List())
    doReturn(post, post).when(htmlParser).parse("")

    val testSubject = new Handler(pathParser, websiteScraper, htmlParser, mock[AnalyticsTracker])

    val input = new Input
    input.setHttpMethod("GET")
    input.setPath("/http://example.com")

    // when
    val output: Output = testSubject.handleRequest(input, mock[Context])

    // then
    output.getStatusCode shouldBe 200
    output.headers.get("content-type") shouldBe "text/markdown; charset=utf-8"
  }

  it should "map a parse failed exception to an unprocessable entity response" in {
    // given
    val pathParser = mock[PathParser]
    val exception = new PathParseFailedException("testing")
    doThrow(exception, exception).when(pathParser).parse("/")

    val testSubject = new Handler(pathParser, mock[WebsiteScraper], mock[HtmlParser], mock[AnalyticsTracker])

    val input = new Input
    input.setHttpMethod("GET")
    input.setPath("/")

    // when
    val output: Output = testSubject.handleRequest(input, mock[Context])

    // then
    output.getStatusCode shouldBe 422
    output.getBody shouldBe "Please provide a valid Medium URL or path"
  }

  it should "map a website scrape failed exception to a bad gateway response" in {
    // given
    val pathParser = mock[PathParser]
    doReturn("http://example.com", "http://example.com").when(pathParser).parse("/http://example.com")

    val websiteScraper = mock[WebsiteScraper]
    val exception = new WebsiteScrapeFailedException("testing")
    doThrow(exception, exception).when(websiteScraper).scrape("http://example.com")

    val testSubject = new Handler(pathParser, websiteScraper, mock[HtmlParser], mock[AnalyticsTracker])

    val input = new Input
    input.setHttpMethod("GET")
    input.setPath("/http://example.com")

    // when
    val output: Output = testSubject.handleRequest(input, mock[Context])

    // then
    output.getStatusCode shouldBe 502
    output.getBody shouldBe "Unable to fetch Medium post"
  }

  it should "map a html parse failed exception to an internal server error response" in {
    // given
    val pathParser = mock[PathParser]
    doReturn("http://example.com", "http://example.com").when(pathParser).parse("/http://example.com")

    val websiteScraper = mock[WebsiteScraper]
    doReturn("", "").when(websiteScraper).scrape("http://example.com")

    val htmlParser = mock[HtmlParser]
    val exception = new HtmlParseFailedException("testing")
    doThrow(exception, exception).when(htmlParser).parse("")

    val testSubject = new Handler(pathParser, websiteScraper, htmlParser, mock[AnalyticsTracker])

    val input = new Input
    input.setHttpMethod("GET")
    input.setPath("/http://example.com")

    // when
    val output: Output = testSubject.handleRequest(input, mock[Context])

    // then
    output.getStatusCode shouldBe 500
    output.getBody shouldBe "Unable to parse Medium post"
  }

  it should "return an internal server error response for non checked exceptions" in {
    // given
    val testSubject = new Handler(mock[PathParser], mock[WebsiteScraper], mock[HtmlParser], mock[AnalyticsTracker])
    val input = null

    // when
    val output: Output = testSubject.handleRequest(input, mock[Context])

    // then
    output.getStatusCode shouldBe 500
    output.getBody shouldBe "An unexpected error occurred"
  }
}
