package pl.edu.agh.service

import akka.actor.ActorSystem
import pl.edu.agh.config.IPNServiceConfig
import pl.edu.agh.ipn.{ParagraphsExtractor, SourcesExtractor, SourcesFetcher}
import pl.edu.agh.request.HttpRequestExecutor

import scala.concurrent.{ExecutionContext, Future}

case class IPNService(
    config: IPNServiceConfig
)(implicit
    system: ActorSystem,
    ec: ExecutionContext
) {

  private lazy val httpExecutor = HttpRequestExecutor()

  def getResults(
      searchText: String,
      sizeOpt: Option[Int]
  ): Future[List[String]] =
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
