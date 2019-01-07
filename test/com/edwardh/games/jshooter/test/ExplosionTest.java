package com.edwardh.games.jshooter.test;

import com.edwardh.games.jshooter.*;
import com.edwardh.games.jshooter.utility.*;
import com.edwardh.games.jshooter.entity.*;
public class ExplosionTest extends JShooterTest {
public ExplosionTest(String name) {
	super(name);
}
public void testExplosion() throws Exception {
	Alien alien = (Alien) game.getAliens().elementAt(0);
	assertTrue(game.getExplosions().isEmpty());
	gameEventHandler.entityExplodes(alien);
	int alienXPos = alien.getXPos();
	int alienYPos = alien.getYPos();
	assertEquals(4, game.getExplosions().size());
	Explosion explosion = (Explosion) game.getExplosions().elementAt(0);
	assertEquals(alienXPos - 5, explosion.getXPos());
	assertEquals(alienYPos, explosion.getYPos());
	assertTrue(game.getExplosions().contains(explosion));
	game.resumeUpdates();
	Thread.sleep(Explosion.TIME_LASTS + 1);
	updateGame();
	assertEquals(alienXPos - 5, explosion.getXPos());
	assertEquals(alienYPos, explosion.getYPos());
	assertTrue(!game.getExplosions().contains(explosion));
}
}
