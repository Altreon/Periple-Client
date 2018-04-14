package Action;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import Effect.Effect;
import Effect.EffectIce;
import Effect.EffectLightning;
import net.periple.character.Monster;
import net.periple.character.Personnage;
import net.periple.game.Periple;
import net.periple.screen.GameScreen;

public class ActionPlayerSuperAttack extends Action{
	
	private static boolean playerAction = true;
	private static Texture fightEffect = Periple.getAssets().get("bin/img/SkyUppercut.PNG", Texture.class);
	private static Texture fightPicture = Periple.getAssets().get("bin/img/angela.png", Texture.class);
	private static Sound sound = Periple.getAssets().get("bin/sound/snd_flameloop.ogg", Sound.class);
	private static int time = 4950;
		
	private int[][][] areas = {{{0,1}}, 
								{{0,2}}, 
								{{0,3}}};
	private boolean[] framesActive = {false, false, false};
	private List<Effect> attackEffects = new ArrayList<Effect>();
	
	private int frame = -1;	
	private int milis = 200;
	private long timeOld;
	
	private Personnage player;
	private boolean sondPlayed = false;

	public ActionPlayerSuperAttack() {
		super(playerAction, sound, fightEffect, fightPicture, time);
	}

	@Override
	public void use(Personnage player) {
		super.playFightEffect();
		this.player = player;
		player.animation("SuperAttack");
		
		if(player.getDir() == 1){
			int[][][] newAreas = {{{0,-1}}, 
					{{0,-2}}, 
					{{0,-3}}};
			areas = newAreas;
		}else if(player.getDir() == 2){
			int[][][] newAreas = {{{1,0}}, 
					{{2,0}}, 
					{{3,0}}};
			areas = newAreas;
		}else if(player.getDir() == 3){
			int[][][] newAreas = {{{-1,0}}, 
					{{-2,0}}, 
					{{-3,0}}};
			areas = newAreas;
		}
	}
	
	@Override
	public void update(Batch renderer) {
		if(player.getAnimation().getFrame() >= 7){
			if(!sondPlayed){
				super.playSond();
				sondPlayed = true;
			}
			if(System.currentTimeMillis() - timeOld > milis && frame < areas.length-1){
				frame++;
				timeOld = System.currentTimeMillis();
			}
			if(!framesActive[frame]){
				for(int[] area : areas[frame]){
					if(!GameScreen.isBlock((int)(player.getPosX()/32 + area[0]), (int)(player.getPosY()/32 + area[1]))){
						Effect attackEffect = new EffectIce(player.getPosX() + (area[0]*32), player.getPosY() + (area[1]*32));
						attackEffects.add(attackEffect);
						framesActive[frame] = true;
					}
				}
			}
			List<Effect> attackEffectsSupp = new ArrayList<Effect>();
			for(Effect attackEffect : attackEffects){
				if(!attackEffect.isFinish()){
					attackEffect.draw(renderer);
					attackEffect.update();
				}else{
					attackEffectsSupp.add(attackEffect);
				}
			}
			for(Effect attackEffectSupp : attackEffectsSupp){
				attackEffects.remove(attackEffectSupp);
			}
		}
		
	}

	@Override
	public boolean isFinish() {
		if(frame >= areas.length-1 && attackEffects.size() == 0){
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
