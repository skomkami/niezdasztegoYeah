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

case class CurrencyServiceConfig(
    host: String,
    sslEnabled: Boolean,
    apiKey: String
) {
  private lazy val protocol = if (sslEnabled) "https" else "http"

  lazy val url: String = s"$protocol://$host"
}

case class ServiceConfig(
    database: DBConfig,
    server: ServerConfig,
    currencyService: CurrencyServiceConfig
)
