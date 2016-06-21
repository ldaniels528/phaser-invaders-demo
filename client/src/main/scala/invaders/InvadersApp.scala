package invaders

import org.scalajs.browser.phaser.Phaser
import org.scalajs.dom.browser.{console, performance}

import scala.scalajs.js.JSApp

/**
  * Invaders Client App
  */
object InvadersApp extends JSApp {

  override def main(): Unit = {
    val game = new Phaser.Game(800, 600, Phaser.AUTO, "invaders-game")
    game.state.add("initial", new InvadersGame(game))
    game.state.start("initial")
  }

}
