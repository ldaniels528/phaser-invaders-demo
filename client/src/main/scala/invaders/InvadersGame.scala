package invaders

import io.scalajs.dom.html.phaser._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportAll

/**
  * Invaders Game State Controller
  * @param game the given [[Game game]] instance
  * @author lawrence.daniels@gmail.com
  */
@JSExportAll
class InvadersGame(val game: Game) extends GameState {
  var player: Sprite = _
  var aliens: Group[Sprite] = _
  var bullets: Group[Sprite] = _
  var bulletTime: Double = _
  var cursors: CursorKeys = _
  var fireButton: Key = _
  var explosions: Group[Sprite] = _
  var starField: TileSprite = _
  var score: Int = _
  val scoreString = "Score : "
  var scoreText: Text = _
  var lives: Group[Sprite] = _
  var enemyBullets: Group[Sprite] = _
  var firingTimer: Double = _
  var stateText: Text = _

  override def preload() {
    game.load.image("bullet", "assets/bullet.png")
    game.load.image("enemyBullet", "assets/enemy-bullet.png")
    game.load.spritesheet("invader", "assets/invader32x32x4.png", 32, 32)
    game.load.image("ship", "assets/player.png")
    game.load.spritesheet("kaboom", "assets/explode.png", 128, 128)
    game.load.image("starField", "assets/starField.png")
    game.load.image("background", "assets/background2.png")
  }

  override def create() {
    game.physics.startSystem(Physics.ARCADE)

    //  The scrolling star field background
    starField = game.add.tileSprite(0, 0, 800, 600, "starField")

    //  Our bullet group
    bullets = game.add.group()
    bullets.enableBody = true
    bullets.physicsBodyType = Physics.ARCADE
    bullets.createMultiple(30, "bullet")
    bullets.setAll("anchor.x", 0.5)
    bullets.setAll("anchor.y", 1)
    bullets.setAll("outOfBoundsKill", true)
    bullets.setAll("checkWorldBounds", true)

    // The enemy's bullets
    enemyBullets = game.add.group()
    enemyBullets.enableBody = true
    enemyBullets.physicsBodyType = Physics.ARCADE
    enemyBullets.createMultiple(30, "enemyBullet")
    enemyBullets.setAll("anchor.x", 0.5)
    enemyBullets.setAll("anchor.y", 1)
    enemyBullets.setAll("outOfBoundsKill", true)
    enemyBullets.setAll("checkWorldBounds", true)

    //  The hero!
    player = game.add.sprite(400, 500, "ship")
    player.anchor.setTo(0.5, 0.5)
    game.physics.enable(player, Physics.ARCADE)

    //  The baddies!
    aliens = game.add.group()
    aliens.enableBody = true
    aliens.physicsBodyType = Physics.ARCADE

    createAliens()

    //  The score
    scoreText = game.add.text(10, 10, scoreString + score, js.Dictionary("font" -> "34px Arial", "fill" -> "#fff"))

    //  Lives
    lives = game.add.group()
    game.add.text(game.world.width - 100, 10, "Lives : ", js.Dictionary("font" -> "34px Arial", "fill" -> "#fff"))

    //  Text
    stateText = game.add.text(game.world.centerX, game.world.centerY, " ", js.Dictionary("font" -> "84px Arial", "fill" -> "#fff"))
    stateText.anchor.setTo(0.5, 0.5)
    stateText.visible = false

    for (n <- 0 to 2) {
      val ship = lives.create(game.world.width - 100 + (30 * n), 60, "ship")
      ship.anchor.setTo(0.5, 0.5)
      ship.angle = 90
      ship.alpha = 0.4
    }

    //  An explosion pool
    explosions = game.add.group()
    explosions.createMultiple(30, "kaboom")
    explosions.forEach(setupInvader)

    //  And some controls to play the game with
    cursors = game.input.keyboard.createCursorKeys()
    fireButton = game.input.keyboard.addKey(Keyboard.SPACEBAR)
  }

