package unmediumed.response

import java.{util => ju}

import scala.beans.BeanProperty

class Output(
    @BeanProperty var statusCode: Integer,
    @BeanProperty var body: String,
    @BeanProperty var headers: ju.Map[String, String],
    @BeanProperty var base64Encoded: Boolean)
