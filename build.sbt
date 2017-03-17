import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt.Keys._
import sbt._

val appVersion = "0.1.2"
val scalaJsVersion = "2.12.1"
val scalaJsIOVersion = "0.3.0.7"

val jsCommonSettings = Seq(
  organization := "com.github.ldaniels528",
  version := appVersion,
  scalaVersion := scalaJsVersion,
  scalacOptions ++= Seq("-feature", "-deprecation"),
  scalacOptions in(Compile, doc) ++= Seq(
    "-no-link-warnings" // Suppresses problems with Scaladoc @throws links
  ),
  relativeSourceMaps := true,
  homepage := Some(url("https://github.com/scalajs-io/phaser-invaders-demo")),
  libraryDependencies ++= Seq(
    "org.scala-lang" % "scala-reflect" % scalaJsVersion
  ))

lazy val client = (project in file("client"))
  .enablePlugins(ScalaJSPlugin)
  .settings(jsCommonSettings: _*)
  .settings(
    name := "phaser-invaders-client",
    persistLauncher := true,
    persistLauncher in Test := false,
    libraryDependencies ++= Seq(
      "io.scalajs" %%% "dom-html" % scalaJsIOVersion,
      "io.scalajs" %%% "pixijs" % "4.3.5-3",
      "io.scalajs" %%% "phaser" % "2.6.2-4"
    ))

lazy val server = (project in file("."))
  .aggregate(client)
  .dependsOn(client)
  .enablePlugins(ScalaJSPlugin)
  .settings(jsCommonSettings: _*)
  .settings(
    name := "phaser-invaders-demo",
    autoCompilerPlugins := true,
    scalaJSModuleKind := ModuleKind.CommonJSModule,
    Seq(packageScalaJSLauncher, fastOptJS, fullOptJS) map { packageJSKey =>
      crossTarget in(client, Compile, packageJSKey) := baseDirectory.value / "public" / "javascripts"
    },
    compile in Compile <<=
      (compile in Compile) dependsOn (fastOptJS in(client, Compile)),
    ivyScala := ivyScala.value map (_.copy(overrideScalaVersion = true)),
    libraryDependencies ++= Seq(
      "io.scalajs" %%% "nodejs" % scalaJsIOVersion,
      "io.scalajs.npm" %%% "express" % "4.14.1-3",
      "io.scalajs.npm" %%% "body-parser" % "1.16.0-3"
    ))

// loads the Scalajs-io root project at sbt startup
onLoad in Global := (Command.process("project server", _: State)) compose (onLoad in Global).value