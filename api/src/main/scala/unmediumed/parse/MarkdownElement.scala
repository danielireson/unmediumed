package unmediumed.parse

trait MarkdownElement {
  def markdown: String
}

sealed case class HeaderMarkdownElement(size: Int, content: String) extends MarkdownElement {
  override def markdown: String = "#" * size + content
}

sealed case class ParagraphMarkdownElement(content: String) extends MarkdownElement {
  override def markdown: String = content
}
