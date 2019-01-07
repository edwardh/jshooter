package com.edwardh.games.jshooter.test;

import com.edwardh.games.jshooter.*;
import com.edwardh.games.jshooter.utility.*;
import com.edwardh.games.jshooter.entity.*;
public class RocketTest extends JShooterTest {
public RocketTest(String name) {
	super(name);
}
public void testEnergy() throws Exception {
	Rocket rocket = game.getRocket();
	rocket.energyGenerationSuspended = true;
	assertEquals(10, rocket.getEnergy());
	assertTrue(rocket.canFirePrimaryGun());
	//
	rocket.shootPrimaryGun();
	assertEquals(9, rocket.getEnergy());
	assertTrue(rocket.canFirePrimaryGun());
	//
	rocket.shootPrimaryGun();
	assertEquals(8, rocket.getEnergy());
	assertTrue(rocket.canFirePrimaryGun());
	//
	rocket.shootPrimaryGun();
	assertEquals(7, rocket.getEnergy());
	assertTrue(rocket.canFirePrimaryGun());
	//
	rocket.shootPrimaryGun();
	assertEquals(6, rocket.getEnergy());
	assertTrue(rocket.canFirePrimaryGun());
	//
	rocket.shootPrimaryGun();
	assertEquals(5, rocket.getEnergy());
	assertTrue(rocket.canFirePrimaryGun());
	//
	rocket.shootPrimaryGun();
	assertEquals(4, rocket.getEnergy());
	assertTrue(rocket.canFirePrimaryGun());
	//
	rocket.shootPrimaryGun();
	assertEquals(3, rocket.getEnergy());
	assertTrue(rocket.canFirePrimaryGun());
	//
	rocket.shootPrimaryGun();
	assertEquals(2, rocket.getEnergy());
	assertTrue(rocket.canFirePrimaryGun());
	//
	rocket.shootPrimaryGun();
	assertEquals(1, rocket.getEnergy());
	assertTrue(rocket.canFirePrimaryGun());
	//
	rocket.shootPrimaryGun();
	assertEquals(0, rocket.getEnergy());
	assertTrue(!rocket.canFirePrimaryGun());
	//
	rocket.energyGenerationSuspended = false;
	Thread.sleep(1000);
	assertEquals(1, rocket.getEnergy());
}
public void testGetsBombed() throws Exception {
	((Alien) game.getAliens().elementAt(0)).dropBomb();
	Bomb bomb = (Bomb) game.getBombs().elementAt(0);
	Thread.sleep(100);
	//
	Rocket rocket = game.getRocket();
	bomb.setXPos(rocket.getXPos() + 1);
	bomb.setYPos(rocket.getYPos() + 1);
	assertEquals(3, rocket.getLives());
	updateGame();
	assertTrue(!game.getBombs().contains(bomb));
	waitForMessageToEnd();
	assertEquals(2, rocket.getLives());
}
public void testInitialPosition() {
	Rocket rocket = game.getRocket();
	assertEquals(game.getBottomBoundary() - Rocket.ROCKET_Y_OFFSET, rocket.getYPos());
	assertEquals(game.getRightBoundary() / 2, rocket.getXPos());
}
}
