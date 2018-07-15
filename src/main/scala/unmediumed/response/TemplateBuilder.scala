package unmediumed.response

import java.io.FileNotFoundException

import scala.io.Source
import scala.util.Try

object TemplateBuilder {
  val template: String = getTemplate

  def main(args: Array[String]): Unit = {
    println("test")
  }

  private def build(params: Map[String, String] = Map()): String = {
    params.keys.foldLeft(template)((html, param) => {
      html.replace(s"{{$param}}", params(param))
    })
  }

  private def getTemplate: String = {
    Try(Source.fromResource("template.html").mkString).getOrElse {
      throw new FileNotFoundException("Unable to load base template")
    }
  }
}
