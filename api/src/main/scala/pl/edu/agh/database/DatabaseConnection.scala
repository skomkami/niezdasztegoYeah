package pl.edu.agh.database

import cats.effect.IO
import doobie.Transactor
import pl.edu.agh.config.DBConfig

case class DatabaseConnection(config: DBConfig) {
  lazy val transactor = Transactor.fromDriverManager[IO](
    driver = config.driver,
    url = config.url,
    user = config.user,
    pass = config.pass
  )
}
