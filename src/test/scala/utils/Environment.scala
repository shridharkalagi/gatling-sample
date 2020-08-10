package utils

object Environment {
  val nhaBaseUrl = scala.util.Properties.envOrElse("baseURL", "http://dev.tweka.in/hiu-api")
  val cmBaseUrl = scala.util.Properties.envOrElse("baseURL", "http://dev.tweka.in/cm")
//  val nhaBaseUrl = scala.util.Properties.envOrElse("baseURL", "https://dev.ndhm.gov.in/api-hiu")
  //    .baseUrl("http://uat.ndhm.gov.in/cm") //UAT
  //    .baseUrl("https://ncg-dev.projecteka.in/consent-manager")   //NCG
  val username = scala.util.Properties.envOrElse("username", "sharath@ndhm")
  val password = scala.util.Properties.envOrElse("password", "Test@1324")
}
