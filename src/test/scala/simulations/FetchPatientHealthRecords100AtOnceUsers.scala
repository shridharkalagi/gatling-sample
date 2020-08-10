package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import scenarios.FetchPatientHealthRecords
import utils.Constants._
import utils.Environment.baseUrl

class FetchPatientHealthRecords100AtOnceUsers extends Simulation {
  /* Place for arbitrary Scala code that is to be executed before the simulation begins. */
  before {
    println("***** My simulation is about to begin! *****")
  }

  /* Place for arbitrary Scala code that is to be executed after the simulation has ended. */
  after {
    println("***** My simulation has ended! ******")
  }

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl(baseUrl)
    .header(CONTENT_TYPE, APPLICATION_JSON)

  setUp(
    FetchPatientHealthRecords.fetchUserHealthRecords.inject(atOnceUsers(1)).protocols(httpProtocol)
  )
}

