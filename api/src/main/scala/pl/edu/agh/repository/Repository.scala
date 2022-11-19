package pl.edu.agh.repository

import cats.effect.IO
import doobie.Fragment
import doobie.implicits._
import doobie.util.Read
import pl.edu.agh.database.DatabaseConnection
import pl.edu.agh.entities.Entity

import scala.reflect.ClassTag

abstract class Repository[E <: Entity: Read](implicit tag: ClassTag[E]) {

  def pluralName: String

  def dbConn: DatabaseConnection

  private lazy val xa = dbConn.transactor

  def createFragment(entity: E): Fragment

  def add(entity: E): IO[Int] = {
    val command = createFragment(entity).update
    command.run.transact(xa)
  }

  def selectAll(from: Option[Int], limit: Option[Int]): IO[Seq[E]] = {
    val f = from.getOrElse(0)
    val l = limit.getOrElse(10)
    sql"select * from users OFFSET $f LIMIT $l"
      .query[E]
      .to[List]
      .transact(xa)
  }
}
