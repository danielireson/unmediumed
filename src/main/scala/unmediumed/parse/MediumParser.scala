package unmediumed.parse

import unmediumed.models.MediumPost
import org.ccil.cowan.tagsoup.jaxp.SAXParserImpl

import scala.xml._

class MediumParser {
  val parser: SAXParser = SAXParserImpl.newInstance(null)

  def parse(html: String): MediumPost = {
    try {
      val source: InputSource = Source.fromString(Option(html).getOrElse(""))
      val rootElement: Elem = XML.loadXML(source, parser)
      new MediumPost(extractMeta(rootElement), extractMarkdown(rootElement), html)
    } catch {
      case e: NoSuchElementException => throw new ParseFailedException("HTML is not a valid medium post", e)
    }
  }

  private def extractMeta(rootElement: Elem): Map[String, String] = {
    val title = (rootElement \\ "title").headOption
      .map(_.text)
      .getOrElse(throw new NoSuchElementException("Title not found"))

    val description = (rootElement \\ "meta")
      .find(_.attribute("name").map(_.text).contains("description"))
      .flatMap(_.attribute("content").map(_.text))
      .getOrElse(throw new NoSuchElementException("Description not found"))

    val canonical = (rootElement \\ "link")
      .find(_.attribute("rel").map(_.text).contains("canonical"))
      .flatMap(_.attribute("href").map(_.text))
      .getOrElse(throw new NoSuchElementException("Canonical link not found"))

    Map("title" -> title, "description" -> description, "canonical" -> canonical)
  }

  private def extractMarkdown(rootElement: Elem): List[MarkdownElement] = {
    List()
  }
}

class ParseFailedException(message: String = null, cause: Throwable = null) extends Exception(message, cause)
