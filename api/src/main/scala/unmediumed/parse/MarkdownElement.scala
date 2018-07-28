package unmediumed.parse

trait MarkdownElement {
  def markdown: String
}

sealed case class HeaderMarkdownElement(size: Int, text: String) extends MarkdownElement {
  def markdown: String = "#" * size + " " + text
}

sealed case class ParagraphMarkdownElement(text: String) extends MarkdownElement {
  def markdown: String =
    text
      .replaceAll("<strong>", "**")
      .replaceAll("</strong>", "**")
      .replaceAll("<em>", "*")
      .replaceAll("</em>", "*")
}

sealed case class ImageMarkdownElement(src: String) extends MarkdownElement {
  def markdown: String = "![](" + src + ")"
}

sealed case class UnorderedMarkdownElement(items: Seq[String]) extends MarkdownElement {
  def markdown: String =
    items.map { item =>
      "* " + item
    }.mkString("\n")
}

sealed case class OrderedMarkdownElement(items: Seq[String]) extends MarkdownElement {
  def markdown: String =
    items.zipWithIndex.map { case (item, i) =>
      (i + 1) + ". " + item
    }.mkString("\n")
}

sealed case class BlockquoteMarkdownElement(text: String) extends MarkdownElement {
  def markdown: String = "> " + text
}

sealed case class CodeblockMarkdownElement(text: String) extends MarkdownElement {
  def markdown: String = "```\n" + text + "\n```"
}
