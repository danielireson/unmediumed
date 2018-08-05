package unmediumed.source

import java.io.InputStream
import java.net.{HttpURLConnection, URL}

class HttpGetRequest(url: String) {
  private val Timeout: Int = 5000
  private val UserAgent: String = "unmediumed/1"

  val connection: HttpURLConnection = new URL(url).openConnection.asInstanceOf[HttpURLConnection]
  connection.setRequestMethod("GET")
  connection.setConnectTimeout(Timeout)
  connection.setReadTimeout(Timeout)
  connection.setRequestProperty("User-Agent", UserAgent)

  def send(): Unit = connection.connect()
  def inputStream: InputStream = connection.getInputStream
}
