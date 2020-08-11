package utils

object Environment {
  val baseUrl = scala.util.Properties.envOrElse("baseURL", "https://preprod.ndhm.gov.in")
  val username = scala.util.Properties.envOrElse("username", "20navjot@ndhm")
  val password = scala.util.Properties.envOrElse("password", "Test@1324")

  val t_users = Integer.getInteger("users", 1).toInt
  val t_rampUp = Integer.getInteger("rampup", 1).toInt
  val t_holdFor = Integer.getInteger("duration", 10).toInt
  val t_throughput = Integer.getInteger("throughput", 100).toInt
}
