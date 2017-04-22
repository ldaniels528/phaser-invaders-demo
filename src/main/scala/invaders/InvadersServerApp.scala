package invaders

import io.scalajs.nodejs.process
import io.scalajs.npm.bodyparser.{BodyParser, UrlEncodedBodyOptions}
import io.scalajs.npm.express.{Express, Request, Response}

import scala.scalajs.js
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExportAll

/**
  * Invaders Server Application
  * @author lawrence.daniels@gmail.com
  */
@JSExportAll
object InvadersServerApp extends JSApp {
  type NextFunction = js.Function0[Unit]

  override def main(): Unit = {
    // determine the port to listen on
    val port = process.env.get("port").map(_.toInt) getOrElse 1337

    // setup the application
    println("Loading Express modules...")
    val app = Express()

    // setup the body parsers
    println("Setting up body parsers...")
    app.use(BodyParser.json())
    app.use(BodyParser.urlencoded(new UrlEncodedBodyOptions(extended = true)))

    // setup the routes for serving static files
    println("Setting up the routes for serving static files...")
    app.use(Express.static("public"))
    app.use("/assets", Express.static("public"))
    app.use("/bower_components", Express.static("bower_components"))

    // disable caching
    app.disable("etag")

    // setup logging of the request - response cycles
    app.use((request: Request, response: Response, next: NextFunction) => {
      val startTime = System.currentTimeMillis()
      next()
      response.onFinish(() => {
        val elapsedTime = System.currentTimeMillis() - startTime
        println(s"[node] application - ${request.method} ${request.originalUrl} ~> ${response.statusCode} [$elapsedTime ms]")
      })
    })

    // start the listener
    app.listen(port, () => println(s"Server now listening on port $port"))
    ()
  }

}
