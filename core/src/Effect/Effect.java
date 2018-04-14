package Effect;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Effect extends Sprite{

	public abstract void update();
	public abstract boolean isFinish();
}
