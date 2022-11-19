package pl.edu.agh.gateway

import cats.effect.unsafe.implicits.global
import doobie.implicits._
import doobie.util.Read
import doobie.util.fragment.Fragment
import pl.edu.agh.database.DatabaseConnection

import scala.concurrent.Future

abstract class EntityGateway(dbConnection: DatabaseConnection) {
  private val xa = dbConnection.transactor
  implicit class FragmentOps(frag: Fragment) {
    def create(): Future[Int] = {
      val io = frag.update.run.transact(xa)
      io.unsafeToFuture()
    }

    def queryOne[E: Read]: Future[E] = {
      val io = frag.query[E].unique.transact(xa)
      io.unsafeToFuture()
    }

    def queryAll[E: Read]: Future[List[E]] = {
      val io = frag.query[E].to[List].transact(xa)
      io.unsafeToFuture()
    }
  }
}
