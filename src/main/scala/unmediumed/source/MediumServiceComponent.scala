package unmediumed.source

import unmediumed.parse.{MediumParser, MediumPost}

trait MediumServiceComponent {
  this: WebsiteSourceComponent =>

  val mediumService: MediumService

  class MediumService {
    def getPost(url: String): MediumPost = {
      websiteSource.scrape(url) match {
        case h: String if h != "" => new MediumParser().parse(h)
        case _ => throw new IllegalArgumentException("Creating MediumPost with invalid HTML")
      }
    }
  }
}
