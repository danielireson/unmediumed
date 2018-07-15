package unmediumed

import com.amazonaws.services.lambda.runtime.Context
import unmediumed.request.Input
import unmediumed.response.Output

class ApplicationTest extends TestHelpers {
  private trait ApplicationTestFixture {
    val testPostPath: String = "/https://medium.design/logos-and-brand-guidelines-f1a01a733592"
    val testPostMarkdown: String = getTestPostMarkdown
  }

  it should "return a 200 response for a valid medium post path" in new ApplicationTestFixture {
    // given
    val testSubject = new Handler
    val context: Context = mock[Context]

    val input = new Input
    input.setHttpMethod("GET")
    input.setPath(testPostPath)

    // when
    val output: Output = testSubject.handleRequest(input, context)

    // then
    output.body shouldBe testPostMarkdown
    output.statusCode shouldBe 200
    output.headers.get("content-type") shouldBe "text/markdown"
  }

  private def getTestPostMarkdown: String = {
    """
      |# Medium Branding Guidelines
      |These few simple rules will help you use our branding elements to communicate the Medium brand most effectively. Download all assets
      |
      |#### Wordmark
      |The Medium wordmark is an important expression of our brand identity. It should in no way be distorted or redrawn when applied to communications. Because the wordmark is such a recognizable and highly visible brand asset, it is vital that it is always applied consistently.
      |
      |#### Monogram
      |Our monogram is the reduced form of our wordmark. It should only be used when the wordmark is too small to achieve maximum impact.
      |
      |#### Clearspace and Positioning
      |The wordmark should always be surrounded by generous white space. The diagram below defines the minimum amount of clear space needed, which is based on the x-height in the wordmark.
      |
      |#### Color
      |Primary Usage: our wordmark is primarily used in black. It can sit on top of the light green or white background. It can also be used in white over black.
      |Discretionary Usage: in rare cases where there is already a strong presence of our light green color, the wordmark may be used on top of a color from our secondary palette.
      |Incorrect Color Usage: our wordmark should never be used in white on top of any light colors. Additionally it shouldn’t be used with our dark colors, neither coloring it with a dark color, or using our primary black over a dark background.
      |
      |#### Dont’s
      |The examples shown here illustrate incorrect uses of the wordmark.
      |
      |#### Download all assets
      |If you have any further questions about our brand elements, please drop us a line at yourfriends@medium.com.
      |From a quick cheer to a standing ovation, clap to show how much you enjoyed this story.
      |
      |### Medium
      |Everyone’s stories and ideas
      |
      |### Designing Medium
      |Stories from Medium’s design and research team
    """.stripMargin.trim
  }
}
