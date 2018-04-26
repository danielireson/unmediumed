package unmediumed

object Application extends Config
  with MediumService
  with WebsiteScraper {

  val config = new Config(Map())
  val mediumService = new MediumService
  val websiteScraper = new WebsiteScraper
}
