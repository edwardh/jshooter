package com.edwardh.games.jshooter.test;

import com.edwardh.games.jshooter.*;
import com.edwardh.games.jshooter.utility.*;
import com.edwardh.games.jshooter.entity.*;
public class PowerUpTest extends JShooterTest {
public PowerUpTest(String name) {
	super(name);
}
private void catchLifePowerUp() {
	assertTrue(game.getPowerUps().isEmpty());
	gameEventHandler.lifePowerUpCreated();
	assertEquals(1, game.getPowerUps().size());
	LifePowerUp powerUp = (LifePowerUp) game.getPowerUps().elementAt(0);
	updateGame();
	powerUp.setYPos(game.getRocket().getYPos() + 1);
	powerUp.setXPos(game.getRocket().getXPos() + 1);
	updateGame();
	assertTrue(game.getPowerUps().isEmpty());
}
public void testLifePowerUp() throws Exception {
	assertEquals(3, game.getRocket().getLives());
	catchLifePowerUp();
	assertEquals(4, game.getRocket().getLives());
	catchLifePowerUp();
	assertEquals(5, game.getRocket().getLives());
	catchLifePowerUp();
	assertEquals(5, game.getRocket().getLives());
}
public void testPowerUpGetsShot() throws Exception {
	gameEventHandler.energyPowerUpCreated();
	PowerUp powerUp = (PowerUp) game.getPowerUps().elementAt(0);
	game.getRocket().shootPrimaryGun();
	Bullet bullet = (Bullet) game.getBullets().elementAt(0);
	int collisionYPos = 200;
	int collisionXPos = 200;
	//
	bullet.setXPos(collisionXPos);
	bullet.setYPos(collisionYPos + Math.abs(bullet.getDy()));
	powerUp.setXPos(collisionXPos);
	powerUp.setYPos(collisionYPos - powerUp.getDy());
	//
	updateGame();
	assertTrue(game.getPowerUps().isEmpty());
	assertTrue(game.getBullets().isEmpty());
	assertEquals(1, game.getExplosions().size());
	Explosion explosion = (Explosion) game.getExplosions().elementAt(0);
	assertEquals(collisionXPos, explosion.getXPos());
	assertEquals(collisionYPos, explosion.getYPos());
}
public void testPowerUpMovement() throws Exception {
	game.getRocket().setXPos(10000); //so the powerUp misses
	assertTrue(game.getPowerUps().isEmpty());
	gameEventHandler.energyPowerUpCreated();
	PowerUp powerUp = (PowerUp) game.getPowerUps().elementAt(0);
	assertEquals(1 - powerUp.getHeight(), powerUp.getYPos());
	int xPos = powerUp.getXPos();
	updateGame();
	game.resumeUpdates();
	while (powerUp.isInBounds()) {
		assertEquals(xPos, powerUp.getXPos());
		assertTrue(powerUp.getYPos() > 1 - powerUp.getHeight());
	}
	assertTrue(game.getPowerUps().isEmpty());
}
}
