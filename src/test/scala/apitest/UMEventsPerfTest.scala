package apitest

import io.gatling.core.Predef.{Simulation, _}
import io.gatling.core.feeder.Feeder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import java.util.concurrent.LinkedBlockingDeque
import scala.concurrent.duration.DurationInt


class UMEventsPerfTest extends Simulation {
  //generate a login token once and use the same token in the subsequent API calls

  val theHttpProtocolBuilder: HttpProtocolBuilder = http
    .header("Content-Type", "application/json")
    .header("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI5ODQ0Nzk1NzMyIiwiaWF0IjoxNjk3MjY5Njk4LCJyb2xlIjpbeyJhdXRob3JpdHkiOiJST0xFX05PUk1BTF9VU0VSIn1dLCJleHAiOjE2OTk4NjE2OTh9.4Y31y0DBsZOqLUAX0pnFS-yaaMsnNOoTxAklVaxefucgurjhr_l827pwyBSuM7N0pCS8UMEWKxG9gdJz1Tnyyg")
    .baseUrl("https://eventsapi.umapps.in")

  val req = "{\n    \"devoteeList\": [\n        {\n            \"devoteeAge\": 20,\n            \"devoteeGender\": \"MALE\",\n            \"devoteeName\": \"Shri\"\n        },\n        {\n            \"devoteeAge\": 20,\n            \"devoteeGender\": \"MALE\",\n            \"devoteeName\": \"Krishna\"\n        },\n        {\n            \"devoteeAge\": 20,\n            \"devoteeGender\": \"MALE\",\n            \"devoteeName\": \"Yenkappa\"\n        },\n        {\n            \"devoteeAge\": 20,\n            \"devoteeGender\": \"MALE\",\n            \"devoteeName\": \"Narsimma\"\n        }\n    ],\n    \"emailId\": \"um@1008.com\",\n    \"slotId\": 3\n}"

  var bearerToken = ""
  val scn1 = scenario("BOOK EVENT") // A scenario is a chain of requests and pauses
    .exec(
      http("BOOKrequest_1")
        .post("/book-event")
        .body(StringBody(req)).asJson
        .check(status is 200)
        .disableFollowRedirect
    )

  val scn2 = scenario("GET EVENT") // A scenario is a chain of requests and pauses
    .exec(
      http("GETrequest_2")
        .get("/event-details")
        .header("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI5ODQ0Nzk1NzMyIiwiaWF0IjoxNjk3MjY5Njk4LCJyb2xlIjpbeyJhdXRob3JpdHkiOiJST0xFX05PUk1BTF9VU0VSIn1dLCJleHAiOjE2OTk4NjE2OTh9.4Y31y0DBsZOqLUAX0pnFS-yaaMsnNOoTxAklVaxefucgurjhr_l827pwyBSuM7N0pCS8UMEWKxG9gdJz1Tnyyg")
        .check(status is 200)
        .disableFollowRedirect
    )


  setUp(
    scn1.inject(constantUsersPerSec(66).during(60)).protocols(theHttpProtocolBuilder),
    scn2.inject(constantUsersPerSec(66).during(60)).protocols(theHttpProtocolBuilder)

  )

}



