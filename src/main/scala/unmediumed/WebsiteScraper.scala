package unmediumed

import java.io.{IOException, InputStream}
import java.net.{HttpURLConnection, URL}

import scala.util.Try

trait WebsiteScraperComponent {
  def websiteScraper: WebsiteScraperLocal

  trait WebsiteScraperLocal {
    def scrape(url: String): String
    def scrapeFromInputStream(inputStream: InputStream): String
  }
}

trait WebsiteScraper extends WebsiteScraperComponent {
  def websiteScraper: WebsiteScraper

  class WebsiteScraper extends WebsiteScraperLocal {
    val timeout: Int = 5000

    def scrape(url: String): String = {
      createInputStream(url) match {
        case Some(is) => scrapeFromInputStream(is)
        case None => throw new WebsiteScrapeFailedException("Unable to create input stream")
      }
    }

    def scrapeFromInputStream(inputStream: InputStream): String = {
      try {
        io.Source.fromInputStream(inputStream).mkString
      } catch {
        case e: IOException => throw new WebsiteScrapeFailedException("Unable to read input stream", e)
      } finally {
        inputStream.close()
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
