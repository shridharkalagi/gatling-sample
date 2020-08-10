package scenarios

import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder
import utils.Environment


object GrantConsentRequest {

  val requestId = java.util.UUID.randomUUID.toString
  val loginRequestBody = "{\n\t\"username\": \"admin\",\n\t\"password\": \"password\"}"
  val createConsentRequestBody: String = "{\n    \"consent\": {\n        \"patient\": {\n            \"id\": \"" + Environment.username + "\"\n        },\n        \"purpose\": {\n            \"code\": \"CAREMGT\"\n        },\n        \"hiTypes\": [\n            \"OPConsultation\"\n        ],\n        \"permission\": {\n            \"dateRange\": {\n                \"from\": \"1992-04-03T10:05:26.352Z\",\n                \"to\": \"2020-08-08T10:05:26.352Z\"\n            },\n            \"dataEraseAt\": \"2020-10-30T12:30:00.352Z\"\n        }\n    }\n}"
  val userRequestBody: String = "{\"grantType\":\"password\",\"password\":\"" + Environment.username + "\",\"username\":\"" + Environment.username + "\"}"
  val grantConsentRequestBody: String = " {\"consents\": [\n" + "       {\n" + "\"careContexts\": [{\"careContextReference\": \"NCP1007\",    \n" + "                    \"patientReference\": \"RVH1002\"      \n" + "                },                                         \n" + "                {                                          \n" + "                    \"careContextReference\": \"RV-MHD-01.17.0024\",    \n" + "                    \"patientReference\": \"RVH1002\"      \n" + "                }                                          \n" + "           ],                                              \n" + "           \"hiTypes\": [                                  \n" + "               \"Prescription\"                            \n" + "           ],                                              \n" + "           \"hip\": {                                      \n" + "               \"id\": \"10000005\"                        \n" + "           },                                              \n" + "           \"permission\": {                               \n" + "               \"accessMode\": \"VIEW\",                   \n" + "               \"dataEraseAt\": \"2020-12-27T10:45:54.688\",    \n" + "               \"dateRange\": {                            \n" + "                   \"from\": \"1992-06-25T18:30:00\",      \n" + "                   \"to\": \"2020-06-26T10:45:54.688\"     \n" + "               },                                          \n" + "               \"frequency\": {                            \n" + "                   \"value\": 1,                           \n" + "                   \"unit\": \"HOUR\",                     \n" + "                   \"repeats\": 0                          \n" + "               }                                           \n" + "           }                                               \n" + "       }                                                   \n" + "   ]                                                       \n" + "}                                                          "
  val approvePINRequestBody: String = "{\"pin\": \"1234\",\n" + "   \"requestId\": \"" + requestId + "\"" + ",\n" + "   \"scope\": \"consentrequest.approve\" \n" + "}"


  val hiuUserLogin: ChainBuilder = exec(
    http("create session")
      .post("/api-hiu/sessions")
      .body(StringBody(loginRequestBody))
      .check(status.is(200))
      .check(jsonPath("$.accessToken").findAll.saveAs("accessToken"))
  )

  val getConsentRequestId: ChainBuilder = exec(
    http("get consent request id")
      .get("/api-hiu/v1/hiu/consent-requests")
      .header("Authorization", "${accessToken}")
      .check(status.is(200))
      .check(jsonPath("$..[0].consentRequestId").is("REQUESTED"))
      .check(jsonPath("$..[0].consentRequestId").findAll.saveAs("consentRequestId"))
  )

  val userLogin: ChainBuilder = exec(
    http("create session")
      .post("/cm/sessions")
      .body(StringBody(userRequestBody))
      .check(status.is(200))
      .check(jsonPath("$.token").findAll.saveAs("userAccessToken"))
  )

  val userPINAuthorization: ChainBuilder = exec(
    http("validate approve PIN")
      .post("/cm/sessions")
      .body(StringBody(approvePINRequestBody))
      .check(status.is(200))
      .check(jsonPath("$.token").findAll.saveAs("pinAccessToken"))
  )

  val grantConsentRequest: ChainBuilder = exec(
    http("grant consent request")
      .post("/cm/consent-requests/\"${consentRequestId}\"/approve")
      .body(StringBody(grantConsentRequestBody))
      .header("Authorization", "${pinAccessToken}")
      .check(status.is(204))
  )

  val grantConsentRequestScenario: ScenarioBuilder =
    scenario("Fetch patient information by HIU")
      .exec(hiuUserLogin, getConsentRequestId, userLogin, userPINAuthorization, grantConsentRequest)
}
