package org.scalajs.nodejs.phaser

import org.scalajs.nodejs.phaser.PIXI.Point

import scala.scalajs.js

@js.native
trait TileSprite extends js.Object {
  val tilePosition: Point = js.native
}
