package request.hiu

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder
import utils.Environment


object hiuRequests {

  val loginRequestBody = "{\n\t\"username\": \"admin\",\n\t\"password\": \"password\"}"
  val createConsentRequestBody = "{\n    \"consent\": {\n        \"patient\": {\n            \"id\": \"" + Environment.username + "\"\n        },\n        \"purpose\": {\n            \"code\": \"CAREMGT\"\n        },\n        \"hiTypes\": [\n            \"OPConsultation\"\n        ],\n        \"permission\": {\n            \"dateRange\": {\n                \"from\": \"1992-04-03T10:05:26.352Z\",\n                \"to\": \"2020-08-08T10:05:26.352Z\"\n            },\n            \"dataEraseAt\": \"2020-10-30T12:30:00.352Z\"\n        }\n    }\n}"


  val hiuUserLogin = exec(
    http("create session")
      .post("/sessions")
      .body(StringBody(loginRequestBody))
      .check(status.is(200))
      .check(jsonPath("$.accessToken").findAll.saveAs("accessToken"))
  )

  val fetchPatientInfo = exec(
    http("patient info")
      .get("/v1/patients/" + Environment.username + "?")
      .header("Authorization", "${accessToken}")
      .check(status.is(200))
  )

  val createConsentRequest = exec(
    http("create consent request")
      .post("/v1/hiu/consent-requests")
      .header("Authorization", "${accessToken}")
      .body(StringBody(createConsentRequestBody)).asJson
      .check(status.is(202))
  )
}
