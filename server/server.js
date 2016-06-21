/**
 * Invaders ScalaJS Bootstrap
 * @author: lawrence.daniels@gmail.com
 */
(function () {
    require("./target/scala-2.11/invaders-server-fastopt.js");
    const facade = invaders.InvadersServerApp();
    facade.startServer({
        "__dirname": __dirname,
        "__filename": __filename,
        "exports": exports,
        "module": module,
        "require": require
    });
})();