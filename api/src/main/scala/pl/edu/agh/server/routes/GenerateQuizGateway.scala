package pl.edu.agh.server.routes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import pl.edu.agh.common.AppLogger
import pl.edu.agh.model.GenerateQuestionsRequestParams
import pl.edu.agh.service.QuestionGeneratorFacade

case class GenerateQuizGateway(
    questionGeneratorFacade: QuestionGeneratorFacade
) {
  val route: Route = pathPrefix("generate-quiz") {
    get {
      parameters("phrase", "size".as[Int].optional) { (phrase, size) =>
        AppLogger.debug("Request for phrase: " + phrase)
        val result = questionGeneratorFacade.generate(
          new GenerateQuestionsRequestParams(
            phrase,
            Integer.valueOf(size.getOrElse(30))
          )
        )
        complete(
          result
        )
      }
    }
  }
}
