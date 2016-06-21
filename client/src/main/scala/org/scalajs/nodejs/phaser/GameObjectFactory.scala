package org.scalajs.nodejs.phaser

import org.scalajs.nodejs.phaser.PIXI.DisplayObject

import scala.scalajs.js

@js.native
trait GameObjectFactory extends js.Object {

  def tween(aliens: DisplayObject): Tween = js.native

  def text(x: Double = 0, y: Double = 0, text: String = "", style: js.Dictionary[_] = null, group: Group[_] = null): Text = js.native

  def sprite(x: Int = 0, y: Int = 0, key: String = null, frame: Any = null, group: Group[_] = null): Sprite

  def group(parent: Any = null, name: String = null, addToStage: Boolean = false, enableBody: Boolean = false, physicsBodyType: Int = 0): Group[Sprite] = js.native

  def tileSprite(x: Int, y: Int, width: Int, height: Int, key: String, frame: Any = null, group: Group[_] = null): TileSprite = js.native
}
