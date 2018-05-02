package unmediumed.core

import java.io.FileNotFoundException

import unmediumed.response.TemplateBuilderComponent
import unmediumed.source._

import scala.io.Source
import scala.util.Try

trait ComponentRegistry extends ConfigComponent
  with MediumServiceComponent
  with WebsiteSourceComponent
  with TemplateBuilderComponent {

  val config = new Config(Map())
  val mediumService = new MediumService
  val websiteSource = new WebsiteSource

  lazy val templateBuilder = new TemplateBuilder(
    Try(Source.fromResource("template.html").mkString).getOrElse {
      throw new FileNotFoundException("Unable to load base template")
    }
  )
}
