package pl.edu.agh.server

import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.{pathPrefix, pathSingleSlash}
import akka.http.scaladsl.server.{
  Directive0,
  Directive1,
  ExceptionHandler,
  Route
}
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes.InternalServerError
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import pl.edu.agh.config.ServiceConfig
import pl.edu.agh.database.DatabaseConnection
import pl.edu.agh.entities.{User}
import pureconfig.ConfigSource
import pureconfig.generic.auto._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._

import scala.concurrent.ExecutionContextExecutor

object TestServer extends App with CorsSupport {
  val config: ServiceConfig = ConfigSource.default.load[ServiceConfig] match {
    case Right(value)   => value
    case Left(failures) => throw new Exception(failures.toString)
  }
  implicit val system: ActorSystem = ActorSystem("backend")
  implicit val materializer: Materializer = Materializer(system)
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  implicit def myExceptionHandler: ExceptionHandler =
    ExceptionHandler { case e: Exception =>
      extractUri { uri =>
        println(
          s"Request to $uri could not be handled normally. ${e.getMessage}"
        )
        complete(HttpResponse(InternalServerError, entity = e.getMessage))
      }
    }

  val useCustomerIdForResponse: Long => Route = (customerId) =>
    complete(customerId.toString)
  val completeWithResponse: Route = complete("fasdaf")

  // prefer
  val getOrPost: Directive0 = get | post
  val withCustomerId: Directive1[(Long)] =
    parameter("customerId".as[Long])

  val route: Route =
    pathPrefix("data") {
      path("customer") {
        parameter("customerId".as[Long]) { customerId =>
          get {
            complete(s"dupa jasia $customerId")
          }
        }
      } ~ path("engagement") {
        get {
          complete("engagement")
        } ~ post {
          entity(as[User]) { user =>
            complete(s"oh yeahhh: $user")
          }
        }
      }
    } ~ pathPrefix("pages") {
      path("page1") {
        getOrPost(completeWithResponse)
      } ~ path("page2") {
        getOrPost(completeWithResponse)
      }
    }

  val bindingFuture =
    Http()
      .newServerAt(config.server.bindHost, config.server.bindPort)
      .bind(Route.seal(corsHandler(route)))
  scribe.info(
    s"connect to api with URL: http://${config.server.bindHost}:${config.server.bindPort}"
  )

  def shutdown(): Unit =
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
}
