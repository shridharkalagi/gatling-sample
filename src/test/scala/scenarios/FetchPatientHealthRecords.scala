package scenarios

import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder
import utils.Constants
import utils.Constants._
import utils.Environment._


object FetchPatientHealthRecords {

  val createConsentRequestBody: String = "{\n    \"consent\": {\n        \"patient\": {\n            \"id\": \"" + username + "\"\n        },\n        \"purpose\": {\n            \"code\": \"CAREMGT\"\n        },\n        \"hiTypes\": [\n            \"OPConsultation\"\n        ],\n        \"permission\": {\n            \"dateRange\": {\n                \"from\": \"1992-04-03T10:05:26.352Z\",\n                \"to\": \"2020-08-08T10:05:26.352Z\"\n            },\n            \"dataEraseAt\": \"2020-10-30T12:30:00.352Z\"\n        }\n    }\n}"
  val userRequestBody: String = "{\"grantType\":\"password\",\"password\":\"" + password + "\",\"username\":\"" + username + "\"}"

  val userLogin: ChainBuilder = exec(
    http("create session")
      .post("/cm/sessions")
      .body(StringBody(userRequestBody))
      .check(status.is(200))
      .check(jsonPath("$.token").findAll.saveAs("userAccessToken"))
      .check(bodyString.saveAs("BODY"))
  ).exec(session => {
    val body = session("BODY").as[String]
    println(body)
    session
  })
//  )

  val userHealthRecords: ChainBuilder = exec(
    http("user health records")
      .get("/cm/patients/me")
      .header(authorization, "${userAccessToken}")
      .check(status.is(200))
      .check(bodyString.saveAs("BODY"))
  ).exec(session => {
    val body = session("BODY").as[String]
    println(body)
    session
  })
//  )


  val fetchUserHealthRecords: ScenarioBuilder =
    scenario("Fetch patient information by HIU")
      .exec(userLogin, userHealthRecords)
}
