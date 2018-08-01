package unmediumed.parse

trait MarkdownElement {
  def markdown: String

  protected def parseText(text: String): String = {
    text
      .replaceAll("<strong>|</strong>", "**")
      .replaceAll("<em>|</em>", "*")
      .replaceAll("<a href=\"(.*)\">(.*)</a>", "[$2]($1)")
  }
}

sealed case class HeaderMarkdownElement(size: Int, text: String) extends MarkdownElement {
  def markdown: String = "#" * size + " " + parseText(text)
}

sealed case class ParagraphMarkdownElement(text: String) extends MarkdownElement {
  def markdown: String = parseText(text)
}

sealed case class ImageMarkdownElement(src: String) extends MarkdownElement {
  def markdown: String = "![](" + src + ")"
}

sealed case class UnorderedMarkdownElement(items: Seq[String]) extends MarkdownElement {
  def markdown: String =
    items.map { item =>
      "* " + parseText(item)
    }.mkString("\n")
}

sealed case class OrderedMarkdownElement(items: Seq[String]) extends MarkdownElement {
  def markdown: String =
    items.zipWithIndex.map { case (item, i) =>
      (i + 1) + ". " + parseText(item)
    }.mkString("\n")
}

sealed case class BlockquoteMarkdownElement(text: String) extends MarkdownElement {
  def markdown: String = "> " + text
}

sealed case class CodeblockMarkdownElement(text: String) extends MarkdownElement {
  def markdown: String = "```\n" + text + "\n```"
}
