package unmediumed.request

import scala.util.matching.Regex

class PathParser {
  private val SafeCharacters: String = "([-a-zA-Z0-9._%#?&=@\\+\\/])*"

  private val HttpUrl: Regex = new Regex("http://" + SafeCharacters)
  private val HttpsUrl: Regex = new Regex("https://" + SafeCharacters)
  private val PathWithMediumDomain: Regex = new Regex("medium.com/" + SafeCharacters)
  private val PathWithoutMediumDomain: Regex = new Regex(SafeCharacters)

  @throws(classOf[PathParseFailedException])
  def parse(pathWithRoot: String): String = {
    val path = pathWithRoot.substring(1)

    path match {
      case p if p == "" => throw new PathParseFailedException("Please provide a Medium URL or path")
      case HttpUrl(_) | HttpsUrl(_) => path
      case PathWithMediumDomain(_) => "https://" + path
      case PathWithoutMediumDomain(_) => "https://medium.com/" + path
      case _ => throw new PathParseFailedException("Invalid Medium URL provided")
    }
  }
}

class PathParseFailedException(message: String = null, cause: Throwable = null) extends Exception(message, cause)
