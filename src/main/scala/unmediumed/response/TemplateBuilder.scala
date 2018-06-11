package unmediumed.response

import unmediumed.core.Config

class TemplateBuilder(config: Config, baseTemplate: String) {
  def build(params: Map[String, String] = Map()): String = {
    Option(baseTemplate) match {
      case Some(bt) => params.keys.foldLeft(bt)((html, param) => {
        html.replace(s"{{$param}}", params(param))
      })
      case _ => throw new IllegalArgumentException("Invalid base template")
    }
  }
}
