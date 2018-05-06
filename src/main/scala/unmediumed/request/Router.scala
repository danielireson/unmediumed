package unmediumed.request

import unmediumed.response.{TemplateBuilderComponent, _}

import scala.util.{Failure, Success, Try}

class Router {
  this: TemplateBuilderComponent =>

  def routeRequest(request: Request): Output = {
    Try (getResponse(request)) match {
      case Success(response) => response.toOutput
      case Failure(t) => failureResponse(t).toOutput
    }
  }

  private def getResponse(request: Request): Response = {
    Option(request) match {
      case Some(r) if r.path == "/" =>
        new HtmlResponse(templateBuilder.build())
      case Some(r) => new MarkdownResponse
      case None => throw new IllegalArgumentException("Invalid request passed to router")
    }
  }

  private def failureResponse(caught: Throwable): Response = {
    caught match {
      case _: IllegalArgumentException => new InternalServerErrorResponse
    }
  }
}