# unmediumed
A Markdown API for Medium that deploys to AWS Lambda.

## Usage
Change the domain name of a Medium post URL to `unmediumed.com` to view as Markdown. The `http` protocol and the `medium.com` domain will be automatically assumed for `medium.com` domain posts. For publications with custom domains the full URL should be provided.

```
# with protocol and medium domain
https://unmediumed.com/https://medium.com/@danielireson/three-tips-when-using-npm-as-a-website-build-system-827d29606715

# without protocol
https://unmediumed.com/medium.com/@danielireson/three-tips-when-using-npm-as-a-website-build-system-827d29606715

# without medium domain
https://unmediumed.com/@danielireson/three-tips-when-using-npm-as-a-website-build-system-827d29606715

# publication with custom domain
https://unmediumed.com/https://medium.freecodecamp.org/how-to-build-a-serverless-url-shortener-using-aws-lambda-and-s3-4fbdf70cbf5c
```

## Building and deploying
Ensure you have [SBT](https://www.scala-sbt.org), the [Serverless Framework](https://serverless.com) and the [AWS CLI](https://aws.amazon.com/cli) installed. The required AWS infrastructure defined in `serverless.yml` will be automatically created on first deploy. The custom domain then needs manually configuring for the S3 bucket and API Gateway endpoint. The website is expected to be hosted from the `unmediumed.com` bucket at the domain root and the API hosted on the `md` subdomain. Optionally the `www` subdomain can be assigned to the `www.unmediumed.com` bucket to redirect www requests to the root domain.

```
# running the test suite
(cd api && sbt test)

# building the API using sbt assembly
./scripts/build-api

# deploying the API using serverless framework
./scripts/deploy-api

# deploying the website to S3 bucket
./scripts/deploy-website
```

Basic request analytics can be sent to Google Analytics. To enable, configure two environment variables for the AWS Lambda handler with the Google Analytics tracking ID and custom domain name with `ANALYTICS_TRACKING_ID` and `ANALYTICS_TRACKING_HOST` respectively.

## License
This project is licensed under the MIT License.
