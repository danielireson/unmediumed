package unmediumed.parse

import unmediumed.models.MediumPost
import org.ccil.cowan.tagsoup.jaxp.SAXParserImpl

import scala.util.matching.Regex
import scala.xml.{Elem, SAXParser, Source, XML}

class MediumParser {
  val parser: SAXParser = SAXParserImpl.newInstance(null)

  def parse(html: String): MediumPost = {
    Option(html) match {
      case Some(h) if isValid(h) => extractMediumPost(h.trim)
      case _ => throw new IllegalArgumentException("HTML is not a valid medium post")
    }
  }

  private def extractMediumPost(html: String): MediumPost = {
    val rootElement = XML.loadXML(Source.fromString(html), parser)
    val metaInformation = extractMetaInformation(rootElement)
    val markdownElements = extractMarkdownElements(html)

    new MediumPost(metaInformation, markdownElements, html)
  }

  private def extractMetaInformation(rootElement: Elem): Map[String, String] = {
    val title = (rootElement \\ "title").head.text

    val description = (rootElement \\ "meta")
      .find(_.attribute("name").map(_.text).contains("description"))
      .flatMap(_.attribute("content").map(_.text))
      .getOrElse("")

    val canonical = (rootElement \\ "link")
      .find(_.attribute("rel").map(_.text).contains("canonical"))
      .flatMap(_.attribute("href").map(_.text))
      .getOrElse("")

    Map("title" -> title, "description" -> description, "canonical" -> canonical)
  }

  private def extractMarkdownElements(html: String): List[MarkdownElement] = {
    List()
  }

  private def isValid(html: String): Boolean = {
    val tags = List(
      HtmlTagSearch("!DOCTYPE html", isSelfClosing = true),
      HtmlTagSearch("html", isSelfClosing = false),
      HtmlTagSearch("body", isSelfClosing = false),
      HtmlTagSearch("title", isSelfClosing = false),
      HtmlTagSearch("meta name=\"description\"", isSelfClosing = true),
      HtmlTagSearch("link rel=\"canonical\"", isSelfClosing = true)
    )

    hasTags(html, tags)
  }

  private def hasTags(html: String, tags: Seq[HtmlTagSearch]): Boolean = {
    tags.forall(tag => {
      if (tag.isSelfClosing) {
        exists(html, s"<${tag.name}(.?)+>")
      } else {
        exists(html, s"<${tag.name}(.?)+>") && exists(html, s"</${tag.name}(.?)+>")
      }
    })
  }

  private def exists(html: String, regex: String): Boolean = {
    new Regex(regex).findFirstIn(html).isDefined
  }
}

sealed case class HtmlTagSearch(name: String, isSelfClosing: Boolean)
