package pl.edu.agh.request

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, HttpResponse, Uri}
import akka.util.ByteString

import scala.concurrent.{ExecutionContext, Future}

case class HttpRequestExecutor()(implicit
    system: ActorSystem,
    ec: ExecutionContext
) {

  def makeSingleRequestUnsafe[T](
      url: String,
      params: Map[String, String],
      onSuccessFn: String => Either[String, T]
  ): Future[T] = {
    makeSingleRequest(url, params, onSuccessFn).map { either =>
      either.fold(err => { throw new Exception(err) }, identity)
    }
  }

  def makeSingleRequest[T](
      url: String,
      params: Map[String, String],
      onSuccessFn: String => Either[String, T]
  ): Future[Either[String, T]] =
    Http()
      .singleRequest(
        HttpRequest(
          method = HttpMethods.GET,
          uri = Uri(url).withQuery(Uri.Query(params))
        )
      )
      .flatMap { resp =>
        val respContent = responseContentStr(resp)
        if (resp.status.isSuccess()) respContent.map(onSuccessFn)
        else respContent.map(Left.apply)
      }

  private def responseContentStr(response: HttpResponse): Future[String] =
    response.entity.dataBytes.runFold(ByteString(""))(_ ++ _).map(_.utf8String)

}
