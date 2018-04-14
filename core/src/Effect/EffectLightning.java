package Effect;

import net.periple.game.Animation;

public class EffectLightning extends Effect{
	private Animation animation;
	
	public EffectLightning (float x, float y) {
		animation = new Animation("Effect", "Lightning", this);
		animation.play(x, y);
	}

	@Override
	public void update() {
		if(!animation.isFinish()){
			animation.update(this.getX(), this.getY());
		}
	}

	@Override
	public boolean isFinish() {
		if(animation.isFinish()){
			return true;
		}else{
			return false;
		}
	}
}
