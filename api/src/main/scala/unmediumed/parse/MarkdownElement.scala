package unmediumed.parse

trait MarkdownElement {
  def markdown: String
}

sealed case class HeaderMarkdownElement(size: Int, content: String) extends MarkdownElement {
  override def markdown: String = "#" * size + " " + content
}

sealed case class ParagraphMarkdownElement(content: String) extends MarkdownElement {
  override def markdown: String = content
}

sealed case class ImageMarkdownElement(src: String) extends MarkdownElement {
  override def markdown: String = "![](" + src + ")"
}

sealed case class UnorderedMarkdownElement(items: Seq[String]) extends MarkdownElement {
  override def markdown: String =
    items.map { item =>
      "* " + item
    }.mkString("\n")
}

sealed case class OrderedMarkdownElement(items: Seq[String]) extends MarkdownElement {
  override def markdown: String =
    items.zipWithIndex.map { case (item, i) =>
      (i + 1) + ". " + item
    }.mkString("\n")
}

sealed case class BlockquoteMarkdownElement(content: String) extends MarkdownElement {
  override def markdown: String = "> " + content
}

sealed case class CodeblockMarkdownElement(content: String) extends MarkdownElement {
  override def markdown: String = "```\n" + content + "\n```"
}
