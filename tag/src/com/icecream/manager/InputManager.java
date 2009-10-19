package com.icecream.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import com.icecream.component.IUpdateable;
import com.icecream.component.InputComponent;
import com.icecream.unit.IComponent;

public class InputManager implements IManager, IUpdateable{
	private static final Logger logger = Logger.getLogger(InputManager.class.getName()); 
	
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
			logger.fine("Updating input component");
			component.doInput(input);
		}
	}
		

}
