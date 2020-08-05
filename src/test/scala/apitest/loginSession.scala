package apitest

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder
import scala.concurrent.duration._

class loginSession extends Simulation {
  /* Place for arbitrary Scala code that is to be executed before the simulation begins. */
  before {
    println("***** My simulation is about to begin! *****")
  }

  /* Place for arbitrary Scala code that is to be executed after the simulation has ended. */
  after {
    println("***** My simulation has ended! ******")
  }

  val req = "{\n\t\"username\": \"pmon.201@ndhm\",\n\t\"password\": \"Test@135\",\n\t\"grantType\": \"password\"\n}"

  val theHttpProtocolBuilder: HttpProtocolBuilder = http
    .header("Content-Type", "application/json")
    .baseUrl("http://uat.ndhm.gov.in/cm") //UAT
  //    .baseUrl("http://dev.tweka.in/cm")   //NHA
  //    .baseUrl("https://ncg-dev.projecteka.in/consent-manager")   //NCG

  val scn = scenario("Scenario1")
    .exec(
      http("loginRequest")
        .post("/sessions")
        .body(StringBody(req))
        .check(status.is(200))
        .disableFollowRedirect
    )

  setUp(
    scn.inject(rampUsers(500) during (10 seconds))
  ).protocols(theHttpProtocolBuilder)
}

