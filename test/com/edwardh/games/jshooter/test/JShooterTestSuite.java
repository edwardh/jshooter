package com.edwardh.games.jshooter.test;

import junit.framework.*;
public class JShooterTestSuite extends TestCase {
public JShooterTestSuite(String name) {
	super(name);
}
public static TestSuite suite() {
	TestSuite result = JShooterTestSuiteWithoutHighScoreTests.suite();
	result.addTest(new TestSuite(HighScoreTest.class));
	return result;
}
}
