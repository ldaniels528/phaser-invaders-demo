package org.scalajs.nodejs.phaser

import scala.scalajs.js

@js.native
trait AnimationManager extends js.Object {

  def add(name: String,
          frames: js.Array[_] = js.Array(),
          frameRate: js.UndefOr[Int] = 60,
          loop: js.UndefOr[Boolean] = false,
          useNumericIndex: js.UndefOr[Boolean] = true): Animation = js.native
}