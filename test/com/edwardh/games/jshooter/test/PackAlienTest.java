package com.edwardh.games.jshooter.test;

import java.util.*;
import com.edwardh.games.jshooter.*;
import com.edwardh.games.jshooter.utility.*;
import com.edwardh.games.jshooter.entity.*;
public class PackAlienTest extends JShooterTest {
public PackAlienTest(String name) {
	super(name);
}
public void testAlienDropsBomb() throws Exception {
	game.resumeUpdates();
	assertTrue(game.getBombs().isEmpty());
	Alien alien = (Alien) game.getAliens().elementAt(0);
	int alienX = alien.getXPos();
	int alienY = alien.getYPos();
	alien.dropBomb();
	updateGame();
	assertEquals(1, game.getBombs().size());
	Bomb bomb = (Bomb) game.getBombs().elementAt(0);
	assertEquals(alienX + (alien.getWidth() / 2), bomb.getXPos());
	game.getRocket().setXPos(bomb.getXPos() + 200);
	int initialBombYPos = alienY + alien.getHeight() + bomb.getDy();
	assertEquals(initialBombYPos, bomb.getYPos());
	updateGame();
	int laterBombYPos = bomb.getYPos();
	assertTrue(laterBombYPos > initialBombYPos);
	assertEquals(alienX + (alien.getWidth() / 2), bomb.getXPos());
	//
	while (bomb.isInBounds())
		Thread.sleep(100);
	assertTrue(!game.getBombs().contains(bomb));
}
public void testAlienFormationInfluenceOnAliens() throws Exception {
	PackAlienFormation formation = (PackAlienFormation) game.getAlienFormations().elementAt(0);
	assertEquals(10, formation.size());
	int[] xPositions = new int[10];
	for (int i = 0; i < 10; i++)
		xPositions[i] = formation.alien(i).getXPos();
	formation.setDirection(Alien.EAST);
	int expectedRightMovement = 2;
	formation.update(1);
	for (int i = 0; i < 10; i++) {
		PackAlien alien = (PackAlien) formation.alien(i);
		assertEquals(xPositions[i] + expectedRightMovement, alien.getXPos());
	}
}
public void testAlienGetsShot() throws Exception {
	assertEquals(0, game.getScore());
	shootAlien((Alien) game.getAliens().elementAt(0));
	assertEquals(1000, game.getScore());
	PackAlien alien = (PackAlien) game.getAliens().elementAt(0);
	alien.goKamikazee();
	shootAlien(alien);
	assertEquals(2500, game.getScore());
}
public void testAlienMovementWhenKamikazee() throws Exception {
	PackAlien alien = (PackAlien) game.getAliens().elementAt(0);
	alien.setXPos(100);
	alien.setYPos(100);

	//move north
	alien.setDirection(Alien.NORTH);
	alien.performMove(1);
	assertEquals(100, alien.getXPos());
	assertEquals(97, alien.getYPos());
	//move south
	alien.setDirection(Alien.SOUTH);
	alien.performMove(1);
	assertEquals(100, alien.getXPos());
	assertEquals(100, alien.getYPos());
	//move east
	alien.setDirection(Alien.EAST);
	alien.performMove(2);
	assertEquals(104, alien.getXPos());
	assertEquals(100, alien.getYPos());
	//move west
	alien.setDirection(Alien.WEST);
	alien.performMove(2);
	assertEquals(100, alien.getXPos());
	assertEquals(100, alien.getYPos());
	//move northeast
	alien.setDirection(Alien.NORTHEAST);
	alien.performMove(3);
	assertEquals(103, alien.getXPos());
	assertEquals(97, alien.getYPos());
	//move southwest
	alien.setDirection(Alien.SOUTHWEST);
	alien.performMove(3);
	assertEquals(100, alien.getXPos());
	assertEquals(100, alien.getYPos());
	//move northwest
	alien.setDirection(Alien.NORTHWEST);
	alien.performMove(4);
	assertEquals(96, alien.getXPos());
	assertEquals(96, alien.getYPos());
	//move northeast
	alien.setDirection(Alien.SOUTHEAST);
	alien.performMove(4);
	assertEquals(100, alien.getXPos());
	assertEquals(100, alien.getYPos());
	//don't move
	alien.setDirection(Alien.STILL);
	alien.performMove(1);
	assertEquals(100, alien.getXPos());
	assertEquals(100, alien.getYPos());
}
public void testAlienRemainsWithinBoundaries() throws Exception {
	Alien alien = (Alien) game.getAliens().elementAt(0);
	alien.setXPos(-3);
	alien.setYPos(-3);
	updateGame();
	assertTrue(alien.getXPos() >= 0);
	assertTrue(alien.getYPos() >= 0);
	//
	alien.setXPos(game.getRightBoundary() - alien.getWidth() + 3);
	alien.setYPos(game.getBottomBoundary() + 3);
	updateGame();
	assertTrue(alien.getXPos() + alien.getWidth() <= game.getRightBoundary());
	assertTrue(alien.getYPos() + alien.getHeight() <= game.getBottomBoundary());
}
public void testKamikazeePackAlienCollidesWithRocket() throws Exception {
	PackAlien alien = (PackAlien) game.getAliens().elementAt(0);
	alien.goKamikazee();
	Rocket rocket = game.getRocket();
	alien.setXPos(rocket.getXPos() + 1);
	alien.setYPos(game.getBottomBoundary() - alien.getHeight() - 20);
	assertEquals(3, rocket.getLives());
	updateGame();
	assertTrue(!game.getAliens().contains(alien));
	waitForMessageToEnd();
	assertEquals(2, rocket.getLives());
}
public void testLastAlienDies() throws Exception {
	//dies by going kamikazee and hitting bottom
	PackAlien alien = removeAllAliensExceptOnePackAlienAndReturnIt();
	assertEquals(1, game.getLevel());
	alien.goKamikazee();
	alien.setYPos(game.getBottomBoundary());
	assertTrue(game.getAliens().contains(alien));
	updateGame();
	assertTrue(!game.getAliens().contains(alien));
	waitForMessageToEnd();
	assertEquals(2, game.getLevel());
}
public void testPackAlienFormationInitialPositions() throws Exception {
	PackAlienFormation formation = (PackAlienFormation) game.getAlienFormations().elementAt(0);
	assertEquals(10, formation.size());
	int columnWidth = (game.getRightBoundary() - 2 * PackAlienFormation.X_OFFSET) / 10;
	for (int i = 0; i < 10; i++)
		assertEquals(PackAlienFormation.X_OFFSET + i * columnWidth, formation.alien(i).getXPos());
	removeAllAliens();
	gameEventHandler.nextLevel();
	//
	waitForMessageToEnd();
	assertEquals(2, game.getLevel());
	//
	formation = (PackAlienFormation) game.getAlienFormations().elementAt(0);
	assertEquals(20, formation.size());
	for (int i = 0; i < 10; i++) {
		assertEquals(PackAlienFormation.X_OFFSET + i * columnWidth, formation.alien(i).getXPos());
		assertEquals(PackAlienFormation.Y_OFFSET, formation.alien(i).getYPos());
	}
	for (int i = 10; i < 20; i++) {
		assertEquals(PackAlienFormation.X_OFFSET + (i - 10) * columnWidth, formation.alien(i).getXPos());
		assertEquals(PackAlienFormation.Y_OFFSET + PackAlienFormation.ROW_SEPARATION, formation.alien(i).getYPos());
	}
}
public void testPackAlienFormationMovement() throws Exception {
	PackAlienFormation formation = (PackAlienFormation) game.getAlienFormations().elementAt(0);
	assertEquals(Alien.EAST, formation.getDirection());
	for (int i = 0; i < 16; i++)
		formation.update(1);
	assertEquals(Alien.SOUTH, formation.getDirection());
	formation.update(1);
	formation.update(1);
	formation.update(1);
	assertEquals(Alien.WEST, formation.getDirection());
}
public void testPackAlienGoesKamikazee() throws Exception {
	game.getRocket().setXPos(200);
	PackAlien alien = (PackAlien) game.getAliens().elementAt(0);
	alien.goKamikazee();
	int oldYPos = alien.getYPos();
	assertTrue(game.getExplosions().isEmpty());
	updateGame();
	int direction = alien.getDirectionWhenKamikazee();
	assertTrue(direction == Alien.SOUTH || direction == Alien.SOUTHEAST || direction == Alien.SOUTHWEST);
	assertTrue(alien.getYPos() > oldYPos);
	//
	alien.setXPos(5); //so it misses the rocket
	alien.setYPos(game.getBottomBoundary() - alien.getHeight() + 1);
	updateGame();
	assertEquals(1, game.getExplosions().size());
	Explosion explosion = (Explosion) game.getExplosions().elementAt(0);
	assertEquals(game.getBottomBoundary() - alien.getHeight(), explosion.getYPos());
}
public void testPackAlienHitsBoundaryWhenKamikazee() throws Exception {
	PackAlien alien = (PackAlien) game.getAliens().elementAt(0);
	alien.goKamikazee();
	//hits left boundary
	alien.setYPos(100);
	alien.setXPos(0);
	alien.setDirection(Alien.WEST);
	updateGame();
	assertEquals(0, alien.getXPos());
	assertEquals(Alien.EAST, alien.getDirectionWhenKamikazee());
	alien.setDirection(Alien.NORTHWEST);
	updateGame();
	assertEquals(0, alien.getXPos());
	assertEquals(Alien.NORTHEAST, alien.getDirectionWhenKamikazee());
	alien.setDirection(Alien.SOUTHWEST);
	updateGame();
	assertEquals(0, alien.getXPos());
	assertEquals(Alien.SOUTHEAST, alien.getDirectionWhenKamikazee());

	//hits top boundary
	alien.setYPos(0);
	alien.setXPos(100);
	alien.setDirection(Alien.NORTH);
	updateGame();
	assertEquals(0, alien.getYPos());
	assertEquals(Alien.SOUTH, alien.getDirectionWhenKamikazee());
	alien.setDirection(Alien.NORTHWEST);
	updateGame();
	assertEquals(0, alien.getYPos());
	assertEquals(Alien.SOUTHWEST, alien.getDirectionWhenKamikazee());
	alien.setDirection(Alien.NORTHEAST);
	updateGame();
	assertEquals(0, alien.getYPos());
	assertEquals(Alien.SOUTHEAST, alien.getDirectionWhenKamikazee());

	//hits right boundary
	alien.setYPos(100);
	int rightMostPosition = game.getRightBoundary() - alien.getWidth();
	alien.setXPos(rightMostPosition);
	alien.setDirection(Alien.EAST);
	updateGame();
	assertEquals(rightMostPosition, alien.getXPos());
	assertEquals(Alien.WEST, alien.getDirectionWhenKamikazee());
	alien.setDirection(Alien.NORTHEAST);
	updateGame();
	assertEquals(rightMostPosition, alien.getXPos());
	assertEquals(Alien.NORTHWEST, alien.getDirectionWhenKamikazee());
	alien.setDirection(Alien.SOUTHEAST);
	updateGame();
	assertEquals(rightMostPosition, alien.getXPos());
	assertEquals(Alien.SOUTHWEST, alien.getDirectionWhenKamikazee());
}
public void testPackAlienSpeed() throws Exception {
	PackAlien alien = (PackAlien) game.getAliens().elementAt(0);
	alien.goKamikazee();
	assertEquals(3, alien.getMovementIncrementWhenKamikazee(1));
	assertEquals(4, alien.getMovementIncrementWhenKamikazee(2));
	assertEquals(5, alien.getMovementIncrementWhenKamikazee(3));
}
}
