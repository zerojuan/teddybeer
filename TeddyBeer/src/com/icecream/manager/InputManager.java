package com.icecream.manager;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import com.icecream.component.IUpdateable;
import com.icecream.component.InputComponent;
import com.icecream.unit.IComponent;

public class InputManager implements IManager, IUpdateable{
	private List<InputComponent> inputComponents;
	
	private static InputManager instance;
	
	private InputManager(){
		inputComponents = new ArrayList<InputComponent>();
	}
	
	public static InputManager instance(){
		if(instance == null){
			instance = new InputManager();
		}
		return instance;
	}
	
	@Override
	public void addComponent(IComponent component) {
		if(component instanceof InputComponent){
			inputComponents.add((InputComponent)component);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		Input input = gc.getInput();
	
		for(InputComponent component: inputComponents){
			Log.info("Updating.. ");
			component.doInput(input);
		}
	}
		

}