  def createAliens() {
    for {
      y <- 0 to 3
      x <- 0 to 9
    } {
      val alien = aliens.create(x * 48, y * 50, "invader")
      alien.anchor.setTo(0.5, 0.5)
      alien.animations.add("fly", js.Array(0, 1, 2, 3), 20, true)
      alien.play("fly")
      // alien.body.moves = false
    }

    aliens.x = 100
    aliens.y = 50

    //  All this does is basically start the invaders moving. Notice we're moving the Group they belong to, rather than the invaders directly.
    val tween = game.add.tween(aliens).to(
      properties = js.Dictionary("x" -> 200),
      duration = 2000,
      ease = Easing.Linear.None,
      autoStart = true, delay = 0, repeat = 1000, yoyo = true
    )

    //  When the tween loops it calls descend
    tween.onLoop.add(descend)
  }

  def descend = { _: Any =>
    aliens.y += 10
  }

  def setupInvader(invader: Sprite) {
    invader.anchor.x = 0.5
    invader.anchor.y = 0.5
    invader.animations.add("kaboom")
  }

  override def update() {
    //  Scroll the background
    starField.tilePosition.y += 2

    if (player.alive) {
      //  Reset the player, then check for movement keys
      player.body.velocity.setTo(0, 0)

      if (cursors.left.isDown) player.body.velocity.x = -200
      else if (cursors.right.isDown) player.body.velocity.x = 200

      // is the player firing?
      if (fireButton.isDown) fireBullet()

      // should the enemy fire?
      if (game.time.now > firingTimer) enemyFires()

      //  Run collision
      game.physics.arcade.overlap(bullets, aliens, collisionHandler, processCallback = null, callbackContext = this)
      game.physics.arcade.overlap(enemyBullets, player, enemyHitsPlayer, processCallback = null, callbackContext = this)
    }
  }

  def enemyHitsPlayer(player: Sprite, bullet: Sprite) {
    // kill the bullet
    bullet.kill()

    // kill the enemy
    lives.getFirstAlive() foreach (_.kill())

    // and create an explosion
    explosions.getFirstExists(false) foreach { explosion =>
      explosion.reset(player.body.x, player.body.y)
      explosion.play("kaboom", 30, loop = false, killOnComplete = true)
    }

    // When the player dies
    if (lives.countLiving() < 1) {
      player.kill()
      enemyBullets.foreach(_.kill())

      stateText.text = " GAME OVER \n Click to restart"
      stateText.visible = true

      // the "click to restart" handler
      game.input.onTap.addOnce(restart)
    }
  }

  def collisionHandler(bullet: Sprite, alien: Sprite) {

    //  When a bullet hits an alien we kill them both
    bullet.kill()
    alien.kill()

    //  Increase the score
    score += 20
    scoreText.text = scoreString + score

    //  And create an explosion :)
    explosions.getFirstExists(false) foreach { explosion =>
      explosion.reset(alien.body.x, alien.body.y)
      explosion.play("kaboom", 30, loop = false, killOnComplete = true)
    }

    if (aliens.countLiving() == 0) {
      score += 1000
      scoreText.text = scoreString + score

      enemyBullets.foreach(_.kill())

      stateText.text = " You Won, \n Click to restart"
      stateText.visible = true

      game.input.onTap.addOnce(restart)
    }
  }

  def restart(context: Any) {
    // A new level starts
    // resets the life count
    lives.foreach(_.revive())

    // and brings the aliens back from the dead :)
    aliens.removeAll()
    createAliens()

    // revives the player
    player.revive()

    // hides the text
    stateText.visible = false
  }

  def fireBullet() {
    //  To avoid them being allowed to fire too fast we set a time limit
    if (game.time.now > bulletTime) {
      //  Grab the first bullet we can from the pool
      bullets.getFirstExists(false) foreach { bulletSprite =>
        //  And fire it
        bulletSprite.reset(player.x, player.y + 8)
        bulletSprite.body.velocity.y = -400
        bulletTime = game.time.now + 200
      }
    }
  }

  def enemyFires() {
    // grab the first bullet we can from the pool
    enemyBullets.getFirstExists(false) foreach { enemyBullet =>

      val livingEnemies = aliens.findAlive()
      if (livingEnemies.nonEmpty) {
        // randomly select one of them
        val random = game.rnd.integerInRange(0, livingEnemies.length - 1)
        val shooter = livingEnemies(random)

        // And fire the bullet from this enemy
        enemyBullet.reset(shooter.body.x, shooter.body.y)
        game.physics.arcade.moveToObject(enemyBullet, player, 120)
        firingTimer = game.time.now + 2000
      }
    }
  }

  override def render(): Unit = {}

}
