package unmediumed

class MediumService {
  val websiteScraper: WebsiteScraper = new WebsiteScraper

  def getPost(url: String): MediumPost = {
    websiteScraper.scrape(url) match {
      case h: String if h != "" => new MediumPost(h)
      case _ => throw new IllegalArgumentException("Creating MediumPost with invalid HTML")
    }
  }
}
