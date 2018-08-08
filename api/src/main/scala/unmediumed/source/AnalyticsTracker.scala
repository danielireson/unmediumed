package unmediumed.source

import scala.util.Try

class AnalyticsTracker {
  private val TrackingEndpoint: String = "https://www.google-analytics.com?v=1&t=pageview&tid={tId}&dh={dh}&dp={dp}"

  def track(path: String): Unit = {
    val config = new AnalyticsConfig

    if (config.isEnabled) {
      val trackingUrl = TrackingEndpoint
        .replace("{tId}", config.trackingId)
        .replace("{dh}", config.trackingHost)
        .replace("{dp}", path)

      Try {
        val request = new HttpPostRequest(trackingUrl)
        request.send()
      }
    }
  }
}

class AnalyticsConfig {
  def isEnabled: Boolean = Try(trackingId, trackingHost).isSuccess

  def trackingId: String = sys.env("ANALYTICS_TRACKING_ID")
  def trackingHost: String = sys.env("ANALYTICS_TRACKING_HOST")
}
