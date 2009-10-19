package com.icecream.game;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.icecream.component.AnimationRenderComponent;
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
import com.icecream.util.EAnimType;
import com.icecream.util.ECollisionId;
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
		
		List<Animation> playerAnimations = new ArrayList<Animation>();
		playerAnimations.add( new Animation(AssetFactory.instance().getSpriteSheet(EAnimType.PLAYER_RUNNING), 100));
		
		background = new Sprite("Background");
		
		SpatialComponent backgroundSp = new SpatialComponent(background, new Vector2f(0,0));					
		RenderComponent backgroundRender = new RenderComponent(backgroundSp, background, 0, AssetFactory.instance().getImage(EImgType.BACKGROUND));
		
		background.connect(backgroundSp);
		background.connect(backgroundRender);	
		
		player = new Player("Player");
		
		SpatialComponent playerSp = new SpatialComponent(player, new Vector2f(100,100), ECollisionId.Player);				
		InputComponent inputCm = new InputComponent(playerSp, player);						
		RenderComponent playerRd = new AnimationRenderComponent(playerSp, player, 1, playerAnimations);		
		
		player.connect(playerSp);
		player.connect(playerRd);
		player.connect(inputCm);	
		
		
		
		InputManager.instance().addComponent(inputCm);
		SpatialManager.instance().addComponent(backgroundSp);
		SpatialManager.instance().addComponent(playerSp);
		RenderingManager.instance().addComponent(backgroundRender);
		RenderingManager.instance().addComponent(playerRd);
		
		for(int i = 0; i < 20; i++){
			Sprite enemy = new Sprite("Enemy");
			SpatialComponent enemySp = new SpatialComponent(enemy, new Vector2f(100 * i,100 * i), ECollisionId.Enemies);
			InputComponent anotherInput = new InputComponent(enemySp, enemy);
			RenderComponent renderComponent = new AnimationRenderComponent(enemySp, enemy, 2, playerAnimations);
			
			
			enemy.connect(enemySp);
			enemy.connect(renderComponent);
			enemy.connect(anotherInput);
			
			try {
				enemySp.activate();
				anotherInput.activate();
			} catch (MissingComponentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			InputManager.instance().addComponent(anotherInput);
			SpatialManager.instance().addComponent(enemySp);
			RenderingManager.instance().addComponent(renderComponent);
		}
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
