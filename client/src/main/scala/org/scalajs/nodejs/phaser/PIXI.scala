package org.scalajs.nodejs.phaser

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

/**
  * Phaser PIXI
  * @version 2.5.0
  * @author lawrence.daniels@gmail.com
  */
@JSName("PIXI")
@js.native
object PIXI extends js.Object {

  @JSName("Point")
  @js.native
  trait Point extends js.Object {
    var x: Double = js.native
    var y: Double = js.native

    def setTo(x: Double, y: Double): Point = js.native
  }

  @JSName("DisplayObject")
  @js.native
  trait DisplayObject extends js.Object {
    var x: Double = js.native
    var y: Double = js.native

    var angle: Double = js.native
    var alpha: Double = js.native

    var visible: Boolean = js.native
    val anchor: Point = js.native
  }

  @JSName("DisplayObjectContainer")
  @js.native
  trait DisplayObjectContainer extends DisplayObject

}

