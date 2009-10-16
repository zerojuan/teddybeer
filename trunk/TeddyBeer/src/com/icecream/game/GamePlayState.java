package com.icecream.game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.icecream.component.InputComponent;
import com.icecream.component.RenderComponent;
import com.icecream.component.SpatialComponent;
import com.icecream.entity.Player;
import com.icecream.entity.Sprite;
import com.icecream.exception.MissingComponentException;
import com.icecream.factory.AssetFactory;
import com.icecream.manager.InputManager;
import com.icecream.manager.RenderingManager;
import com.icecream.manager.SpatialManager;
import com.icecream.util.EImgType;

public class GamePlayState extends BasicGameState{
	private int stateID = -1;
	
	private Sprite background;
	
	private Player player;
	
	public GamePlayState(int state){
		stateID = state;
	}
	
	@Override
	public int getID() {
		return stateID;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		/*SpriteSheet spriteSheet = new SpriteSheet(
						AssetFactory.instance().getImage(EImgType.PLAYER), 
						288/8, 65,0,0);
		running = new Animation(spriteSheet,100);
		
		backgroundImg = AssetFactory.instance().getImage(EImgType.BACKGROUND);*/
		player = new Player("Player");
		background = new Sprite("Background");
		
		SpatialComponent backgroundSp = new SpatialComponent();
		backgroundSp.setPosition(new Vector2f(0,0));		
			
		RenderComponent backgroundRd = new RenderComponent();
		backgroundRd.setImage(AssetFactory.instance().getImage(EImgType.BACKGROUND));
		backgroundRd.setLayer(0);
		backgroundRd.connect(backgroundSp);
		
		background.connect(backgroundSp);
		background.connect(backgroundRd);
		backgroundSp.connect(background);
		
		SpatialComponent playerSp = new SpatialComponent();
		playerSp.setPosition(new Vector2f(100,100));
		
		InputComponent inputCm = new InputComponent();
		inputCm.connect(playerSp);
		inputCm.connect(player);
		
		RenderComponent playerRd = new RenderComponent();
		playerRd.setImage(AssetFactory.instance().getImage(EImgType.PLAYER));
		playerRd.setLayer(1);
		playerRd.connect(playerSp);
		
		player.connect(playerSp);
		player.connect(playerRd);
		player.connect(inputCm);
		playerSp.connect(player);
		
		InputManager.instance().addComponent(inputCm);
		SpatialManager.instance().addComponent(backgroundSp);
		SpatialManager.instance().addComponent(playerSp);
		RenderingManager.instance().addComponent(backgroundRd);
		RenderingManager.instance().addComponent(playerRd);
		
		try {
			playerSp.activate();
			backgroundSp.activate();
			inputCm.activate();
		} catch (MissingComponentException e) {	
			e.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		RenderingManager.instance().render(container, game, g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		InputManager.instance().update(container, game, delta);
		SpatialManager.instance().update(container, game, delta);
		
	}
	
}
