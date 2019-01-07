package com.edwardh.games.jshooter.test;

import junit.framework.*;
import com.edwardh.games.jshooter.utility.*;
public class UtilityTest extends TestCase {
public UtilityTest(String name) {
	super(name);
}
public void testGreaterOf() {
	assertEquals(5, JShooterUtility.greaterOf(5, 1));
	assertEquals(5, JShooterUtility.greaterOf(-1, 5));
	assertEquals(5, JShooterUtility.greaterOf(5, 5));
}
public void testRandomIntBetweenInclusiveZeroAnd() {
	int random = JShooterUtility.randomIntBetweenInclusiveZeroAnd(10);
	assertTrue(random >= 0 && random <= 10);
}
}
