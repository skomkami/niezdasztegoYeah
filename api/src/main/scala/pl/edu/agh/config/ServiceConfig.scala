package pl.edu.agh.config

case class ServerConfig(
    bindHost: String,
    bindPort: Int
)

case class ExternalServiceConfig(
    host: String,
    sslEnabled: Boolean
) {
  private lazy val protocol = if (sslEnabled) "https" else "http"

  lazy val url: String = s"$protocol://$host"
}

case class ServiceConfig(
    server: ServerConfig,
    ipnService: ExternalServiceConfig,
    clarinService: ExternalServiceConfig
)
