package Action;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import net.periple.character.Monster;
import net.periple.character.Personnage;
import net.periple.game.AttackSprite;
import net.periple.screen.GameScreen;

public abstract class Action {
	private boolean playerAction;
	
	private Sound sound;
	
	private Texture fightEffect;
	private Texture fightPicture;
	
	private AttackSprite attackSprite;
	private int time;

	public Action (boolean playerAction, Sound sound, int time){
		this.playerAction = playerAction;
		this.sound = sound;
		this.time = time;
	}
	
	public Action (boolean playerAction, Sound sound, Texture fightEffect, Texture fightPicture, int time){
		this.playerAction = playerAction;
		this.sound = sound;
		this.fightEffect = fightEffect;
		this.fightPicture = fightPicture;
		this.time = time;
	}
	
	public abstract void use(Personnage player);
	public abstract void use(Monster monster);
	public abstract void update(Batch renderer);
	public abstract boolean isFinish();
	public abstract int getWaitTime();
	
	public void playSond () {
		sound.play();
	}
	
	public void playFightEffect () {
		GameScreen.fightEffect(fightEffect, fightPicture, playerAction);
	}
	
	public void playFightEffect (Action action) {
		GameScreen.fightEffect(fightEffect, fightPicture, playerAction, action);
	}
}
