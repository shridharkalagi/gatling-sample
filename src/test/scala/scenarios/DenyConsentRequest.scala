package scenarios

import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder
import utils.Environment._


object DenyConsentRequest {

  val loginRequestBody: String = "{\n\t\"username\": \"admin\",\n\t\"password\": \"tzDvMGYvEM3SFHB6\"}"
  val createConsentRequestBody: String = "{\n    \"consent\": {\n        \"patient\": {\n            \"id\": \"" + username + "\"\n        },\n        \"purpose\": {\n            \"code\": \"CAREMGT\"\n        },\n        \"hiTypes\": [\n            \"OPConsultation\"\n        ],\n        \"permission\": {\n            \"dateRange\": {\n                \"from\": \"1992-04-03T10:05:26.352Z\",\n                \"to\": \"2020-08-08T10:05:26.352Z\"\n            },\n            \"dataEraseAt\": \"2020-10-30T12:30:00.352Z\"\n        }\n    }\n}"
  val userRequestBody: String = "{\"grantType\":\"password\",\"password\":\"" + password + "\",\"username\":\"" + username + "\"}"


  val hiuUserLogin: ChainBuilder = exec(
    http("create hiu session")
      .post("/api-hiu/sessions")
      .body(StringBody(loginRequestBody))
      .check(status.is(200))
      .check(jsonPath("$.accessToken").findAll.saveAs("accessToken"))
  )

  //  val fetchPatientInfo: ChainBuilder = exec(
  //    http("patient info")
  //      .get("/v1/patients/" + Environment.username + "?")
  //      .header("Authorization", "${accessToken}")
  //      .check(status.is(200))
  //  )
  //
  //  val createConsentRequest: ChainBuilder = exec(
  //    http("create consent request")
  //      .post("/v1/hiu/consent-requests")
  //      .header("Authorization", "${accessToken}")
  //      .body(StringBody(createConsentRequestBody)).asJson
  //      .check(status.is(202))
  //  )

  val getConsentRequestId: ChainBuilder = exec(
    http("get consent request id")
      .get("/api-hiu/v1/hiu/consent-requests")
      .header("Authorization", "${accessToken}")
      .check(status.is(200))
//      .check(jsonPath("$..[0].consentRequestId").findAll.saveAs("request"))
      .check(bodyString.saveAs("BODY"))
  ).exec(session => {
    val body = session("BODY").as[String]
    println(body)
    session
  })
  //  )

  val userLogin: ChainBuilder = exec(
    http("create patient session")
      .post("/cm/sessions")
      .body(StringBody(userRequestBody))
      .check(status.is(200))
      .check(jsonPath("$.token").findAll.saveAs("userAccessToken"))
  )

  val denyHIUConsentRequest: ChainBuilder = exec(
    http("grant consent request")
      .post("/cm/consent-requests/\"${consentId}\"/deny")
      .header("Authorization", "${userAccessToken}")
      .check(status.is(204))
  )

  val denyConsentRequest1: ScenarioBuilder =
    scenario("Fetch patient information by HIU1")
      .exec(hiuUserLogin, getConsentRequestId)

  val denyConsentRequest2: ScenarioBuilder =
    scenario("Fetch patient information by HIU2")
      .exec(userLogin, denyHIUConsentRequest)


}
