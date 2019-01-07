package com.edwardh.games.jshooter.test;

import com.edwardh.games.jshooter.*;
import com.edwardh.games.jshooter.utility.*;
import com.edwardh.games.jshooter.entity.*;
public class StarTest extends JShooterTest {
public StarTest(String name) {
	super(name);
}
public void testMovement() throws Exception {
	game.resumeUpdates();
	assertEquals(20, game.getStars().size());
	Star star = (Star) game.getStars().elementAt(0);
	int starX = star.getXPos();
	int initialStarY = star.getYPos();
	updateGame();
	assertTrue(star.getYPos() > initialStarY);
	assertEquals(starX, star.getXPos());
	game.resumeUpdates();
	while (star.isInBounds())
		Thread.sleep(100);
	Thread.sleep(100);
	assertTrue(!game.getStars().contains(star));
}
}
