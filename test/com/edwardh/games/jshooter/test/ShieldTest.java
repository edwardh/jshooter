package com.edwardh.games.jshooter.test;

import com.edwardh.games.jshooter.entity.*;
public class ShieldTest extends JShooterTest {
public ShieldTest(String name) {
	super(name);
}
protected boolean shouldHaveShields() {
	return true;
}
public void testGetsShot() throws Exception {
	Shield shield = (Shield) game.getShields().elementAt(0);
	Rocket rocket = game.getRocket();
	rocket.setXPos(shield.getXPos() + 5);
	rocket.shootPrimaryGun();
	Thread.sleep(500);
	updateGame();
	assertTrue(!game.getShields().contains(shield));
}
}
