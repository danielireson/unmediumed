package unmediumed.response

trait TemplateBuilderComponent {
  val templateBuilder: TemplateBuilder

  class TemplateBuilder(baseTemplate: String) {
    def build(params: Map[String, String] = Map()): String = {
      Option(baseTemplate) match {
        case Some(bt) => params.keys.foldLeft(bt)((html, param) => {
          html.replace(s"{{$param}}", params(param))
        })
        case _ => throw new IllegalArgumentException("Invalid base template")
      }
    }
  }
}
