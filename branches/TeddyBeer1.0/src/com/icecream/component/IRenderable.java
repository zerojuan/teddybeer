package com.icecream.component;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public interface IRenderable {
	public abstract void render(GameContainer gc, StateBasedGame sb, Graphics g);
}
