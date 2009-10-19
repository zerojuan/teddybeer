package com.icecream.entity;

import com.icecream.unit.IComponent;


public abstract class Entity{
	protected String id;
	
	public Entity(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
}
