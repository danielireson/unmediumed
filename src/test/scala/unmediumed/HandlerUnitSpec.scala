package unmediumed

import java.{util => ju}

import com.amazonaws.services.lambda.runtime.Context
import unmediumed.request.Input

class HandlerUnitSpec extends UnitSpec {
  "Handler" should "return an api gateway formatted output" in {
    // given
    val testSubject = new Handler
    val input = new Input
    val context = mock[Context]

    // when
    val output = testSubject.handleRequest(input, context)

    // then
    output.getStatusCode shouldBe a[Integer]
    output.getHeaders shouldBe a[ju.Map[_, _]]
    output.getBody shouldBe a[String]
  }

  it should "throw an IllegalArgumentException when input is null" in {
    // given
    val testSubject = new Handler
    val input = null
    val context = mock[Context]

    // then
    val error = the[IllegalArgumentException] thrownBy {
      testSubject.handleRequest(input, context)
    }

    error.getMessage shouldBe "Request input is invalid"
  }

  it should "return a http 200 html response for the base route" in {
    // given
    val testSubject = new Handler
    val input = new Input
    val context = mock[Context]

    // then
    val output = testSubject.handleRequest(input, context)

    output.headers.get("content-type") shouldBe "text/html"
  }
}
