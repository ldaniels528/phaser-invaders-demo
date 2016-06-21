package org.scalajs.nodejs.phaser

import scala.scalajs.js

@js.native
trait World extends js.Object {
  var width: Double = js.native

  def centerX: Double = js.native

  def centerY: Double = js.native

}
