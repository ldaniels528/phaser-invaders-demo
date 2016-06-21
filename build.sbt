import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt.Keys._
import sbt._

val appVersion = "0.1.0"
val meanjsVersion = "0.2.0"

val _scalaVersion = "2.11.8"
val paradisePluginVersion = "3.0.0-M1"

val jsCommonSettings = Seq(
  organization := "com.github.ldaniels528",
  version := appVersion,
  scalaVersion := _scalaVersion,
  scalacOptions ++= Seq("-feature", "-deprecation"),
  scalacOptions in(Compile, doc) ++= Seq(
    "-no-link-warnings" // Suppresses problems with Scaladoc @throws links
  ),
  relativeSourceMaps := true,
  persistLauncher := true,
  persistLauncher in Test := false,
  homepage := Some(url("https://github.com/ldaniels528/invaders")),
  addCompilerPlugin("org.scalamacros" % "paradise" % paradisePluginVersion cross CrossVersion.full),
  libraryDependencies ++= Seq(
    "org.scala-lang" % "scala-reflect" % _scalaVersion
  )
)

lazy val root = (project in file("."))
  .aggregate(view, nodejs)
  .settings(
    name := "invaders"
  )

lazy val view = (project in file("client"))
  .enablePlugins(ScalaJSPlugin)
  .settings(jsCommonSettings: _*)
  .settings(
    name := "invaders-client",
    libraryDependencies ++= Seq(
      "com.github.ldaniels528" %%% "scalajs-common-browser" % meanjsVersion
    )
  )

lazy val nodejs = (project in file("server"))
  .aggregate(view)
  .dependsOn(view)
  .enablePlugins(ScalaJSPlugin)
  .settings(jsCommonSettings: _*)
  .settings(
    name := "invaders-server",
    //pipelineStages := Seq(gzip, scalaJSProd),
    Seq(packageScalaJSLauncher, fastOptJS, fullOptJS) map { packageJSKey =>
      crossTarget in(view, Compile, packageJSKey) := baseDirectory.value / "public" / "javascripts"
    },
    compile in Compile <<=
      (compile in Compile) dependsOn (fastOptJS in(view, Compile)),
    ivyScala := ivyScala.value map (_.copy(overrideScalaVersion = true)),
    libraryDependencies ++= Seq(
      "com.github.ldaniels528" %%% "scalajs-nodejs-mean-bundle-minimal" % meanjsVersion
    )
  )