package unmediumed.source

import java.io.{IOException, InputStream}
import java.net.{HttpURLConnection, URL}

import scala.util.Try

class WebsiteScraper {
  private val Timeout: Int = 5000
  private val UserAgent: String = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:15.0) Gecko/20100101 Firefox/15.0.1"

  @throws(classOf[WebsiteScrapeFailedException])
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
      connection.setConnectTimeout(Timeout)
      connection.setReadTimeout(Timeout)
      connection.setRequestProperty("User-Agent", UserAgent)
      Some(connection.getInputStream)
    } getOrElse None
  }
}

class WebsiteScrapeFailedException(message: String = null, cause: Throwable = null) extends Exception(message, cause)
