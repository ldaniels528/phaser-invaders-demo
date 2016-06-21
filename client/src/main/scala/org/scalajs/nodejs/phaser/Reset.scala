package org.scalajs.nodejs.phaser

import org.scalajs.nodejs.phaser.PIXI.DisplayObject

import scala.scalajs.js

@js.native
trait Reset extends js.Object {

  def reset(x: Double, y: Double, health: Double = 1): DisplayObject = js.native
}
