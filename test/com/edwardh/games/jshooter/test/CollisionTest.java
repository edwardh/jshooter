package com.edwardh.games.jshooter.test;

import com.edwardh.games.jshooter.utility.*;
import com.edwardh.games.jshooter.entity.*;
import junit.framework.*;
public class CollisionTest extends TestCase {
	Entity entity1, entity2;
public CollisionTest(String arg1) {
	super(arg1);
}
public void setUp() {
	entity1 = new DummyEntity();
	entity1.setWidth(25);
	entity1.setHeight(15);
	entity1.setXPos(100);
	entity1.setYPos(200);
	//
	entity2 = new DummyEntity();
	entity2.setWidth(5);
	entity2.setHeight(10);
}
public void testBoundaryCases() {
	//as close as can be without touching
	//entity2 on the left of entity1
	entity2.setXPos(94);
	entity2.setYPos(200);
	assertTrue(!CollisionUtility.entitiesOverlap(entity1, entity2));
	//now entity 2 on top of entity1
	entity2.setXPos(100);
	entity2.setYPos(189);
	assertTrue(!CollisionUtility.entitiesOverlap(entity1, entity2));	
	
	//just barely touching
	//entity2 on the left of entity1
	entity2.setXPos(95);
	entity2.setYPos(200);
	assertTrue(CollisionUtility.entitiesOverlap(entity1, entity2));
	//entity2 below entity1
	entity2.setXPos(100);
	entity2.setYPos(215);
	assertTrue(CollisionUtility.entitiesOverlap(entity1, entity2));
}
public void testSimple() {
	//not overlapping
	entity2.setXPos(50);
	entity2.setYPos(60);
	assertTrue(!CollisionUtility.entitiesOverlap(entity1, entity2));
	
	//overlapping
	entity2.setXPos(98);
	entity2.setYPos(195);
	assertTrue(CollisionUtility.entitiesOverlap(entity1, entity2));
}
}
