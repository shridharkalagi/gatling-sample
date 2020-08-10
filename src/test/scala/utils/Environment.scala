package utils

object Environment {
  val baseUrl = scala.util.Properties.envOrElse("baseURL", "https://uat.ndhm.gov.in")
  val username = scala.util.Properties.envOrElse("username", "20navjot@ndhm")
  val password = scala.util.Properties.envOrElse("password", "Test@1324")
}
