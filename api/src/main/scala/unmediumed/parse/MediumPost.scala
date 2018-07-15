package unmediumed.parse

class MediumPost(val meta: Map[String, String], val elements: Seq[MarkdownElement]) {
  def markdown: String = elements.map(_.markdown).mkString("\n")
}
