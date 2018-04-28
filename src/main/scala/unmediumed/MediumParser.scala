package unmediumed

import scala.util.matching.Regex

class MediumParser {
  def parse(html: String): MediumPost = {
    Option(html) match {
      case Some(h) if isValid(h) => extractMediumPost(h.trim)
      case _ => throw new IllegalArgumentException("HTML is not a recognised medium post")
    }
  }

  private def extractMediumPost(html: String): MediumPost = {
    new MediumPost(extractMetaInformation(html), extractMarkdownElements(html), html)
  }

  private def extractMetaInformation(html: String): Map[String, String] = {
    Map(
      "title" -> "This is the title",
      "description" -> "This is the description",
      "canonical" -> "http://example.com"
    )
  }

  private def extractMarkdownElements(html: String): List[MarkdownElement] = {
    List()
  }

  private def isValid(html: String): Boolean = {
    val tags = List(
      HtmlTag("!DOCTYPE html", isSelfClosing = true),
      HtmlTag("html", isSelfClosing = false),
      HtmlTag("body", isSelfClosing = false),
      HtmlTag("title", isSelfClosing = false),
      HtmlTag("meta name=\"description\"", isSelfClosing = true),
      HtmlTag("link rel=\"canonical\"", isSelfClosing = true)
    )

    hasTags(html, tags)
  }

  private def hasTags(html: String, tags: Seq[HtmlTag]): Boolean = {
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

sealed case class HtmlTag(name: String, isSelfClosing: Boolean)
