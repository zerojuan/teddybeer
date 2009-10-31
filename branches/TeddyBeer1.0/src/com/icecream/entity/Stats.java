package com.icecream.entity;

public class Stats {
	
	private static Stats instance;
	
	public int runningTime;
	public int hidingTime;
	public int shotAtTimes;
	public int diedTimes;
	public int carryingTime;	
	public int score;	
	
	private Stats(){
		reset();
	}

	public void reset(){
		runningTime = 0;
		hidingTime = 0;
		shotAtTimes = 0;
		carryingTime = 0;
		score = 0;
	}
	
	public static Stats instance(){
		if(instance == null){
			instance = new Stats();
		}
		return instance; 
	}
}
