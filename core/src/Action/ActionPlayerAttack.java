package Action;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;

import net.periple.character.Monster;
import net.periple.character.Personnage;
import net.periple.game.Periple;

public class ActionPlayerAttack extends Action{
	
	private static boolean playerAction = true;
	private static Sound sound = Periple.getAssets().get("bin/sound/snd_curtgunshot.ogg", Sound.class);
	private static int time = 0;
	
	private Personnage player;
	private boolean soundPlayed = false;
	private long endTime;

	public ActionPlayerAttack() {
		super(playerAction, sound, time);
	}
	
	@Override
	public void use (Personnage player) {
		this.player = player;
		player.animation("Attack");
		endTime = System.currentTimeMillis() + (long)player.getAnimation().getTotalMilis();
	}
	
	@Override
	public void update(Batch renderer) {
		if(!soundPlayed && player.getAnimation().getFrame() == 2){
			super.playSond();
			soundPlayed = true;
		}
	}

	@Override
	public boolean isFinish() {
		if(System.currentTimeMillis() >= endTime){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void use(Monster monster) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getWaitTime () {
		return (int) player.getAnimation().getTotalMilis();
	}
}
