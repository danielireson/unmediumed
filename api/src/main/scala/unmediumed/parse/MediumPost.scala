package unmediumed.parse

case class MediumPost(meta: Map[String, String], elements: Seq[MarkdownElement]) {
  def markdown: String = {
    val markdownOfElements = elements.map {
      case e: HeaderMarkdownElement if e != elements.head => "\n" + e.markdown
      case e => e.markdown
    }

    markdownOfElements.mkString("\n")
  }
}
