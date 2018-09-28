package perspective.scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object GetOrdersByDate{

val getOrdersByDateHttp=http("get orders by date")
			.get("/orders/date")
			.pathParam("date","Java")
			.check(statu.is(200))

val getOrdersByDate=scenario("get orders by date")
.exec(getOrdersByDateHttp)
}
