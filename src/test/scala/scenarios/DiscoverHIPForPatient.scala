package scenarios

import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder
import utils.Environment._


object DiscoverHIPForPatient {

  val requestId = java.util.UUID.randomUUID.toString
  val discoverHIPRequest: String = "{\n    \"hip\": {\n        \"id\": \""+linkedProvider+"\"\n    },\n    \"requestId\": \""+requestId+"\"\n}"
  val userRequestBody = "{\"grantType\":\"password\",\"password\":\""+password+"\",\"username\":\""+username+"\"}"

  val userLogin: ChainBuilder = exec(
    http("create session")
      .post("/cm/sessions")
      .body(StringBody(userRequestBody))
      .check(status.is(200))
      .check(jsonPath("$.token").findAll.saveAs("userAccessToken"))
  )

  val providerDetails: ChainBuilder = exec(
    http("discover HIP for the user")
      .get("/cm/providers/"+linkedProvider+"?")
      .header("Authorization", "${userAccessToken}")
      .check(status.is(200))
  )

  val discoverHIP: ChainBuilder = exec(
    http("discover care context for the HIP")
      .post("/cm/v1/care-contexts/discover")
      .header("Authorization", "${userAccessToken}")
      .body(StringBody(discoverHIPRequest))
      .check(status.is(200))
  )


  val discoverHIPScenario: ScenarioBuilder =
    scenario("Fetch patient information by HIU")
      .exec(userLogin, providerDetails, discoverHIP)
}
