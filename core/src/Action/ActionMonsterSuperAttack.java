package Action;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import Effect.Effect;
import Effect.EffectLightning;
import net.periple.character.Monster;
import net.periple.character.Personnage;
import net.periple.game.AttackSprite;
import net.periple.game.Periple;
import net.periple.screen.GameScreen;

public class ActionMonsterSuperAttack extends Action{
	
	private static boolean playerAction = false;
	private static Texture fightEffect = Periple.getAssets().get("bin/img/Psychic.png", Texture.class);
	private static Texture fightPicture = Periple.getAssets().get("bin/img/knight.png", Texture.class);
	private static Sound sound = Periple.getAssets().get("bin/sound/snd_flameloop.ogg", Sound.class);
	private static int time = 5450;
		
	private int[][][] areas = {{{0,1}, {0,-1}, {1,0}, {-1,0}}, 
								{{0,2}, {1,1}, {-1,1}, {0,-2}, {1,-1}, {-1,-1}, {2,0}, {-2,0}}, 
								{{0,3}, {1,2}, {-1,2}, {0,-3}, {1,-2}, {-1,-2}, {3,0}, {2,1}, {2,-1}, {-3,0}, {-2,1}, {-2,-1}}};
	private boolean[] framesActive = {false, false, false};
	private List<Effect> attackEffects = new ArrayList<Effect>();
	
	private int frame = -1;	
	private int milis = 200;
	private long timeOld;
	
	private Monster monster;
	private boolean sondPlayed = false;

	public ActionMonsterSuperAttack() {
		super(playerAction, sound, fightEffect, fightPicture, time);
	}

	@Override
	public void use(Monster monster) {
		super.playFightEffect();
		this.monster = monster;
		monster.animation("SuperAttack");
	}
	
	/*
	@Override
	public void useEffect() {
		super.playSond();
		GameScreen.addActionEffect(this);
		framesActive[0] = false;
		framesActive[1] = false;
		framesActive[2] = false;
		timeOld = System.currentTimeMillis();
		frame = 0;
	}
	*/

	@Override
	public void update(Batch renderer) {
		if(monster.getAnimation().getFrame() >= 43){
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
					if(!GameScreen.isBlock((int)(monster.getPosX()/32 + area[0]), (int)(monster.getPosY()/32 + area[1]))){
						Effect attackEffect = new EffectLightning(monster.getPosX() + (area[0]*32), monster.getPosY() + (area[1]*32));
						attackEffects.add(attackEffect);
						framesActive[frame] = true;
					}
				}
			}else{
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
	public void use(Personnage player) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getWaitTime () {
		return (int) monster.getAnimation().getTotalMilis();
	}
}
