package unmediumed

trait MediumServiceComponent {
  def mediumService: MediumServiceLocal

  trait MediumServiceLocal {
    def getPost(url: String): MediumPost
  }
}

trait MediumService extends MediumServiceComponent {
  this: WebsiteScraperComponent =>

  def mediumService: MediumService

  class MediumService extends MediumServiceLocal {
    def getPost(url: String): MediumPost = {
      websiteScraper.scrape(url) match {
        case h: String if h != "" => new MediumParser().parse(h)
        case _ => throw new IllegalArgumentException("Creating MediumPost with invalid HTML")
      }
    }
  }
}
