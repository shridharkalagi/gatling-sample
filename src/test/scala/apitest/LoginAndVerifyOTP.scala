package apitest

import io.gatling.core.Predef.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import scala.util.Random


class LoginAndVerifyOTP extends Simulation {

  object randomStringGenerator {
    def randomString(length: Int) = Random.alphanumeric.filter(_.isDigit).take(length).mkString
  }


  val theHttpProtocolBuilder: HttpProtocolBuilder = http
    .header("Content-Type", "application/json")
    .header("postman_key", "")
    .baseUrl("https://user-bo.mopservice.in")

  val req = "{    \"mobile\" : \"" + "8888888888" + "\",   \"istest\": true\n}"


  var randomSession = Iterator.continually(Map("randsession" -> (
    req.replace("8888888888", randomStringGenerator.randomString(10)))))

  val scn = scenario("Scenario Name") // A scenario is a chain of requests and pauses
    .feed(randomSession)
    .exec(
      http("request_1")
        .post("/auth/send-login-otp")
        .body(StringBody("""${randsession}""")).asJson
        .check(status is 200)
        .check(jsonPath("$.data.session").saveAs("sessionID"))
        .check(jsonPath("$.data.devOtp").saveAs("otp"))
        .check(jsonPath("$.data.mobile").saveAs("mobile"))
        .disableFollowRedirect
    )
    .exec(
      http("request_1")
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
//        .check(jsonPath("$.data.token").saveAs("authToken"))
        .disableFollowRedirect
    )

  setUp(
    scn.inject(atOnceUsers(1000)).protocols(theHttpProtocolBuilder)

  )
}
