package com.edwardh.games.jshooter.test;

import com.edwardh.games.jshooter.entity.*;
public class TankAlienTest extends JShooterTest {
	private TankAlienFormation formation;
public TankAlienTest(String name) {
	super(name);
}
public void setUp() throws Exception {
	super.setUp();
	gameEventHandler.tankAlienAppears();
	formation = (TankAlienFormation) game.getAlienFormations().elementAt(1);
}
public void testTankAlienHits() throws Exception {
	TankAlien soleTankAlien = (TankAlien) formation.alien(0);
	shootAlien(soleTankAlien);
	assertTrue(!soleTankAlien.isDead());
	shootAlien(soleTankAlien);
	assertTrue(!soleTankAlien.isDead());
	shootAlien(soleTankAlien);
	assertTrue(soleTankAlien.isDead());
}
public void testTankAlienIsKamikazee() throws Exception {
	assertTrue(((TankAlien) formation.alien(0)).isKamikazee());
}
}
