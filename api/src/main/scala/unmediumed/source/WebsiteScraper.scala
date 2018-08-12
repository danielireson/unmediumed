package unmediumed.source

import java.io.{IOException, InputStream}

import scala.util.Try

class WebsiteScraper {
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
      val request = new HttpGetRequest(url)
      Some(request.send)
    } getOrElse None
  }
}

class WebsiteScrapeFailedException(message: String = null, cause: Throwable = null) extends Exception(message, cause)
