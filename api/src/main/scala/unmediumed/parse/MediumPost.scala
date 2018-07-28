package unmediumed.parse

class MediumPost(val meta: Map[String, String], val elements: Seq[MarkdownElement]) {
  def markdown: String = {
    val markdownOfElements = elements.map {
      case e: HeaderMarkdownElement if e != elements.head => "\n" + e.markdown
      case e => e.markdown
    }

    markdownOfElements.mkString("\n")
  }
}
