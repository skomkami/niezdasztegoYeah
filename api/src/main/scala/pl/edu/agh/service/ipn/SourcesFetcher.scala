package pl.edu.agh.service.ipn

import pl.edu.agh.server.request.HttpRequestExecutor
import pl.edu.agh.service.ipn.SourcesFetcher.FetchedSource

import scala.concurrent.{ExecutionContext, Future}

case class SourcesFetcher(httpExecutor: HttpRequestExecutor)(implicit
    val ec: ExecutionContext
) {

  private def fetchSource(url: String): Future[FetchedSource] =
    httpExecutor.makeSingleRequestUnsafe(url, Map.empty, Right.apply).map { source =>
      FetchedSource(url, source)
    }

  def fetchSources(urls: List[String]): Future[List[FetchedSource]] = {
    val futures = urls.map(fetchSource)
    Future.sequence(futures)
  }

}

object SourcesFetcher {
  case class FetchedSource(url: String, source: String)
}
