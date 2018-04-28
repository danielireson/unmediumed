package unmediumed

import unmediumed.components.{MediumService, WebsiteScraper}

object Application extends components.Config
  with MediumService
  with WebsiteScraper {

  val config = new Config(Map())
  val mediumService = new MediumService
  val websiteScraper = new WebsiteScraper
}
