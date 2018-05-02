package unmediumed

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import unmediumed.core.ComponentRegistry
import unmediumed.request.{Input, Router}
import unmediumed.response.Output

class Handler extends RequestHandler[Input, Output] {
  val router = new Router with ComponentRegistry

  def handleRequest(input: Input, context: Context): Output = {
    Option(input).map(_.toRequest).map(router.routeRequest).getOrElse {
      throw new IllegalArgumentException("Invalid input passed to application handler")
    }
  }
}
