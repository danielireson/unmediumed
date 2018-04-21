package unmediumed

import java.{util => ju}

import scala.beans.BeanProperty

case class ApiGatewayResponse(
    @BeanProperty statusCode: Integer,
    @BeanProperty body: String,
    @BeanProperty headers: ju.Map[String, Object],
    @BeanProperty base64Encoded: Boolean = true)


object ApiGatewayResponse {
  def apply(statusCode: Integer, body: String, headers: Map[String, Object] = Map()) {
    new ApiGatewayResponse(statusCode, body, headers)

  }
}