package org.scalajs.nodejs.phaser

import org.scalajs.nodejs.phaser.PIXI.DisplayObject

import scala.scalajs.js

@js.native
trait Lifespan extends js.Object {
  val alive: Boolean = js.native

  def kill(): DisplayObject = js.native

  def revive(health: Double = 1): DisplayObject = js.native
}
