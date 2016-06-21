package org.scalajs.nodejs.phaser

import org.scalajs.nodejs.phaser.PIXI.Point

import scala.scalajs.js

@js.native
trait PhysicsBody extends js.Object {
  val x: Double = js.native
  val y: Double = js.native
  var moves: Boolean = js.native
  val velocity: Point = js.native
}
