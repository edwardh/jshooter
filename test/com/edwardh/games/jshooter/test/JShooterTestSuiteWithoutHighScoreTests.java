package com.edwardh.games.jshooter.test;

import junit.framework.*;
public class JShooterTestSuiteWithoutHighScoreTests extends TestCase {
public JShooterTestSuiteWithoutHighScoreTests(String name) {
	super(name);
}
public static TestSuite suite() {
	TestSuite result = new TestSuite();
	result.addTest(new TestSuite(PackAlienTest.class));
	result.addTest(new TestSuite(CrabAlienTest.class));
	result.addTest(new TestSuite(AsteroidTest.class));
	result.addTest(new TestSuite(ShieldTest.class));
	result.addTest(new TestSuite(BulletTest.class));
	result.addTest(new TestSuite(CollisionTest.class));
	result.addTest(new TestSuite(ExplosionTest.class));
	result.addTest(new TestSuite(GameTest.class));
	result.addTest(new TestSuite(PowerUpTest.class));
	result.addTest(new TestSuite(RocketTest.class));
	result.addTest(new TestSuite(StarTest.class));
	result.addTest(new TestSuite(UtilityTest.class));
	result.addTest(new TestSuite(TankAlienTest.class));
	return result;
}
}
