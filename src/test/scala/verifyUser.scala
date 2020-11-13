//import io.gatling.core.Predef._
//import io.gatling.http.Predef._
//import io.gatling.http.protocol.HttpProtocolBuilder
//import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder
//
//import scala.concurrent.duration._
//import scala.util.Random
//
///**
// * Example Gatling load test that sends one HTTP GET requests to a URL.
// * Note that the request is redirected and this causes the request count to become two.
// * Run this simulation with:
// * mvn -Dgatling.simulation.name=HttpSimulation1 gatling:test
// *
// */
//class verifyUser extends Simulation {
//  /* Place for arbitrary Scala code that is to be executed before the simulation begins. */
//  before {
//    println("***** My simulation is about to begin! *****")
//  }
//
//  /* Place for arbitrary Scala code that is to be executed after the simulation has ended. */
//  after {
//    println("***** My simulation has ended! ******")
//  }
//
//  /*
//   * A scenario consists of one or more requests. For instance logging into a e-commerce
//   * website, placing an order and then logging out.
//   * One simulation can contain many scenarios.
//   */
//  /* Scenario1 is a name that describes the scenario. */
//  val req = "{\n\t\"identifierType\": \"mobile\",\n\t\"identifier\": \"+91-8888888888\"\n}"
//
//
//  /*
//   * A HTTP protocol builder is used to specify common properties of request(s) to be sent,
//   * for instance the base URL, HTTP headers that are to be enclosed with all requests etc.
//   */
//  val theHttpProtocolBuilder: HttpProtocolBuilder = http
//    .header("Content-Type", "application/json")
//    //    .baseUrl("http://uat.ndhm.gov.in/cm")
//    .baseUrl("http://dev.tweka.in/cm")
//  val scn = scenario("Scenario1")
//    .feed(randomSession)
//    //    .exec { session =>
//    //      println(session)
//    //      session
//    //    }
//    .exec(
//      /* myRequest1 is a name that describes the request. */
//
//      http("verifyRequest")
//        .post("/users/verify")
//
//        .body(StringBody("""${randsession}"""))
//        .check(status.is(201))
//        .disableFollowRedirect
//    )
//
//  println(s"$req")
//  var randomSession = Iterator.continually(Map("randsession" -> (
//    req.replace("8888888888", randomStringGenerator.randomString(10)))))
//  //    .baseUrl("https://ncg-dev.projecteka.in/consent-manager")
//
//  object randomStringGenerator {
//
//    def randomString(length: Int) = Random.alphanumeric.filter(_.isDigit).take(length).mkString
//  }
//
//
//  /*
//   * Define the load simulation.
//   * Here we can specify how many users we want to simulate, if the number of users is to increase
//   * gradually or if all the simulated users are to start sending requests at once etc.
//   * We also specify the HTTP protocol builder to be used by the load simulation.
//   */
//  setUp(
//    scn.inject(rampUsers(10) during (10 seconds))
//  ).protocols(theHttpProtocolBuilder)
//}
//
