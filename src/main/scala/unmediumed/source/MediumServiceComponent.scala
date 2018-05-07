package unmediumed.source

import unmediumed.parse.{HtmlParser, MediumPost}

trait MediumServiceComponent {
  this: WebsiteScraperComponent =>

  val mediumService: MediumService

  class MediumService {
    def getPost(url: String): MediumPost = {
      websiteScraper.scrape(url) match {
        case h: String if h != "" => new HtmlParser().parse(h)
        case _ => throw new IllegalArgumentException("Creating MediumPost with invalid HTML")
      }
    }
  }
}
