package com.edwardh.games.jshooter.test;

import java.util.*;
import com.edwardh.games.jshooter.entity.*;
public class AsteroidTest extends JShooterTest {
public AsteroidTest(String name) {
	super(name);
}
public void testCollision() {
	gameEventHandler.largeAsteroidCreated();
	gameEventHandler.largeAsteroidCreated();
	assertEquals(2, game.getAsteroids().size());
	Asteroid asteroid1 = (Asteroid) game.getAsteroids().elementAt(0);
	Asteroid asteroid2 = (Asteroid) game.getAsteroids().elementAt(1);
	asteroid1.setXPos(50);
	asteroid2.setXPos(50);
	asteroid1.setYPos(50);
	asteroid2.setYPos(50);
	updateGame();
	assertEquals(0, game.getAsteroids().size());
}
public void testGetsShot() {
	gameEventHandler.largeAsteroidCreated();
	assertEquals(1, game.getAsteroids().size());
	Asteroid asteroid = (Asteroid) game.getAsteroids().elementAt(0);
	asteroid.setXPos(50);
	asteroid.setYPos(200);
	Rocket rocket = game.getRocket();
	rocket.setXPos(51);
	rocket.shootPrimaryGun();
	game.resumeUpdates();
	Bullet bullet = (Bullet) game.getBullets().elementAt(0);
	do {
		updateGame();
		asteroid.setXPos(50);
		asteroid.setYPos(200);
	} while (game.getBullets().contains(bullet));
	Vector asteroids = game.getAsteroids();
	assertEquals(2, asteroids.size());
	assertEquals(SmallAsteroid.class, asteroids.elementAt(0).getClass());
	assertEquals(SmallAsteroid.class, asteroids.elementAt(0).getClass());
}
public void testMovement() throws Exception {
	gameEventHandler.largeAsteroidCreated();
	assertEquals(1, game.getAsteroids().size());
	Asteroid asteroid = (Asteroid) game.getAsteroids().elementAt(0);
	int asteroidDx = asteroid.getDx();
	int asteroidDy = asteroid.getDy();
	int asteroidX = asteroid.getXPos();
	int asteroidY = asteroid.getYPos();
	assertTrue(asteroidX >= 0);
	assertTrue(asteroidX <= game.getRightBoundary() - 20);
	assertEquals(0, asteroidY);
	updateGame();
	assertEquals(asteroidX + asteroidDx, asteroid.getXPos());
	assertEquals(asteroidY + asteroidDy, asteroid.getYPos());
	game.resumeUpdates();
	game.getRocket().setXPos(10000);
	while (asteroid.isInBounds())
		Thread.sleep(100);
	Thread.sleep(100);
	assertTrue(!game.getAsteroids().contains(asteroid));
}
}
