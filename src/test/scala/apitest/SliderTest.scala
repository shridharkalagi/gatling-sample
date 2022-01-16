package apitest

import io.gatling.core.Predef.Simulation
import io.gatling.core.Predef._
import io.gatling.core.feeder.Feeder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration.DurationInt
import java.util.concurrent.LinkedBlockingDeque


class SliderTest extends Simulation {
  //generate a login token once and use the same token in the subsequent API calls
  object DequeHolder {
    val DataDeque = new LinkedBlockingDeque[String]()
  }

  val theHttpProtocolBuilder: HttpProtocolBuilder = http
    .header("Content-Type", "application/json")
    .header("postman_key", "")
    .baseUrl("https://user-bo.mopservice.in")

  val req = "{    \"mobile\" : \"" + "9844795732" + "\",   \"istest\": true\n}"

  var bearerToken = ""
  val scn1 = scenario("Scenario Name") // A scenario is a chain of requests and pauses
    .exec(
      http("request_1")
        .post("/auth/send-login-otp")
        .body(StringBody(req)).asJson
        .check(status is 200)
        .check(jsonPath("$.data.session").saveAs("sessionID"))
        .check(jsonPath("$.data.devOtp").saveAs("otp"))
        .check(jsonPath("$.data.mobile").saveAs("mobile"))
        .disableFollowRedirect
    )
    .exec(
      http("request_1.1")
        .post("/auth/verify-login-otp")
        .body(StringBody(
          """
            |{
            |    "otp" : "${otp}",
            |    "session": "${sessionID}",
            |    "mobile": "${mobile}"
            |}
            |""".stripMargin)).asJson
        .check(status is 200)
        .check(jsonPath("$.data.token").saveAs("authToken"))
        .disableFollowRedirect
    )
    .exec(
      session => {
        DequeHolder.DataDeque.offerLast(session("authToken").as[String])
        bearerToken = session("authToken").as[String]
        println("%%%%%%%%%%% ID =====>>>>>>>>>> " + bearerToken)
        session
      }
    )


  val scn2 = scenario("Scenario Name 2") // A scenario is a chain of requests and pauses
    .feed(new DataFeeder())
    .exec(
      http("request_2")
        .get("/common/slider")
        .header("Authorization", "Bearer " + """${authToken}""")
        .check(status is 200)
        .disableFollowRedirect
    )


  setUp(
    scn1.inject(atOnceUsers(1)).protocols(theHttpProtocolBuilder),
    scn2.inject(nothingFor(4.seconds), atOnceUsers(2000)).protocols(theHttpProtocolBuilder)

  )


  class DataFeeder extends Feeder[String] {
    override def hasNext: Boolean = DequeHolder.DataDeque.size() > 0

    override def next(): Map[String, String] = Map("authToken" -> DequeHolder.DataDeque.peek())
  }
}



