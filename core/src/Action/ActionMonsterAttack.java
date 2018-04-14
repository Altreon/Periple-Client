package Action;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;

import net.periple.character.Monster;
import net.periple.character.Personnage;
import net.periple.game.Periple;

public class ActionMonsterAttack extends Action{
	
	private static boolean playerAction = false;
	private static Sound sound = Periple.getAssets().get("bin/sound/snd_curtgunshot.ogg", Sound.class);
	private static int time = 0;
	
	private Monster monster;
	private boolean sondPlayed = false;

	public ActionMonsterAttack() {
		super(playerAction, sound, time);
	}

	@Override
	public void use(Monster monster) {
		this.monster = monster;
		monster.animation("Attack");
	}
	
	@Override
	public void update(Batch renderer) {
		if(!sondPlayed && monster.getAnimation().getFrame() == 2){
			super.playSond();
			sondPlayed = true;
		}
		
	}

	@Override
	public boolean isFinish() {
		if(monster.getAnimation().getFrame() == monster.getAnimation().getNbFrame()-1){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void use(Personnage player) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getWaitTime () {
		return (int) monster.getAnimation().getTotalMilis();
	}
}
