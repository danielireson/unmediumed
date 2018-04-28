package unmediumed.response

import java.{util => ju}

import scala.beans.BeanProperty

class Output(
    @BeanProperty val statusCode: Integer,
    @BeanProperty val body: String,
    @BeanProperty val headers: ju.Map[String, String],
    @BeanProperty val base64Encoded: Boolean)
