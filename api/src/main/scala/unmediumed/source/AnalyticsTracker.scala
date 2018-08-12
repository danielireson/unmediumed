package unmediumed.source

import java.net.URLEncoder

import unmediumed.request.Request

import scala.util.Try

class AnalyticsTracker {
  private val TrackingEndpoint: String =
    "https://www.google-analytics.com/collect?v=1&t=pageview&tid={tid}&dh={dh}&cid={cid}&dp={dp}"

  def track(request: Request): Unit = {
    val config = new AnalyticsConfig

    if (config.isEnabled) {
      val trackingUrl = TrackingEndpoint
        .replace("{tid}", encodeParameter(config.trackingId))
        .replace("{dh}", encodeParameter(config.trackingHost))
        .replace("{cid}", encodeParameter(request.id))
        .replace("{dp}", encodeParameter(request.path))

      Try {
        val request = new HttpPostRequest(trackingUrl)
        request.send()
      }
    }
  }

  private def encodeParameter(value: Any): String = URLEncoder.encode(value.toString, "UTF-8")
}

class AnalyticsConfig {
  def isEnabled: Boolean = Try(trackingId, trackingHost).isSuccess

  def trackingId: String = sys.env("ANALYTICS_TRACKING_ID")
  def trackingHost: String = sys.env("ANALYTICS_TRACKING_HOST")
}
