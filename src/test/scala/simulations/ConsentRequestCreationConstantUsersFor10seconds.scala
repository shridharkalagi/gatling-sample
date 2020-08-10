package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import scenarios.CreateConsentRequestForAPatient
import utils.Constants._
import utils.Environment.baseUrl

class ConsentRequestCreationConstantUsersFor10seconds extends Simulation {
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

  setUp(CreateConsentRequestForAPatient
    .createConsentRequestScenario
    .inject(constantUsersPerSec(1) during (10))
    .protocols(httpProtocol))

}

