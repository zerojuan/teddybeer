package com.icecream.manager;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import com.icecream.component.IRenderable;
import com.icecream.component.RenderComponent;
import com.icecream.unit.IComponent;

public class RenderingManager implements IManager, IRenderable{	
	private List<List<IComponent>> renderComponents;	
	
	private static RenderingManager instance;
	
	private RenderingManager(){
		renderComponents = new ArrayList<List<IComponent>>();
		renderComponents.add(new ArrayList<IComponent>());
	}
	
	public static RenderingManager instance(){
		if(instance == null){
			instance = new RenderingManager();
		}
		return instance;
	}
	
	/**
	 * Adds a component.
	 * The order the component was added is important on startup
	 * First start with the background component (layer0)
	 * Then work it up.
	 * This is because the ordering system is implemented as a list 
	 * @param component
	 */
	public void addComponent(IComponent component){
		if(component instanceof RenderComponent){
			RenderComponent rComp = (RenderComponent)component;
			List<IComponent> list;
			int compLayer = rComp.getLayer();
			if(renderComponents.size() > compLayer){
				list = new ArrayList<IComponent>();
				renderComponents.add(list);
				list.add(component);
			}else{
				list = renderComponents.get(compLayer);
				list.add(component);
			}
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) {
		for(List<IComponent> list : renderComponents){
			for(IComponent comp : list){				
				RenderComponent rComp = (RenderComponent)comp;
				rComp.render(gc, sb, g);
			}
		}
		
	}
	
	
}
