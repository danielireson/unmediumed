package unmediumed

import java.{util => ju}

import scala.beans.BeanProperty

class ApiGatewayResponse(
    @BeanProperty val statusCode: Integer,
    @BeanProperty val body: String,
    @BeanProperty val headers: ju.Map[String, Object],
    @BeanProperty val base64Encoded: Boolean)
