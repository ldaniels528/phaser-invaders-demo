package invaders

import io.scalajs.dom.html.phaser.Phaser
import io.scalajs.dom.html.phaser.Game

import scala.scalajs.js.JSApp

/**
  * Invaders Client Application
  * @author lawrence.daniels@gmail.com
  */
object InvadersApp extends JSApp {

  override def main(): Unit = {
    val game = new Game(800, 600, Phaser.AUTO, "invaders-game")
    game.state.add("initial", new InvadersGame(game))
    game.state.start("initial")
  }

}
