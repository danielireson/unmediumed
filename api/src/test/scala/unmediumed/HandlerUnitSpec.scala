package unmediumed

import com.amazonaws.services.lambda.runtime.Context
import unmediumed.request.Input
import unmediumed.response.Output

class HandlerUnitSpec extends TestHelpers {
  "Handler" should "return an api gateway formatted output" in {
    // given
    val testSubject = new Handler(null, null, null, null)

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
}
