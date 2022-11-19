package pl.edu.agh.gateway

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import doobie.implicits.toSqlInterpolator
import pl.edu.agh.database.DatabaseConnection
import pl.edu.agh.entities.User

case class UsersGateway(dbConnection: DatabaseConnection)
    extends EntityGateway(dbConnection) {
  val route: Route = pathPrefix("users") {
    pathPrefix(IntNumber) { userId =>
      get(
        complete(
          sql"SELECT * FROM users WHERE id=$userId"
            .queryOne[User]
        )
      )
    } ~ post {
      entity(as[User]) { user =>
        complete(sql"INSERT INTO users ".queryOne[Int])
      }
    }
  }
}
