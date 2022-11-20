ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val doobie = "1.0.0-M1"
lazy val akkaHttp = "10.2.0"
lazy val akkaCore = "2.6.18"
lazy val circe = "0.13.0"
lazy val akkaHttpCirce = "1.39.2"
lazy val quicklens = "1.8.2"
lazy val pureconfig = "0.17.1"
lazy val scribe = "3.6.9"
lazy val tapirSwagger = "0.20.0-M6"

lazy val root = (project in file("."))
  .settings(
    name := "api",
    update / evictionWarningOptions := EvictionWarningOptions.empty,
    libraryDependencies ++= Seq(
      "com.softwaremill.quicklens" %% "quicklens" % quicklens,
      "org.tpolecat" %% "doobie-core" % doobie,
      "org.tpolecat" %% "doobie-postgres" % doobie,
      "org.tpolecat" %% "doobie-specs2" % doobie,
      "org.tpolecat" %% "doobie-postgres-circe" % doobie,
      "io.circe" %% s"circe-generic" % circe,
      "io.circe" %% s"circe-parser" % circe,
      "io.circe" %% s"circe-optics" % circe,
      "io.circe" %% s"circe-refined" % circe,
      "com.typesafe.akka" %% "akka-actor" % akkaCore,
      "com.typesafe.akka" %% "akka-actor-typed" % akkaCore,
      "com.typesafe.akka" %% "akka-stream" % akkaCore,
      "com.typesafe.akka" %% "akka-http" % akkaHttp,
      "de.heikoseeberger" %% "akka-http-circe" % akkaHttpCirce,
      "com.github.pureconfig" %% "pureconfig" % pureconfig,
      "com.outr" %% "scribe" % scribe,
      "com.softwaremill.sttp.tapir" %% "tapir-akka-http-server" % tapirSwagger,
      "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % tapirSwagger,
      "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % tapirSwagger,
      "org.glassfish.jersey.core" % "jersey-client" % "2.22",
      "org.json" % "json" % "20141113"
    )
  )
