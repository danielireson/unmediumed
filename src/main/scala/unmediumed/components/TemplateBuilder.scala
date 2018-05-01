package unmediumed.components

trait TemplateBuilderComponent {
  def templateBuilder: TemplateBuilderLocal

  trait TemplateBuilderLocal {
    def build(params: Map[String, String]): String
  }
}

trait TemplateBuilder extends TemplateBuilderComponent {
  def templateBuilder: TemplateBuilder

  class TemplateBuilder(baseTemplate: String) extends TemplateBuilderLocal {
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
