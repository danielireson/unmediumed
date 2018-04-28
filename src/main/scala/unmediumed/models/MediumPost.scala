package unmediumed.models

import unmediumed.parse.MarkdownElement

class MediumPost(val meta: Map[String, String], val markdown: List[MarkdownElement], val html: String)
