package unmediumed

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import unmediumed.request.{Input, Request}
import unmediumed.response.{Output, Response}

import scala.util.{Failure, Success, Try}

class Handler extends RequestHandler[Input, Output] {
  def handleRequest(input: Input, context: Context): Output = {
    Try {
      getRequest(input) match {
        case Some(request) if request.path == "/" => homepageResponse(request)
        case Some(request) => markdownPostResponse(request)
        case None => invalidRequestResponse()
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

  private def homepageResponse(request: Request): Response = {
    Response(200, "<!DOCTYPE html><html></html><body></body>", Map("content-type" -> "text/html"))
  }

  private def markdownPostResponse(request: Request): Response = {
    Response(200, "", Map("content-type" -> "text/markdown"))
  }

  private def invalidRequestResponse(): Response = {
    Response(422, "", Map("content-type" -> "text/markdown"))
  }

  private def failureResponse(caught: Throwable): Response = {
    caught match {
      case _: IllegalArgumentException => Response(500, "", Map("content-type" -> "text/markdown"))
    }
  }
}
