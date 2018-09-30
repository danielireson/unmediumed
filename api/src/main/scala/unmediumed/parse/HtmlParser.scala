package unmediumed.parse

import org.ccil.cowan.tagsoup.jaxp.SAXParserImpl

import scala.util.Try
import scala.xml._

class HtmlParser {
  private val parser: SAXParser = SAXParserImpl.newInstance(null)

  @throws(classOf[HtmlParseProtectedPostException])
  @throws(classOf[HtmlParseFailedException])
  def parse(html: String): MediumPost = {
    Option(html) match {
      case None =>
        throw new HtmlParseFailedException("Unable to parse null html")
      case Some(h) if h.contains("js-lockedPostHeader") =>
        throw new HtmlParseProtectedPostException("Unable to parse members only Medium post")
      case Some(h) =>
        Try {
          val source: InputSource = Source.fromString(h)
          val rootElement: Elem = XML.loadXML(source, parser)
          val meta = extractMeta(rootElement)
          val elements = extractBlockElements(rootElement)
          val processedElements = processBlockElements(elements)
          MediumPost(meta, processedElements)
        } getOrElse {
          throw new HtmlParseFailedException("Unable to parse Medium post")
        }
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

  private def extractBlockElements(rootElement: Elem): Seq[MarkdownElement] = {
    (rootElement \\ "article" \\ "section" \\ "_").collect {
      case e if e.label == "p" => ParagraphMarkdownElement(extractInlineElements(e))
      case e if e.label == "h1" => HeaderMarkdownElement(1, extractInlineElements(e))
      case e if e.label == "h2" => HeaderMarkdownElement(2, extractInlineElements(e))
      case e if e.label == "h3" => HeaderMarkdownElement(3, extractInlineElements(e))
      case e if e.label == "h4" => HeaderMarkdownElement(4, extractInlineElements(e))
      case e if e.label == "h5" => HeaderMarkdownElement(5, extractInlineElements(e))
      case e if e.label == "h6" => HeaderMarkdownElement(6, extractInlineElements(e))
      case e if e.label == "img" => ImageMarkdownElement(getAttribute("src", e))
      case e if e.label == "ul" => UnorderedMarkdownElement((e \\ "li").map(extractInlineElements))
      case e if e.label == "ol" => OrderedMarkdownElement((e \\ "li").map(extractInlineElements))
      case e if e.label == "blockquote" => BlockquoteMarkdownElement(e.text)
      case e if e.label == "pre" => PreMarkdownElement(extractInlineElements(e))
    } match {
      case elements if elements.nonEmpty => elements
      case _ => throw new HtmlParseFailedException("Unable to extract markdown elements")
    }
  }

  private def processBlockElements(elements: Seq[MarkdownElement]): Seq[MarkdownElement] = {
    elements.foldLeft(Seq.empty[MarkdownElement]) { (elements, element) =>
      if (elements.isEmpty) {
        elements :+ element
      } else {
        (elements.last, element) match {
          // merge adjacent pre blocks
          case (e1: PreMarkdownElement, e2: PreMarkdownElement) =>
            elements.dropRight(1) :+ PreMarkdownElement(e1.text + e2.text)
          case _ =>
            elements :+ element
        }
      }
    }
  }

  private def extractInlineElements(element: Node): String = {
    element.child.map {
      case c if c.label == "code" => "<code>" + c.text + "</code>"
      case c if c.label == "strong" => "<strong>" + c.text + "</strong>"
      case c if c.label == "em" => "<em>" + c.text + "</em>"
      case c if c.label == "a" => "<a href=\"" + getAttribute("href", c) + "\">" + c.text + "</a>"
      case c if c.label == "br" => "\n"
      case c => c.text
    }.mkString
  }

  private def getAttribute(name: String, element: Node, default: String = ""): String = {
    element.attribute(name).map(_.toString).getOrElse(default)
  }
}

class HtmlParseFailedException(message: String = null, cause: Throwable = null) extends Exception(message, cause)
class HtmlParseProtectedPostException(message: String = null, cause: Throwable = null) extends Exception(message, cause)
