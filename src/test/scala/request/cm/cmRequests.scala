package request.cm

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import utils.Constants.{APPLICATION_JSON, CONTENT_TYPE}
import utils.Environment._

class cmRequests extends Simulation {

  val userRequestBody = "{\"grantType\":\"password\",\"password\":\"Test@1324\",\"username\":\"navjot60@ndhm\"}"

//  val httpProtocol = http
//    .baseUrl(cmBaseUrl)
//    .header(CONTENT_TYPE, APPLICATION_JSON)

  val userLogin = exec(
    http("create session")
      .post("/sessions")
      .body(StringBody(userRequestBody))
      .check(status.is(200))
      .check(jsonPath("$.token").findAll.saveAs("token"))
  )

//  val scenarios = scenario("Fetch patient information by HIU").exec(userLogin)

//  setUp(scenarios.inject(atOnceUsers(1))).protocols(httpProtocol)
}
