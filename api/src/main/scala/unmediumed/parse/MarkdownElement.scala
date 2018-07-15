package unmediumed.parse

trait MarkdownElement

sealed case class HeaderMarkdownElement(size: Int, content: String) extends MarkdownElement
sealed case class ParagraphMarkdownElement(content: String) extends MarkdownElement
