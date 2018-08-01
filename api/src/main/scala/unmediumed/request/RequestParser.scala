package unmediumed.request

import scala.util.matching.Regex

class RequestParser {
  private val SafeCharacters: String = "([-a-zA-Z0-9._%#?&=@\\+\\/])*"

  private val HttpUrl: Regex = new Regex("http://" + SafeCharacters)
  private val HttpsUrl: Regex = new Regex("https://" + SafeCharacters)
  private val PathWithMediumDomain: Regex = new Regex("medium.com/" + SafeCharacters)
  private val PathWithoutMediumDomain: Regex = new Regex(SafeCharacters)

  def getPostUrl(request: Request): String = {
    val path = request.path.substring(1)

    path match {
      case p if p.length == 0 => throw new RequestParseFailedException("Please provide a Medium URL or path")
      case HttpUrl(_) | HttpsUrl(_) => path
      case PathWithMediumDomain(_) => "https://" + path
      case PathWithoutMediumDomain(_) => "https://medium.com/" + path
      case _ => throw new RequestParseFailedException("Invalid Medium URL provided")
    }
  }
}

class RequestParseFailedException(message: String = null, cause: Throwable = null) extends Exception(message, cause)
