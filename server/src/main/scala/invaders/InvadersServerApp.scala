package invaders

import org.scalajs.nodejs.bodyparser.{BodyParser, UrlEncodedBodyOptions}
import org.scalajs.nodejs.express.fileupload.ExpressFileUpload
import org.scalajs.nodejs.express.{Express, Request, Response}
import org.scalajs.nodejs.expressws.{ExpressWS, WsRouterExtensions}
import org.scalajs.nodejs.global.process
import org.scalajs.nodejs.{Bootstrap, console}

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
    console.warn("Use startServer(Bootstrap) instead")
  }

  def startServer(implicit bootstrap: Bootstrap): Unit = {
    implicit val require = bootstrap.require

    // determine the port to listen on
    val port = process.env.get("port").map(_.toInt) getOrElse 1337

    // setup the application
    console.log("Loading Express modules...")
    implicit val express = Express()
    implicit val app = express().withWsRouting
    implicit val wss = ExpressWS()(require)(app)
    implicit val fileUpload = ExpressFileUpload()

    // setup the body parsers
    console.log("Setting up body parsers...")
    val bodyParser = BodyParser()
    app.use(bodyParser.json())
    app.use(bodyParser.urlencoded(new UrlEncodedBodyOptions(extended = true)))

    // setup the routes for serving static files
    console.log("Setting up the routes for serving static files...")
    app.use(fileUpload())
    app.use(express.static("public"))
    app.use("/assets", express.static("public"))
    app.use("/bower_components", express.static("bower_components"))

    // disable caching
    app.disable("etag")

    // setup logging of the request - response cycles
    app.use((request: Request, response: Response, next: NextFunction) => {
      val startTime = System.currentTimeMillis()
      next()
      response.onFinish(() => {
        val elapsedTime = System.currentTimeMillis() - startTime
        console.log("[node] application - %s %s ~> %d [%d ms]", request.method, request.originalUrl, response.statusCode, elapsedTime)
      })
    })

    // start the listener
    app.listen(port, () => console.log("Server now listening on port %d", port))
    ()
  }

}
