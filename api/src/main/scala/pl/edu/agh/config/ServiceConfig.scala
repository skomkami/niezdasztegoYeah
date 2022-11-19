package pl.edu.agh.config

case class DBConfig(
    driver: String,
    url: String,
    user: String,
    pass: String
)

case class ServerConfig(
    bindHost: String,
    bindPort: Int
)

case class IPNServiceConfig(
    host: String,
    sslEnabled: Boolean
) {
  private lazy val protocol = if (sslEnabled) "https" else "http"

  lazy val url: String = s"$protocol://$host"
}

case class ServiceConfig(
    database: DBConfig,
    server: ServerConfig,
    ipnService: IPNServiceConfig
)
