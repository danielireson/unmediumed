package unmediumed.parse

case class MediumPost(meta: Map[String, String], elements: Seq[MarkdownElement]) {
  def markdown: String = {
    elements.map {
      case e if e == elements.last => e.markdown
      case e => e.markdown + "\n"
    }.mkString("\n")
  }
}
