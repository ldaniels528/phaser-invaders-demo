package org.scalajs.nodejs.phaser

import org.scalajs.nodejs.phaser.PIXI.DisplayObjectContainer

import scala.scalajs.js
import scala.scalajs.js.|

@js.native
trait Group[T] extends DisplayObjectContainer {
  var enableBody: Boolean = js.native
  var physicsBodyType: Int = js.native

  def getFirstAlive(): js.UndefOr[T] = js.native

  def getFirstAlive(createIfNull: Boolean = false, x: Double, y: Double, key: String = null, frame: String | Int = null): js.UndefOr[T] = js.native

  def removeAll(destroy: Boolean = false, silent: Boolean = false): Unit = js.native

  def countLiving(): Int = js.native

  def getFirstExists(exists: Boolean): js.UndefOr[T] = js.native

  def forEach(callback: js.Function1[T, Any], callbackContext: Any = this, checkExists: Boolean = false): Unit = js.native

  def forEach(callback: js.Function1[T, Any], callbackContext: Any, checkExists: Boolean, args: Any*): Unit = js.native

  def forEachAlive(callback: js.Function1[T, Any], callbackContext: Any = this): Unit = js.native

  def forEachAlive(callback: js.Function1[T, Any], callbackContext: Any, args: Any*): Unit = js.native

  def create(x: Double, y: Double, key: Any = null, frame: Any = null, exists: Boolean = true): T = js.native

  def setAll(key: String, value: Any, checkAlive: Boolean = false, checkVisible: Boolean = false, operation: Int = 0, force: Boolean = false): Unit = js.native

  def createMultiple(quantity: Int, key: String, frame: Any = null, exists: Boolean = false): Unit = js.native

}
