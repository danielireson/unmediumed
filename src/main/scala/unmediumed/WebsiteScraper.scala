package unmediumed

import java.io.InputStream
import java.net.{HttpURLConnection, URL}

import scala.util.Try
import scala.util.matching.Regex

trait WebsiteScraperComponent {
  def websiteScraper: WebsiteScraperLocal

  trait WebsiteScraperLocal {
    def scrape(url: String): String
  }
}

trait WebsiteScraper extends WebsiteScraperComponent {
  def websiteScraper: WebsiteScraper

  class WebsiteScraper extends WebsiteScraperLocal {
    val timeout: Int = 5000
    val ValidWebsite: Regex = "(http|https):\\/\\/[\\w.]+".r

    def scrape(url: String): String = {
      url match {
        case ValidWebsite(u) => scrapeWebsiteWithBufferedReader(u)
        case _ => throw new IllegalArgumentException("Creating input stream from invalid URL")
      }
    }

    private def scrapeWebsiteWithBufferedReader(url: String): String = {
      createInputStream(url) match {
        case Some(is) =>
          try {
            io.Source.fromInputStream(is).mkString
          } finally {
            is.close()
          }
        case None => throw new WebsiteScrapeFailedException("Unable to create input stream")
      }
    }

    private def createInputStream(url: String): Option[InputStream] = {
      Try {
        val connection = new URL(url).openConnection.asInstanceOf[HttpURLConnection]
        connection.setRequestMethod("GET")
        connection.setConnectTimeout(timeout)
        connection.setReadTimeout(timeout)
        Some(connection.getInputStream)
      }.getOrElse(None)
    }
  }
}

class WebsiteScrapeFailedException(message: String = null, cause: Throwable = null) extends Exception(message, cause)
