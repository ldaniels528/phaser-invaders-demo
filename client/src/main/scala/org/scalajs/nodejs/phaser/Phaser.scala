package org.scalajs.nodejs.phaser

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

/**
  * phaser - A fast, free and fun HTML5 Game Framework for Desktop and Mobile web browsers.
  * @version 2.5.0
  * @author lawrence.daniels@gmail.com
  */
@js.native
@JSName("Phaser")
object Phaser extends js.Object {
  val AUTO: Int = js.native

  @js.native
  @JSName("Physics")
  object Physics extends Physics

  @js.native
  @JSName("Keyboard")
  object Keyboard extends Keyboard

  @js.native
  @JSName("Easing")
  object Easing extends js.Object {

    @js.native
    @JSName("Linear")
    object Linear extends Linear

  }

  @js.native
  @JSName("Game")
  class Game(val width: Int = 800,
             val height: Int = 600,
             val renderer: Int = Phaser.AUTO,
             val name: String = "phaser-game") extends js.Object {
    val load: Loader = js.native
    val state: State = js.native
    val physics: Physics = js.native
    val add: GameObjectFactory = js.native
    val world: World = js.native
    val input: Input = js.native
    val time: Time = js.native
    val rnd: RandomDataGenerator = js.native
  }

}