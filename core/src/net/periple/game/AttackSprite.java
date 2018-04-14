package net.periple.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AttackSprite extends Sprite{
	
	private static Texture texture = Periple.getAssets().get("img/Lightning.png", Texture.class);
	
	private Texture sheet;
    private TextureRegion[][] frames;
    
    private int frame = 0;
    private int nbFrame = 11;
    private long time;
	
	private int milis;
	private long createTime;

	public AttackSprite(int milis) {
		sheet = texture;
		frames = TextureRegion.split(sheet, 32, 70);
		super.set(new Sprite(frames[frame][0]));
		
		time = System.currentTimeMillis();
		
		this.milis = milis;
		createTime = System.currentTimeMillis();
	}
	
	public void frame () {
		if(System.currentTimeMillis() - time > milis/nbFrame){
			float posX = super.getX();
			float posY = super.getY();
			super.set(new Sprite(frames[0][frame]));
			super.setPosition(posX, posY);
			time = System.currentTimeMillis();
			frame++;
		}
		
		if(frame >= 10){
			frame = 0;
		}
	}

	public boolean isFinish() {
		if(System.currentTimeMillis() - createTime > milis){
			return true;
		}else{
			return false;
		}
	}
}
