package com.icecream.component;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public interface IUpdateable {
	public abstract void update(GameContainer gc, StateBasedGame sb, int delta);
}
