package com.edwardh.games.jshooter.test;

import java.util.*;
import com.edwardh.games.jshooter.*;
import com.edwardh.games.jshooter.utility.*;
import com.edwardh.games.jshooter.entity.*;
public class CrabAlienTest extends JShooterTest {
	private CrabAlienFormation formation;
	private int crabAlienWidth;
public CrabAlienTest(String name) {
	super(name);
}
public static void assertEqualsWithMarginOfError(int value1, int value2) throws Exception {
	assertEquals((double) value1, (double) value2, 1d);
}
public void setUp() throws Exception {
	super.setUp();
	gameEventHandler.crabAliensAppear();
	formation = (CrabAlienFormation) game.getAlienFormations().elementAt(1);
	crabAlienWidth = formation.alien(0).getWidth();
}
public void testFormationInitialPositionAndDirection() throws Exception {
	assertEquals(formation.getRadius(), formation.getXPos());
	assertEquals(formation.getRadius(), formation.getYPos());
	assertEquals(Alien.SOUTHEAST, formation.getDirection());
}
public void testFormationMovement() throws Exception {
	formation.update(1);
	assertEquals(formation.getRadius() + formation.getSpeed(), formation.getXPos());
	assertEquals(formation.getRadius() + formation.getSpeed(), formation.getYPos());
	//
	while (formation.getXPos() < formation.getRightmostPosition() / 2)
		formation.update(1);
	formation.update(1);
	assertEquals(Alien.NORTHEAST, formation.getDirection());
	//
	while (formation.getXPos() < formation.getRightmostPosition())
		formation.update(1);
	formation.update(1);
	assertEquals(Alien.SOUTHWEST, formation.getDirection());
	//
	while (formation.getXPos() > formation.getRightmostPosition() / 2)
		formation.update(1);
	formation.update(1);
	assertEquals(Alien.NORTHWEST, formation.getDirection());
	//
	while (formation.getXPos() > formation.getRadius())
		formation.update(1);
	formation.update(1);
	assertEquals(Alien.SOUTHEAST, formation.getDirection());
}
public void testFormationRightmostPosition() throws Exception {
	int expectedRightmostPosition = 
		game.getRightBoundary() - 
		formation.getRadius() - 
		formation.alien(0).getWidth();
	assertEquals(expectedRightmostPosition, formation.getRightmostPosition());
}
public void testFormationSize() throws Exception {
	assertEquals(3, formation.size());
}
public void testFormationSpeed() throws Exception {
	assertEquals(4, formation.getSpeed());
}
public void testIndividualAlienPositions() throws Exception {
	assertEquals(0d, formation.getAngleOffset(), 0d);
	//	
	Alien alien1 = formation.alien(0);
	int expectedXPos = formation.getXPos() + formation.getRadius();
	assertEqualsWithMarginOfError(expectedXPos, alien1.getXPos());
	int expectedYPos = formation.getYPos();
	assertEqualsWithMarginOfError(expectedYPos, alien1.getYPos());
	//
	Alien alien2 = formation.alien(1);
	expectedXPos = formation.getXPos() + (int) (-.5* formation.getRadius());
	assertEqualsWithMarginOfError(expectedXPos, alien2.getXPos());
	expectedYPos = formation.getYPos() + (int) ((Math.sqrt(3) / 2) * formation.getRadius());
	assertEqualsWithMarginOfError(expectedYPos, alien2.getYPos());
	//
	Alien alien3 = formation.alien(2);
	expectedXPos = formation.getXPos() + (int) (-.5* formation.getRadius());
	assertEqualsWithMarginOfError(expectedXPos, alien3.getXPos());
	expectedYPos = formation.getYPos() + (int) (- (Math.sqrt(3) / 2) * formation.getRadius());
	assertEqualsWithMarginOfError(expectedYPos, alien3.getYPos());
	//
	for (int i = 0; i < 3; i++)
		formation.update(1);
	assertEquals(3 * 2 * Math.PI / CrabAlienFormation.ROTATION_TICKS, formation.getAngleOffset(), 0d);
}
}
