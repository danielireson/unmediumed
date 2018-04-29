package unmediumed.request

sealed case class Request(
    httpMethod: String,
    path: String,
    body: String,
    headers: Map[String, Object],
    pathParameters: Map[String, Object],
    queryStringParameters: Map[String, Object])
