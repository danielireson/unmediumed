package unmediumed.parse

import unmediumed.models.MediumPost

import scala.util.matching.Regex

class MediumParser {
  def parse(html: String): MediumPost = {
    Option(html) match {
      case Some(h) if isValid(h) => extractMediumPost(h.trim)
      case _ => throw new IllegalArgumentException("HTML is not a valid medium post")
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
