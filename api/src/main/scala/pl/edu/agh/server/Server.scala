package pl.edu.agh.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes.InternalServerError
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{ExceptionHandler, Route}
import akka.stream.Materializer
import pl.edu.agh.config.ServiceConfig
import pl.edu.agh.server.routes.GenerateQuizGateway
import pl.edu.agh.service.{ClarinService, IpnService, QuestionGeneratorFacade}
import pureconfig.ConfigSource
import pureconfig.generic.auto._

import scala.concurrent.ExecutionContextExecutor

object Server extends App with CorsSupport {

  val config: ServiceConfig = ConfigSource.default.load[ServiceConfig] match {
    case Right(value)   => value
    case Left(failures) => throw new Exception(failures.toString)
  }
  implicit val system: ActorSystem = ActorSystem("backend")
  implicit val materializer: Materializer = Materializer(system)
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  implicit def exceptionHandler: ExceptionHandler =
    ExceptionHandler { case e: Exception =>
      extractUri { uri =>
        println(
          s"Request to $uri could not be handled normally. ${e.getMessage}"
        )
        complete(HttpResponse(InternalServerError, entity = e.getMessage))
      }
    }

  val ipnService = IpnService(config.ipnService)
  val clarinService = ClarinService(config.clarinService)
  val questionGeneratorFacade = new QuestionGeneratorFacade(ipnService, clarinService)
  val quizApi = GenerateQuizGateway(questionGeneratorFacade)


  val route: Route = Route.seal(pathPrefix("api") {
    quizApi.route
  })

  val bindingFuture =
    Http()
      .newServerAt(config.server.bindHost, config.server.bindPort)
      .bind(corsHandler(route))
  scribe.info(
    s"connect to api with URL: http://${config.server.bindHost}:${config.server.bindPort}"
  )

  def shutdown(): Unit =
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
}
