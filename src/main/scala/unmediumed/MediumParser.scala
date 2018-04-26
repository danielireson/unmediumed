package unmediumed

import scala.util.matching.Regex

class MediumParser {
  def parse(html: String): List[MarkdownElement] = {
    Option(html) match {
      case Some(h) if isValidMediumPost(h) => extractMarkdownElements(h.trim)
      case _ => throw new IllegalArgumentException("HTML is not a recognised medium post")
    }
  }

  private def extractMarkdownElements(html: String): List[MarkdownElement] = {
    List()
  }

  private def isValidMediumPost(html: String): Boolean = {
    hasTags(
      html,
      HtmlTag("!DOCTYPE html", isSelfClosing = true),
      HtmlTag("html", isSelfClosing = false),
      HtmlTag("body", isSelfClosing = false)
    )
  }

  def hasTags(html: String, tags: HtmlTag*): Boolean = {
    tags.forall(tag => {
      if (tag.isSelfClosing) {
        exists(html, s"<${tag.name}(.?)+>")
      } else {
        exists(html, s"<${tag.name}(.?)+>") && exists(html, s"</${tag.name}(.?)+>")
      }
    })
  }

  def exists(html: String, regex: String): Boolean = {
    new Regex(regex).findFirstIn(html).isDefined
  }
}

case class HtmlTag(name: String, isSelfClosing: Boolean)
