package com.icecream.test;

import org.junit.Test;
import org.newdawn.slick.geom.Vector2f;
import org.junit.Assert;

import com.icecream.util.MathD;


public class MathDTest{
	
	@Test
	public void testAngleBetween(){
		Vector2f pullForce = new Vector2f(40,10);
		Vector2f gravity = new Vector2f(-10,10);
		float angle = MathD.angleBetween(pullForce, gravity);
		Assert.assertTrue(angle == 180);
		
		pullForce = new Vector2f(50,50);
		gravity = new Vector2f(30,50);
		angle = MathD.angleBetween(pullForce, gravity);
		Assert.assertTrue("Expected 180 got " + angle, angle == 180);
	}
	
		
}
