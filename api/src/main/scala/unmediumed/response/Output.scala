package unmediumed.response

import scala.beans.BeanProperty

class Output(
    @BeanProperty var statusCode: Integer,
    @BeanProperty var body: String,
    @BeanProperty var headers: java.util.Map[String, String],
    @BeanProperty var base64Encoded: Boolean)
