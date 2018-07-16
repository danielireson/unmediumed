package unmediumed.parse

trait MarkdownElement {
  def markdown: String
}

sealed case class HeaderMarkdownElement(size: Int, content: Any) extends MarkdownElement {
  override def markdown: String = "#" * size + " " + content.toString
}

sealed case class ParagraphMarkdownElement(content: Any) extends MarkdownElement {
  override def markdown: String = content.toString
}

sealed case class ImageMarkdownElement(src: Any) extends MarkdownElement {
  override def markdown: String = "![](" + src.toString + ")"
}
