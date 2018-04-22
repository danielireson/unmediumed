package unmediumed

import java.{util => ju}

import com.amazonaws.services.lambda.runtime.Context
import org.scalatest._
import org.scalatest.mockito.MockitoSugar

class HandlerUnitSpec extends FlatSpec with Matchers with MockitoSugar {
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

  it should "throw an IllegalArgumentException for a null input" in {
    // given
    val testSubject = new Handler
    val input = null
    val context = mock[Context]

    // then
    intercept[IllegalArgumentException] {
      testSubject.handleRequest(input, context)
    }
  }
}
