package unmediumed.request

import scala.util.matching.Regex

class RequestParser {
  val SafeCharacters: String = "([-a-zA-Z0-9._%#?&=@\\+\\/])*"

  val HttpUrl: Regex = new Regex("http://" + SafeCharacters)
  val HttpsUrl: Regex = new Regex("https://" + SafeCharacters)
  val PathWithMediumDomain: Regex = new Regex("medium.com/" + SafeCharacters)
  val PathWithoutMediumDomain: Regex = new Regex(SafeCharacters)

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
