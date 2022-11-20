package pl.edu.agh.service.ipn

import pl.edu.agh.server.request.HttpRequestExecutor
import scala.concurrent.{ExecutionContext, Future}

case class SourcesFetcher(httpExecutor: HttpRequestExecutor)(implicit
    val ec: ExecutionContext
) {

  private def fetchSource(url: String): Future[String] =
    httpExecutor.makeSingleRequestUnsafe(url, Map.empty, Right.apply)

  def fetchSources(urls: List[String]): Future[List[String]] = {
    val futures = urls.map(fetchSource)
    Future.sequence(futures)
  }

}
