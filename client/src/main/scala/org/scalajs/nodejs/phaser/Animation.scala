package org.scalajs.nodejs.phaser

import scala.scalajs.js

@js.native
trait Animation extends js.Object {
  def play(frameRate: Int = 60, loop: Boolean = false, killOnComplete: Boolean = false): Animation = js.native
}
