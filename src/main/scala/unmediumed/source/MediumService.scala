package unmediumed.source

import unmediumed.parse.{HtmlParser, MediumPost}

class MediumService(websiteScraper: WebsiteScraper) {
  def getPost(url: String): MediumPost = {
    websiteScraper.scrape(url) match {
      case h: String if h != "" => new HtmlParser().parse(h)
      case _ => throw new IllegalArgumentException("Creating MediumPost with invalid HTML")
    }
  }
}
