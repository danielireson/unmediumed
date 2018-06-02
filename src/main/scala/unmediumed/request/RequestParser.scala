package unmediumed.request

import scala.util.matching.Regex

class RequestParser {
  val urlSafeCharacters: String = "([-a-zA-Z0-9._%#?&=@\\+\\/])*"

  val HttpUrl: Regex = new Regex("http://" + urlSafeCharacters)
  val HttpsUrl: Regex = new Regex("https://" + urlSafeCharacters)
  val PathWithMediumDomain: Regex = new Regex("medium.com/" + urlSafeCharacters)
  val PathWithoutMediumDomain: Regex = new Regex(urlSafeCharacters)

  def getPostUrl(request: Request): String = {
    val path = request.path.substring(1)

    path match {
      case HttpUrl(_) | HttpsUrl(_) => path
      case PathWithMediumDomain(_) => "https://" + path
      case PathWithoutMediumDomain(_) => "https://medium.com/" + path
      case _ => throw new RequestParseFailedException("Invalid medium url provided")
    }
  }
}

class RequestParseFailedException(message: String = null, cause: Throwable = null) extends Exception(message, cause)
