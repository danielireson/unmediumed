package unmediumed

import com.amazonaws.services.lambda.runtime.Context
import unmediumed.request.Input
import unmediumed.response.Output

class ApplicationTest extends TestHelpers {
  it should "return the expected response for a known medium post" in {
    // given
    val testSubject = new Bootstrap
    val context: Context = mock[Context]

    val testPostPath: String = "/https://medium.design/logos-and-brand-guidelines-f1a01a733592"
    val input = new Input
    input.setHttpMethod("GET")
    input.setPath(testPostPath)

    // when
    val output: Output = testSubject.handleRequest(input, context)

    // then
    output.body shouldBe getTestPostMarkdown
    output.statusCode shouldBe 200
    output.headers.get("content-type") shouldBe "text/markdown; charset=utf-8"
  }

  it should "return the expected response for a pay wall protected medium post" in {
    // given
    val testSubject = new Bootstrap
    val context: Context = mock[Context]

    val testPostPath: String = "/https://medium.com/s/futurehuman/how-different-will-humans-be-in-a-century-d4543e09f9ff"
    val input = new Input
    input.setHttpMethod("GET")
    input.setPath(testPostPath)

    // when
    val output: Output = testSubject.handleRequest(input, context)

    // then
    output.body shouldBe "Unable to parse Medium post"
    output.statusCode shouldBe 500
    output.headers.get("content-type") shouldBe "text/markdown; charset=utf-8"
  }

  private def getTestPostMarkdown: String = {
    """
      |# Medium Branding Guidelines
      |These few simple rules will help you use our branding elements to communicate the Medium brand most effectively. [Download all assets](https://github.com/Medium/medium-logos/archive/master.zip)
      |
      |#### Wordmark
      |The Medium wordmark is an important expression of our brand identity. It should in no way be distorted or redrawn when applied to communications. Because the wordmark is such a recognizable and highly visible brand asset, it is vital that it is always applied consistently.
      |![](https://cdn-images-1.medium.com/max/1600/1*TGH72Nnw24QL3iV9IOm4VA.png)
      |
      |#### Monogram
      |Our monogram is the reduced form of our wordmark. It should only be used when the wordmark is too small to achieve maximum impact.
      |![](https://cdn-images-1.medium.com/max/1600/1*emiGsBgJu2KHWyjluhKXQw.png)
      |
      |#### Clearspace and Positioning
      |The wordmark should always be surrounded by generous white space. The diagram below defines the minimum amount of clear space needed, which is based on the x-height in the wordmark.
      |![](https://cdn-images-1.medium.com/max/1600/1*xVBOuB8XD-dNRzxqIyCHaQ.png)
      |
      |#### Color
      |**Primary Usage: **our wordmark is primarily used in black. It can sit on top of the light green or white background. It can also be used in white over black.
      |![](https://cdn-images-1.medium.com/max/800/1*JRMLdiqVq5bP9Fs6HEQVhw.png)
      |![](https://cdn-images-1.medium.com/max/800/1*uLuWzCXfq2rt1t_TkuLB8A.png)
      |![](https://cdn-images-1.medium.com/max/800/1*F6SrJR7_s95r6oCF3ugMZw.png)
      |**Discretionary Usage: **in rare cases where there is already a strong presence of our light green color, the wordmark may be used on top of a color from our secondary palette.
      |![](https://cdn-images-1.medium.com/max/800/1*WTq6omST-X_d6qd6ASW16A.png)
      |![](https://cdn-images-1.medium.com/max/800/1*g8cIaz3hKXspLBVwE5Khpg.png)
      |![](https://cdn-images-1.medium.com/max/800/1*P8ZAhwcb8AcCR8HKiz-okw.png)
      |![](https://cdn-images-1.medium.com/max/800/1*OG_nr5C3VvbvrsCC1Q577g.png)
      |![](https://cdn-images-1.medium.com/max/800/1*GDVTHFzBfDE-cUlVezWTzA.png)
      |![](https://cdn-images-1.medium.com/max/800/1*BWh2pt4DkcFUMznsWHBn0A.png)
      |**Incorrect Color Usage: **our wordmark should never be used in white on top of any light colors. Additionally it shouldn’t be used with our dark colors, neither coloring it with a dark color, or using our primary black over a dark background.
      |![](https://cdn-images-1.medium.com/max/800/1*EiWAWP7_AYH7lL60CnHf6g.png)
      |![](https://cdn-images-1.medium.com/max/800/1*bSNAgJ115WMtGBzQCJfzOg.png)
      |![](https://cdn-images-1.medium.com/max/800/1*jqqsI7gQpSok49nPygKUaQ.png)
      |
      |#### Dont’s
      |The examples shown here illustrate incorrect uses of the wordmark.
      |![](https://cdn-images-1.medium.com/max/2000/1*C_tIv5LgGBK4RxorcZhOoA.png)
      |
      |#### [Download all assets](https://github.com/Medium/medium-logos/archive/master.zip)
      |If you have any further questions about our brand elements, please drop us a line at [yourfriends@medium.com](mailto:yourfriends@medium.com).
    """.stripMargin.trim
  }
}
