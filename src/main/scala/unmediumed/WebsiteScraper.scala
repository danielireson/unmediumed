package unmediumed

import java.io.InputStream
import java.net.{HttpURLConnection, URL}

class WebsiteScraper {
  val timeout: Int = 5000

  def scrape(url: String): String = {
    val inputStream = createInputStream(url)
    val html = io.Source.fromInputStream(inputStream).mkString
    if (inputStream != null) inputStream.close()

    html
  }

  private def createInputStream(url: String): InputStream = {
    if (url == null || url == "") {
      throw new IllegalArgumentException("Creating input stream from invalid URL")
    }

    val connection = new URL(url).openConnection.asInstanceOf[HttpURLConnection]
    connection.setRequestMethod("GET")
    connection.setConnectTimeout(timeout)
    connection.setReadTimeout(timeout)

    connection.getInputStream
  }
}
