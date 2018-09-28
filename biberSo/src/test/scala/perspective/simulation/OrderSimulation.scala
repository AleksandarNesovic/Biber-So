package com.developers.perspective.simulation

/**
  * Created by dmumma on 11/20/15.
  */

import com.developers.perspective.util._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import perspective.scenarios.GetOrdersByDate

class OrderSimulation extends Simulation{

val httpConf = http.baseURL(Environemnt.baseURL)
                      .headers(Headers.commonHeader)

val orderScenarios=List(
	GetOrdersByDate.getOrdersByDate.inject(
      atOnceOrder(1),
      //rampUsers(100) over(1 seconds),
      //constantUsersPerSec(1000) during(15 seconds)
      rampUsersPerSec(1) to 100 during(30 seconds) // 6
      //rampUsersPerSec(10) to 20 during(10 minutes) randomized, // 7
      //splitUsers(1000) into(rampUsers(10) over(10 seconds)) separatedBy(10 seconds), // 8
      //splitUsers(1000) into(rampUsers(10) over(10 seconds)) separatedBy atOnceUsers(30), // 9
      //heavisideUsers(1000) over(20 seconds) // 10
    )
setUp(orderScenarios)
    .protocols(httpConf)
    .maxDuration(1 minutes)
    .assertions(
      global.responseTime.max.lessThan(Environemnt.maxResponseTime.toInt)
    )
)

}
