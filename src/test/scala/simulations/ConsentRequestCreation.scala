package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import request.hiu.hiuRequests
import utils.Constants._
import utils.Environment.nhaBaseUrl

class ConsentRequestCreation extends Simulation {
  /* Place for arbitrary Scala code that is to be executed before the simulation begins. */
  before {
    println("***** My simulation is about to begin! *****")
  }

  /* Place for arbitrary Scala code that is to be executed after the simulation has ended. */
  after {
    println("***** My simulation has ended! ******")
  }

  val httpProtocol = http
    .baseUrl(nhaBaseUrl)
    .header(CONTENT_TYPE, APPLICATION_JSON)

  val scenarios = scenario("Fetch patient information by HIU").exec(hiuRequests.hiuUserLogin,
    hiuRequests.fetchPatientInfo, hiuRequests.createConsentRequest)

  setUp(scenarios.inject(atOnceUsers(1))).protocols(httpProtocol)
}

