package com.edwardh.games.jshooter.test;

import java.util.*;
import com.edwardh.games.jshooter.highscores.*;
import com.edwardh.games.jshooter.utility.*;
public class HighScoreTest extends JShooterTest {
	protected HighScores highScores;
	protected static final String TEST_HIGH_SCORE_FILE_NAME = "test/testhighscores.txt";
	protected static final String TEST_FULL_HIGH_SCORE_FILE_NAME = "test/testhighscoresfull.txt";
	protected static final String TEST_HIGH_SCORE_TEST_FILE_CONTENTS = "player1=1000~~player2=500~~player3=250";
	protected static final String TEST_FULL_HIGH_SCORE_TEST_FILE_CONTENTS = "player1=1000~~player2=500~~player3=250~~player4=125~~player5=100~~player6=50~~player7=25~~player8=10~~player9=5~~player10=2";
public HighScoreTest(String name) {
	super(name);
}
public void setUp() throws Exception {
	super.setUp();
	highScores = new HighScores(TEST_HIGH_SCORE_FILE_NAME);
	highScores.update();
}
public void tearDown() throws Exception {
	JShooterUtility.hitUrlAndGetResponse("http://www.edwardh.com/jshooter/test/restoretestfiles.cgi");
	super.tearDown();
}
public void testAddHighScore() throws Exception {
	highScores.addHighScore("testplayer", 400);
	assertEquals("player1=1000~~player2=500~~testplayer=400~~player3=250", highScores.testVariableDelimitedString);
}
public void testFullHighScores() throws Exception {
	HighScores fullHighScores = new HighScores(TEST_FULL_HIGH_SCORE_FILE_NAME);
	fullHighScores.update();
	fullHighScores.addHighScore("testplayer", 1);
	assertEquals(TEST_FULL_HIGH_SCORE_TEST_FILE_CONTENTS, fullHighScores.testVariableDelimitedString);
	fullHighScores.addHighScore("testplayer", 3);
	assertEquals("player1=1000~~player2=500~~player3=250~~player4=125~~player5=100~~player6=50~~player7=25~~player8=10~~player9=5~~testplayer=3", fullHighScores.testVariableDelimitedString);
}
public void testGetLowestHighScore() throws Exception {
	assertEquals(250, highScores.getLowestHighScore());
}
public void testHighScoresFormatting() throws Exception {
	Vector v = new Vector();
	highScores.addFormattedHighScoresTo(v);
	Enumeration enum = v.elements();
	assertEquals("player1 1000", enum.nextElement());
	assertEquals("player2 500", enum.nextElement());
	assertEquals("player3 250", enum.nextElement());
}
public void testRetrieveHighScoresDelimitedString() throws Exception {
	assertEquals(TEST_HIGH_SCORE_TEST_FILE_CONTENTS, highScores.testVariableDelimitedString);
}
}
