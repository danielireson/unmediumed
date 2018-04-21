package unmediumed

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}

class Handler extends RequestHandler[ApiGatewayRequest, ApiGatewayResponse] {
  def handleRequest(apiGatewayRequest: ApiGatewayRequest, context: Context): ApiGatewayResponse = {
    val request = apiGatewayRequest.toRequest
    val response = Response(200, "Go Serverless v1.0! Your function executed successfully!", Map(
      "x-extra-header" -> "abc"
    ))

    response.toApiGatewayResponse
  }
}
