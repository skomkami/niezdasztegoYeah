package pl.edu.agh.server.routes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import pl.edu.agh.service.IPNService
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._

case class GenerateQuizGateway(ipnService: IPNService) {
  val route: Route = pathPrefix("generate-quiz") {
    get {
      parameters("fraze", "size".as[Int].optional) { (fraze, size) =>
        complete(ipnService.getResults(fraze, size))
      }
    }
  }
}
