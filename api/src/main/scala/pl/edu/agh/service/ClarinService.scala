package pl.edu.agh.service

import akka.actor.ActorSystem
import pl.edu.agh.config.ExternalServiceConfig
import pl.edu.agh.server.request.HttpRequestExecutor
import pl.edu.agh.service.ipn.{ParagraphsExtractor, SourcesExtractor, SourcesFetcher}

import scala.concurrent.{ExecutionContext, Future}

case class ClarinService(
    config: ExternalServiceConfig
)(implicit
    system: ActorSystem,
    ec: ExecutionContext
) {

  private lazy val httpExecutor = HttpRequestExecutor()

  def getResults(paragraph: String): Future[List[String]] =
    httpExecutor
      .makeSingleRequestUnsafe(
        url = s"${config.url}/search",
        params = Map(
          "q" -> searchText,
          "site" -> "",
          "sort" -> "date%3AD%3AL%3Ad1",
          "output" -> "xml_no_dtd",
          "client" -> "default_frontend",
          "size" -> sizeOpt.getOrElse(50).toString,
          "doctype" -> "WEB"
        ),
        onSuccessFn = str => {
          Right(SourcesExtractor.extractPages(str))
        }
      )
      .flatMap(SourcesFetcher(httpExecutor).fetchSources)
      .map { sources =>
        sources.flatMap(ParagraphsExtractor.extractParagraphFromSource)
      }

}
