package com.edwardh.games.jshooter.test;

import com.edwardh.games.jshooter.messages.*;
import com.edwardh.games.jshooter.*;
import com.edwardh.games.jshooter.utility.*;
import com.edwardh.games.jshooter.entity.*;
import java.util.*;
public class GameTest extends JShooterTest {
public GameTest(String name) {
	super(name);
}
protected boolean shouldHaveShields() {
	return true;
}
public void testGameOver() throws Exception {
	gameEventHandler.rocketDied();
	waitForMessageToEnd();
	gameEventHandler.rocketDied();
	waitForMessageToEnd();
	gameEventHandler.rocketDied();
	assertTrue(game.getMessageController() instanceof RocketDiedMessageController);
	waitForMessageToEnd();
	assertTrue(game.getMessageController() instanceof HighScoreMessageController);
	game.getMessageController().mousePressed();
	updateGame();
	assertEquals(0, game.getLevel());
	assertEquals(0, game.getScore());
	assertEquals(3, game.getRocket().getLives());
	assertTrue(game.getMessageController() instanceof LevelMessageController);
}
public void testInitialConditions() {
	//boundaries
	assertEquals(600, game.getRightBoundary());
	assertEquals(600, game.getBottomBoundary());
	//rocket
	Rocket rocket = game.getRocket();
	assertEquals(3, rocket.getLives());
	assertEquals(game.getRightBoundary() / 2, rocket.getXPos());
	assertEquals(game.getBottomBoundary() - Rocket.ROCKET_Y_OFFSET, rocket.getYPos());
	//aliens
	Vector aliens = game.getAliens();
	assertEquals(10, aliens.size());
	//score
	assertEquals(0, game.getScore());
	//bullets
	assertTrue(game.getBullets().isEmpty());
	//shields
	Vector shields = game.getShields();
	assertEquals(15, shields.size());
	Enumeration shieldsEnum = shields.elements();
	Shield shield;
	int yPos = rocket.getYPos() - 10 - Shield.HEIGHT;
	int xPosOfFirstCenterShield = game.getRightBoundary() / 2 - (2 + 1 / 2) * Shield.WIDTH;
	for (int i = 0; i < 5; i++) {
		shield = (Shield) shieldsEnum.nextElement();
		assertEquals(yPos, shield.getYPos());
		assertEquals(i * Shield.WIDTH, shield.getXPos());
	}
	for (int i = 0; i < 5; i++) {
		shield = (Shield) shieldsEnum.nextElement();
		assertEquals(yPos, shield.getYPos());
		assertEquals(xPosOfFirstCenterShield + i * Shield.WIDTH, shield.getXPos());
	}
	for (int i = 5; i > 0; i--) {
		shield = (Shield) shieldsEnum.nextElement();
		assertEquals(yPos, shield.getYPos());
		assertEquals(game.getRightBoundary() - i * Shield.WIDTH, shield.getXPos());
	}
}
public void testLevels() throws Exception {
	game.resumeUpdates();
	assertEquals(1, game.getLevel());
	long timeFirstLevelStarted = game.getTimeLevelStarted();
	long time1 = game.getLevelTime();
	assertTrue(time1 >= 0);
	Thread.sleep(100);
	assertTrue(game.getLevelTime() > time1);
	Vector aliens = game.getAliens();
	((Alien) aliens.elementAt(0)).dropBomb();
	assertTrue(game.getBombs().size() >= 1);
	game.removeAllAliens();
	gameEventHandler.nextLevel();
	updateGame();
	assertTrue(game.getBombs().isEmpty());
	assertTrue(game.isShowingMessage());
	assertTrue(game.getUpdater().powerUpsSuspended());
	assertEquals(1, game.getLevel());
	waitForMessageToEnd();
	assertTrue(!game.isShowingMessage());
	assertTrue(!game.getUpdater().powerUpsSuspended());
	assertEquals(2, game.getLevel());
	assertTrue(game.getTimeLevelStarted() > timeFirstLevelStarted);
}
public void testTimeBonus() throws Exception {
	assertEquals((30 - 10) * 100, JShooterUtility.levelBonusForTime(10 * 1000));
	assertEquals(0, JShooterUtility.levelBonusForTime(31 * 1000));
}
public void testTwoMessageControllers() throws Exception {
	PackAlien alien = removeAllAliensExceptOnePackAlienAndReturnIt();
	alien.goKamikazee();
	Rocket rocket = game.getRocket();
	alien.setXPos(rocket.getXPos() + 1);
	alien.setYPos(rocket.getYPos() + 1-alien.getHeight());
	//
	assertEquals(3, rocket.getLives());
	assertEquals(1, game.getLevel());
	assertNull(game.getMessageController());
	//
	updateGame();
	assertEquals(3, rocket.getLives());
	assertEquals(1, game.getLevel());
	assertTrue(game.getMessageController() instanceof RocketDiedMessageController);
	assertTrue(!game.getAliens().contains(alien));
	waitForMessageToEnd();
	assertEquals(2, rocket.getLives());
	assertEquals(1, game.getLevel());
	//
	assertEquals(LevelMessageController.class, game.getMessageController().getClass());
	waitForMessageToEnd();
	assertEquals(2, rocket.getLives());
	assertEquals(2, game.getLevel());
	assertNull(game.getMessageController());
}
}
