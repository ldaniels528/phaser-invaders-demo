package org.scalajs.nodejs.phaser

import org.scalajs.nodejs.phaser.PIXI.DisplayObject

import scala.scalajs.js

@js.native
trait Sprite extends Core with DisplayObject with Lifespan with Reset {
  var body: PhysicsBody = js.native

  def play(key: String, frameRate: Int = 60, loop: Boolean = false, killOnComplete: Boolean = false): Animation = js.native
}
