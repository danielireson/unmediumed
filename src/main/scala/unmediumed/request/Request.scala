package unmediumed.request

sealed case class Request(
    httpMethod: String,
    path: String,
    body: String = "",
    headers: Map[String, Object] = Map(),
    pathParameters: Map[String, Object] = Map(),
    queryStringParameters: Map[String, Object] = Map())
