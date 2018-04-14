package net.periple.character;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.periple.game.Animation;

public abstract class Monster extends Sprite{
	
	private int[][] collision;

	private int life;
	private int mana;
	
	private boolean dispear = false;
	
	private int dir = 1;
	
	private float current = 0;
	
	private List<Animation> animations;
    private int currentAnim = 1;
    
    private long startAnim;
    
    private float posX;
    private float posY;

	
	public Monster (Sprite sprite, int vie, int mana, int[][] collision) {
		super(sprite);
		this.collision = collision;
		this.animations = getAnim();
		//sheet = sprite.getTexture();
		//frames = TextureRegion.split(sheet, 32, 32);

		this.life = vie;
		this.mana = mana;
	}
	
	public abstract List<Animation> getAnim();
	
	public void setPos(float x, float y){
		posX = x;
		posY = y;
	}
	
	public float getPosX () {
		return posX;
	}
	
	public float getPosY () {
		return posY;
	}
	
	public int getLife () {
		return life;
	}
	
	public int getMana () {
		return mana;
	}
	
	public void setLife (int life) {
		if(this.life + life <= 0){
			this.life = 0;
		}else{
			this.life += life;
		}
	}
	
	public void receiveLife (int life) {
		if(this.life > life){
			animation("Hurt");
		}
		this.life = life;
	}
	
	public void setMana (int mana) {
		this.mana += mana;
	}
	
	public void receiveMana (int mana) {
		this.mana = mana;
	}
	
	public abstract void animation(String name);

	public void play(int id){
		animations.get(id).play(posX, posY);
		startAnim = System.currentTimeMillis();
		current = 0;
		currentAnim = id;
	}
	
	public abstract void update();
	
	public Animation getAnim (int id) {
		return animations.get(id);
	}
	
	public int getCurrentAnim () {
		return currentAnim;
	}
	
	public long getStartAnim () {
		return startAnim;
	}
	
	public void setCurrent (float current) {
		this.current = current;
	}
	
	public float getCurrent () {
		return current;
	}
	
	public abstract void moveUp();
	public abstract void moveDown();
	public abstract void moveRight();
	public abstract void moveLeft();
	
	public void up () {
		posY += 32;
		dir = 0;
	}
	
	public void down () {
		posY -= 32;
		dir = 1;
	}

	public void right () {
		posX += 32;
		dir = 2;
	}

	public void left () {
		posX -= 32;
		dir = 3;
	}
	
	public void setDir(int dir){
		if(this.dir != dir){
			animations.get(dir).play(posX, posY);
			startAnim = System.currentTimeMillis();
			current = 0;
			this.dir = dir;
			currentAnim = dir;
		}
	}
	
	public void draw (SpriteBatch spriteBatch) {
		//update(Gdx.graphics.getDeltaTime());
		super.draw(spriteBatch);
	}

	public abstract boolean isMove();
	
	public void drawCase(Batch batch, float x, float y){
		Sprite caseSpace = new Sprite(new Texture("bin/img/redCase.png"));
		caseSpace.setAlpha(0.25f);
		for(int[] coord : collision){
			caseSpace.setPosition(x+coord[0]*32, y+coord[1]*32);
			caseSpace.draw(batch);
		}
	}
	
	public int getDir () {
		return dir;
	}
	
	public boolean isTouch (float tX, float tY) {
		System.out.println(collision.length);
		for(int[] coord : collision){
			if(posX + coord[0]*32 == tX && posY + coord[1]*32 == tY){
				return true;
			}
		}
		return false;
	}
	
	public Animation getAnimation () {
		return animations.get(currentAnim);
	}
	
	public void setDispear (boolean dispear) {
		this.dispear = dispear;
	}

	public boolean isDispear() {
		return dispear;
	}
}
