package org.scalajs.nodejs.phaser

import scala.scalajs.js

@js.native
trait RandomDataGenerator extends js.Object {
  def integerInRange(min: Int, max: Int): Int = js.native
}
