package com.icecream.util;

import org.newdawn.slick.geom.Vector2f;

/**
 * Basic math conversions
 * @author Julius
 *
 */
public class MathD {
	public static float degreesToRadians(float degrees){
		return degrees * (float)(Math.PI/180);
	}
	
	public static float radiansToDegrees(float radians){
		return radians * (float)(180/Math.PI);
	}
	
	public static float angleBetween(Vector2f a, Vector2f b){
		return (float)Math.atan2(b.y - a.y, b.x - a.x) * (float)(180 / Math.PI);
	}
}
