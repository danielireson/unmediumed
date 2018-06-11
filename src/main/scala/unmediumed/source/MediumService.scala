package unmediumed.source

import unmediumed.core.Config
import unmediumed.parse.{HtmlParser, MediumPost}

class MediumService(config: Config, websiteScraper: WebsiteScraper) {
  def getPost(url: String): MediumPost = {
    websiteScraper.scrape(url) match {
      case h: String if h != "" => new HtmlParser().parse(h)
      case _ => throw new IllegalArgumentException("Creating MediumPost with invalid HTML")
    }
  }
}
