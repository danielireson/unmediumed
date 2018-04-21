package unmediumed

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}

class Handler extends RequestHandler[ApiGatewayRequest, ApiGatewayResponse] {
  def handleRequest(input: ApiGatewayRequest, context: Context): ApiGatewayResponse = {
    val request = input.toRequest
    ApiGatewayResponse(200, "Go Serverless v1.0! Your function executed successfully!", Map(
      "path" -> request.path,
      "headers" -> request.headers,
      "pathParameters" -> request.pathParameters
    ))
  }
}
