package unmediumed

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import unmediumed.request.{Input, Request}
import unmediumed.response._

import scala.util.{Failure, Success, Try}

class Handler extends RequestHandler[Input, Output] {
  def handleRequest(input: Input, context: Context): Output = {
    Try {
      getRequest(input) match {
        case Some(request) if request.path == "/" =>
          new HtmlResponse
        case Some(request) =>
          new MarkdownResponse
        case None =>
          new UnprocessableEntityResponse
      }
    } match {
      case Success(response) => response.toOutput
      case Failure(t) => failureResponse(t).toOutput
    }
  }

  private def getRequest(input: Input): Option[Request] = {
    Option(input).map(_.toRequest).filter(isValidRequest).orElse {
      throw new IllegalArgumentException("Invalid input")
    }
  }

  private def isValidRequest(request: Request): Boolean = true

  private def failureResponse(caught: Throwable): Response = {
    caught match {
      case _: IllegalArgumentException => new InternalServerErrorResponse
    }
  }
}
