package pl.edu.agh.service

import akka.actor.ActorSystem
import pl.edu.agh.config.ExternalServiceConfig
import pl.edu.agh.model.IpnSourceModel
import pl.edu.agh.server.request.HttpRequestExecutor
import pl.edu.agh.service.ipn.{
  ParagraphsExtractor,
  SourcesExtractor,
  SourcesFetcher
}

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.jdk.CollectionConverters.SeqHasAsJava

case class IpnService(
    config: ExternalServiceConfig
)(implicit
    system: ActorSystem,
    ec: ExecutionContext
) {

  private lazy val httpExecutor = HttpRequestExecutor()

  def getResultsSync(
      searchText: String,
      sizeOpt: Int
  ) =
    Await.result(getResults(searchText, Option(sizeOpt)), 200.seconds).asJava

  def getResults(
      searchText: String,
      sizeOpt: Option[Int]
  ): Future[List[IpnSourceModel]] =
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
          Right(SourcesExtractor.extractSourcePages(str))
        }
      )
      .flatMap(SourcesFetcher(httpExecutor).fetchSources)
      .map { sources =>
        for {
          source <- sources
          paragraph <- ParagraphsExtractor.extractParagraphFromSource(
            source.source
          )
        } yield new IpnSourceModel(source.url, paragraph)
      }

}
