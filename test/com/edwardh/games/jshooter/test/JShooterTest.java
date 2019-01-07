package com.edwardh.games.jshooter.test;

import java.net.*;
import java.util.*;
import java.applet.*;
import java.awt.*;
import com.edwardh.games.jshooter.*;
import com.edwardh.games.jshooter.utility.*;
import com.edwardh.games.jshooter.messages.*;
import com.edwardh.games.jshooter.entity.*;
import junit.framework.*;
public abstract class JShooterTest extends TestCase {
	protected JShooter applet;
	protected JShooterGame game;
	protected GameEventHandler gameEventHandler;
	private GameUpdater gameUpdater;
public JShooterTest(String name) {
	super(name);
}
protected void finishLevel() throws Exception {
	game.getAliens().removeAllElements();
	gameEventHandler.nextLevel();
	waitForMessageToEnd();
}
protected void removeAllAliens() {
	Enumeration enum = game.getAlienFormations().elements();
	while (enum.hasMoreElements()) {
		AlienFormation formation = (AlienFormation) enum.nextElement();
		formation.removeAllAliens();
	}
}
protected PackAlien removeAllAliensExceptOnePackAlienAndReturnIt() {
	PackAlien result = null;
	Enumeration enum = game.getAlienFormations().elements();
	while (enum.hasMoreElements()) {
		AlienFormation formation = (AlienFormation) enum.nextElement();
		if (result == null && formation instanceof PackAlienFormation) {
			Vector aliens = formation.getAliens();
			result = (PackAlien) formation.alien(0);
			int alienCount = aliens.size();
			for (int i = 1; i < alienCount; i++)
				formation.removeAlien(formation.alien(1));
		} else
			formation.removeAllAliens();
	}
	return result;
}
public void setUp() throws Exception {
	applet = JShooter.createAndSetUpApplet(600, 600, true);
	game = applet.getGame();
	gameEventHandler = game.getEventHandler();
	gameUpdater = game.getUpdater();
	PackAlien.canGoKamikazee = false;
	Alien.canDropBombs = false;
	AbstractMessageController.TIME_MESSAGES_LAST = 25;
	game.getMessageController().mousePressed();
	updateGame();
	waitForMessageToEnd();
	if (!shouldHaveShields())
		game.getShields().removeAllElements();
	gameUpdater.suspendPowerUps();
	gameUpdater.suspendAsteroids();
	gameUpdater.suspendDynamicAliens();
}
protected void shootAlien(Alien alien) throws Exception {
	game.getRocket().shootPrimaryGun();
	Bullet bullet = (Bullet) game.getBullets().elementAt(0);
	bullet.setXPos(alien.getXPos());
	bullet.setYPos(alien.getYPos());
	//
	alien.suspendMovement();
	bullet.suspendMovement();
	updateGame();
	if (alien.isDead())
		assertTrue(!game.getAliens().contains(alien));
	assertTrue(!game.getBullets().contains(bullet));
}
protected boolean shouldHaveShields() {
	return false;
}
protected void suspendAllAlienMovement() {
	Enumeration enum = game.getAliens().elements();
	while (enum.hasMoreElements()) {
		((Alien) enum.nextElement()).suspendMovement();
	}
}
public void tearDown() throws Exception {
	applet.getNonAppletFrame().dispose();
	applet.stop();
	applet.destroy();
	applet = null;
	game = null;
}
protected void updateGame() {
	gameUpdater.update();
}
protected void waitForMessageToEnd() throws Exception {
	Thread.sleep(AbstractMessageController.TIME_MESSAGES_LAST + 1);
	updateGame();
}
}
