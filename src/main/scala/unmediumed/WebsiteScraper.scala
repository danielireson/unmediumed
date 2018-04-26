package unmediumed

import java.io.InputStream
import java.net.{HttpURLConnection, URL}

import scala.util.matching.Regex

trait WebsiteScraperComponentLocal {
  def websiteScraper: WebsiteScraperLocal

  trait WebsiteScraperLocal {
    def scrape(url: String): String
  }
}

trait WebsiteScraperComponent extends WebsiteScraperComponentLocal {
  def websiteScraper = new WebsiteScraperComponent

  class WebsiteScraperComponent extends WebsiteScraperLocal {
    val timeout: Int = 5000
    val ValidWebsite: Regex = "(a-zA-Z0-9)+".r

    def scrape(url: String): String = {
      url match {
        case ValidWebsite(u) => scrapeWebsiteWithBufferedReader(u)
        case _ => throw new IllegalArgumentException("Creating input stream from invalid URL")
      }
    }

    private def scrapeWebsiteWithBufferedReader(url: String): String = {

      val inputStream = try {
        createInputStream(url)
      }

      try {
        io.Source.fromInputStream(inputStream).mkString
      } catch {

      } finally  {

      }


      val inputStream = try {
        createInputStream(url)
        val html = io.Source.fromInputStream(inputStream).mkString
        html
      } finally {
        if (inputStream != null) inputStream.close()
      }
    }

    private def createInputStream(url: String): InputStream = {
      val connection = new URL(url).openConnection.asInstanceOf[HttpURLConnection]
      connection.setRequestMethod("GET")
      connection.setConnectTimeout(timeout)
      connection.setReadTimeout(timeout)
      connection.getInputStream
    }
  }
}
