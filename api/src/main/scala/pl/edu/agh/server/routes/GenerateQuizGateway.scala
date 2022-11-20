package pl.edu.agh.server.routes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import pl.edu.agh.generator.api.GenerateQuestionsRequestParams
import pl.edu.agh.service.QuestionGeneratorFacade

case class GenerateQuizGateway(
    questionGeneratorFacade: QuestionGeneratorFacade
) {
  val route: Route = pathPrefix("generate-quiz") {
    get {
      parameters("fraze", "size".as[Int].optional) { (fraze, size) =>
        complete(
          questionGeneratorFacade.generate(
            new GenerateQuestionsRequestParams(
              fraze,
              Integer.valueOf(size.getOrElse(20))
            )
          )
        )
      }
    }
  }
}
