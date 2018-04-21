package unmediumed

import scala.beans.BeanProperty

case class Response(@BeanProperty statusCode: Integer, @BeanProperty body: String,
    @BeanProperty headers: java.util.Map[String, Object], @BeanProperty base64Encoded: Boolean = false)
