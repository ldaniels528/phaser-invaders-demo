Scala.js Invaders
=================

This project is a port of [scalajs-invaders](https://github.com/adrobisch/scalajs-invaders) to the 
[ScalaJs.io](https://github.com/ldaniels528/scalajs.io) framework, which is in turn a 
[Scala.js](http://scala-js.org) port of the [invaders example](http://phaser.io/examples/v2/games/invaders) from the 
[Phaser.js](http://phaser.io/) documentation. 

### Build Requirements

* [SBT 0.13.15](http://www.scala-sbt.org/download.html)

### Running the application

If you haven't done so, you'll need to install the Node modules and Bower components.
*NOTE*: You only need to do this once.

```bash
me@mypc:\scalajs-invaders> npm install
me@mypc:\scalajs-invaders> bower install
```

To run the server application, just type:

```bash
me@mypc:\scalajs-invaders> sbt clean run
```
 
Finally, open a browser to [http://localhost:1337]([http://localhost:1337) 
 
<img src="https://github.com/ldaniels528/scalajs-invaders/blob/master/Invaders.png">