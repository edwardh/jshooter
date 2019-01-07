package com.edwardh.games.jshooter.test;

import com.edwardh.games.jshooter.*;
import com.edwardh.games.jshooter.utility.*;
import com.edwardh.games.jshooter.entity.*;
public class BulletTest extends JShooterTest {
public BulletTest(String name) {
	super(name);
}
public void testDoubleBulletMovement() throws Exception {
	removeAllAliens();
	assertTrue(game.getBullets().isEmpty());
	Rocket rocket = game.getRocket();
	rocket.setXPos(10);
	rocket.shootSecondaryGun();
	assertEquals(2, game.getBullets().size());
	Bullet bullet1 = (Bullet) game.getBullets().elementAt(0);
	Bullet bullet2 = (Bullet) game.getBullets().elementAt(1);
	//
	assertEquals(11, bullet1.getXPos());
	assertEquals(10 + rocket.getWidth() - 1, bullet2.getXPos());
	int initialBullet1YPos = bullet1.getYPos();
	int initialBullet2YPos = bullet2.getYPos();
	assertEquals(initialBullet1YPos, initialBullet2YPos);
	updateGame();
	int laterBullet1YPos = bullet1.getYPos();
	int laterBullet2YPos = bullet2.getYPos();
	assertEquals(laterBullet1YPos, laterBullet2YPos);
	assertTrue(initialBullet1YPos > laterBullet1YPos);
	//
	game.resumeUpdates();
	while (bullet1.isInBounds() || bullet2.isInBounds())
		Thread.sleep(100);
	assertTrue(game.getBullets().isEmpty());
}
public void testSingleBulletMovement() throws Exception {
	removeAllAliens();
	assertTrue(game.getBullets().isEmpty());
	Rocket rocket = game.getRocket();
	rocket.setXPos(10);
	rocket.shootPrimaryGun();
	assertEquals(1, game.getBullets().size());
	Bullet bullet = (Bullet) game.getBullets().elementAt(0);
	assertEquals(10 + game.getRocket().getWidth() / 2, bullet.getXPos());
	int firstBulletYPos = bullet.getYPos();
	updateGame();
	int secondBulletYPos = bullet.getYPos();
	assertTrue(secondBulletYPos < firstBulletYPos);
	//
	game.resumeUpdates();
	while (bullet.isInBounds())
		Thread.sleep(100);
	assertTrue(game.getBullets().isEmpty());
}
}
