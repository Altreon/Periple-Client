package net.periple.character;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import net.periple.game.Animation;

public class Personnage extends Sprite {
	
	private String name;
	private int life;
	private int mana;
	private int maxLife = 50;
	private int maxMana = 50;
	private int moveAction = 5;
	private int skillAction = 10;
	
	private boolean dispear = false;
		
	//private boolean move = false;
	/*
	private int moveStat = 32;
	private int moveFrame = 3;
	private long time = 0;
	*/
	private int dir = 1;
	private float current = 0;
	/*
	private int[] finalMove = new int[2];
	
	
    private Texture sheet;
    private TextureRegion[][] frames;
    */
    
    private List<Animation> animations = new ArrayList<Animation>();
    private int currentAnim = 1;
    
    private long startAnim;
    
    private float posX;
    private float posY;
	
	public Personnage (Sprite sprite, String name, int vie, int mana) {
		super(sprite);
		animations.add(new Animation("Angela", "IdleUp", this));
		animations.add(new Animation("Angela", "IdleDown", this));
		animations.add(new Animation("Angela", "IdleRight", this));
		animations.add(new Animation("Angela", "IdleLeft", this));
		animations.add(new Animation("Angela", "WalkUp", this));
		animations.add(new Animation("Angela", "WalkDown", this));
		animations.add(new Animation("Angela", "WalkRight", this));
		animations.add(new Animation("Angela", "WalkLeft", this));
		animations.add(new Animation("Angela", "AttackUp", this));
		animations.add(new Animation("Angela", "AttackDown", this));
		animations.add(new Animation("Angela", "AttackRight", this));
		animations.add(new Animation("Angela", "AttackLeft", this));
		animations.add(new Animation("Angela", "SuperAttackUp", this));
		animations.add(new Animation("Angela", "SuperAttackDown", this));
		animations.add(new Animation("Angela", "SuperAttackRight", this));
		animations.add(new Animation("Angela", "SuperAttackLeft", this));
		animations.add(new Animation("Angela", "HurtUp", this));
		animations.add(new Animation("Angela", "HurtDown", this));
		animations.add(new Animation("Angela", "HurtRight", this));
		animations.add(new Animation("Angela", "HurtLeft", this));
		animations.add(new Animation("Angela", "DeadUp", this));
		animations.add(new Animation("Angela", "DeadDown", this));
		animations.add(new Animation("Angela", "DeadRight", this));
		animations.add(new Animation("Angela", "DeadLeft", this));
		animations.add(new Animation("Angela", "FightIdleUp", this));
		animations.add(new Animation("Angela", "FightIdleDown", this));
		animations.add(new Animation("Angela", "FightIdleRight", this));
		animations.add(new Animation("Angela", "FightIdleLeft", this));
		animations.add(new Animation("Angela", "FightWalkUp", this));
		animations.add(new Animation("Angela", "FightWalkDown", this));
		animations.add(new Animation("Angela", "FightWalkRight", this));
		animations.add(new Animation("Angela", "FightWalkLeft", this));
		animations.add(new Animation("Angela", "FightBeginUp", this));
		animations.add(new Animation("Angela", "FightBeginDown", this));
		animations.add(new Animation("Angela", "FightBeginRight", this));
		animations.add(new Animation("Angela", "FightBeginLeft", this));
		animations.add(new Animation("Angela", "FightEndUp", this));
		animations.add(new Animation("Angela", "FightEndDown", this));
		animations.add(new Animation("Angela", "FightEndRight", this));
		animations.add(new Animation("Angela", "FightEndLeft", this));
		//sheet = sprite.getTexture();
		//frames = TextureRegion.split(sheet, 32, 32);

		this.name = name;
		this.life = vie;
		this.mana = mana;
	}
	
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
	
	public String getName () {
		return name;
	}
	
	public int getMaxLife () {
		return maxLife;
	}
	
	public int getMaxMana () {
		return maxMana;
	}
	
	public int getLife () {
		return life;
	}
	
	public int getMana () {
		return mana;
	}
	
	public void setLife (int life) {
		if(this.life + life > maxLife){
			this.life = maxLife;
		}else if(this.life + life <= 0){
			this.life = 0;
			System.out.println("DEAD");
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
	
	public int getMoveAction() {
		return moveAction;
	}

	public void setMoveAction(int moveAction) {
		this.moveAction = moveAction;
	}

	public int getSkillAction() {
		return skillAction;
	}

	public void setSkillAction(int skillAction) {
		this.skillAction = skillAction;
	}
	
	public void animation(String name) {
		if(name.equals("Attack")){
			animations.get(8+dir).play(posX, posY);
			startAnim = System.currentTimeMillis();
			current = 0;
			currentAnim = 8+dir;
		}else if(name.equals("SuperAttack")){
			animations.get(12+dir).play(posX, posY);
			startAnim = System.currentTimeMillis();
			current = 0;
			currentAnim = 12+dir;
		}else if(name.equals("Hurt")){
			animations.get(16+dir).play(posX, posY);
			startAnim = System.currentTimeMillis();
			current = 0;
			currentAnim = 16+dir;
		}else if(name.equals("Dead")){
			animations.get(20+dir).play(posX, posY);
			startAnim = System.currentTimeMillis();
			current = 0;
			currentAnim = 20+dir;
		}else if(name.equals("FightBegin")){
			animations.get(32+dir).play(posX, posY);
			startAnim = System.currentTimeMillis();
			current = 0;
			currentAnim = 32+dir;
		}else if(name.equals("FightEnd")){
			animations.get(36+dir).play(posX, posY);
			startAnim = System.currentTimeMillis();
			current = 0;
			currentAnim = 36+dir;
		}
	}
	
	public void update(boolean isFight) {
		if(life > 0){
			if(!animations.get(currentAnim).isFinish()){
				if((currentAnim < 4 || currentAnim > 7) && (currentAnim < 28 || currentAnim > 31)){
					animations.get(currentAnim).update(posX, posY);
				}
				if(!animations.get(currentAnim).isFinish() && currentAnim >= 4){
					long delta = System.currentTimeMillis() - startAnim+1;
					current = delta/animations.get(currentAnim).getTotalMilis();
					float add = 32*current;
					if(add > 32){
						add = 32;
					}
					if(currentAnim == 4 || currentAnim == 28){
						animations.get(currentAnim).update(posX, posY-32 + add);
						//super.setPosition(posX, posY - add);
					}else if(currentAnim == 5 || currentAnim == 29){
						animations.get(currentAnim).update(posX, posY+32 - add);
						//super.setPosition(posX, posY + add);
					}else if(currentAnim == 6 || currentAnim == 30){
						animations.get(currentAnim).update(posX-32 + add, posY);
						//super.setPosition(posX - add, posY);
					}else if(currentAnim == 7 || currentAnim == 31){
						animations.get(currentAnim).update(posX+32 - add, posY);
						//super.setPosition(posX + add, posY);
					}
				}else{
					if(currentAnim >= 4){
						animations.get(currentAnim).update(posX, posY);
						//super.setPosition(posX, posY);
					}
					//saveX = super.getX();
					//saveY = super.getY();
				}
			}else{
				if(!isFight){
					animations.get(dir).play(posX, posY);
					currentAnim = dir;
				}else{
					animations.get(24+dir).play(posX, posY);
					currentAnim = 24+dir;
				}
			}
		}else{
			if(currentAnim < 20){
				if(animations.get(currentAnim).isFinish()){
					animation("Dead");
				}else{
					animations.get(currentAnim).update(posX, posY);
				}
			}else{
				if(animations.get(currentAnim).isFinish()){
					dispear = true;
				}else{
					animations.get(currentAnim).update(posX, posY);
				}
			}
		}
	}
	
	public void moveUp (boolean isFight) {
		posY += 32;
		if(!isFight){
			animations.get(4).play(posX, posY);
		}else{
			animations.get(28).play(posX, posY);
		}
		startAnim = System.currentTimeMillis();
		current = 0;
		dir = 0;
		if(!isFight){
			currentAnim = 4;
		}else{
			currentAnim = 28;
		}
	}
	
	public void moveDown (boolean isFight) {
		posY -= 32;
		if(!isFight){
			animations.get(5).play(posX, posY);
		}else{
			animations.get(29).play(posX, posY);
		}
		startAnim = System.currentTimeMillis();
		current = 0;
		dir = 1;
		if(!isFight){
			currentAnim = 5;
		}else{
			currentAnim = 29;
		}
	}

	public void moveRight (boolean isFight) {
		posX += 32;
		if(!isFight){
			animations.get(6).play(posX, posY);
		}else{
			animations.get(30).play(posX, posY);
		}
		startAnim = System.currentTimeMillis();
		current = 0;
		dir = 2;
		if(!isFight){
			currentAnim = 6;
		}else{
			currentAnim = 30;
		}
	}

	public void moveLeft (boolean isFight) {
		posX -= 32;
		if(!isFight){
			animations.get(7).play(posX, posY);
		}else{
			animations.get(31).play(posX, posY);
		}
		startAnim = System.currentTimeMillis();
		current = 0;
		dir = 3;
		if(!isFight){
			currentAnim = 7;
		}else{
			currentAnim = 31;
		}
	}
	
	public void setDir(int dir, boolean isFight){
		if(this.dir != dir){
			animations.get(dir).play(posX, posY);
			startAnim = System.currentTimeMillis();
			current = 0;
			this.dir = dir;
			if(!isFight){
				currentAnim = dir;
			}else{
				currentAnim = 24+dir;
			}
		}
	}

	//public void setMove(int i, int z, int x, int y){
		//super.set(new Sprite(frames[i][z]));
		//super.setPosition(x, y);
	//}
	
	/*
	
	public void setMove(int d, int x, int y){
		move = true;
		dir = d;
		finalMove[0] = x;
		finalMove[1] = y;
		//System.out.println(finalMove[0]);
		time = System.currentTimeMillis();
		current = 0;
		makeMove();
	}
	
	public void setMove(int d){
		float x = super.getX();
		float y = super.getY();
		super.set(new Sprite(frames[d][1]));
		super.setPosition(x, y);
	}
	
	public void makeMove () {
		/*
		moveStat -= 8;
		moveFrame --;
		int frame;
		if(moveFrame != -1){
			frame = moveFrame;
		}else{
			frame = 1;
		}
		
		if(dir == 1){
			super.set(new Sprite(frames[3][frame]));
			super.setPosition(finalMove[0], finalMove[1]-moveStat);
		}else if(dir == 2){
			super.set(new Sprite(frames[0][frame]));
			super.setPosition(finalMove[0], finalMove[1]+moveStat);
		}else if(dir == 3){
			super.set(new Sprite(frames[2][frame]));
			super.setPosition(finalMove[0]-moveStat, finalMove[1]);
		}else{
			super.set(new Sprite(frames[1][frame]));
			super.setPosition(finalMove[0]+moveStat, finalMove[1]);
		}
		
		if(moveFrame == -1){
			move = false;
			moveStat = 32;
			moveFrame = 3;
		}
		*//*
		long delta = System.currentTimeMillis() - time;
		current += 0.1*delta;
		int frame = 1;
		if(current < 24){
			frame = 2;
			if(current < 16){
				frame = 1;
				if(current < 8){
					frame = 0;
				}
			}
		}
		
		if(dir == 1){
			super.set(new Sprite(frames[3][frame]));
			super.setPosition(finalMove[0], finalMove[1]-(32-current));
			if(current >= 32){
				super.set(new Sprite(frames[3][frame]));
				super.setPosition(finalMove[0], finalMove[1]);
				move = false;
			}
		}else if(dir == 2){
			super.set(new Sprite(frames[0][frame]));
			super.setPosition(finalMove[0], finalMove[1]+(32-current));
			if(current >= 32){
				super.set(new Sprite(frames[0][frame]));
				super.setPosition(finalMove[0], finalMove[1]);
				move = false;
			}
		}else if(dir == 3){
			super.set(new Sprite(frames[2][frame]));
			super.setPosition(finalMove[0]-(32-current), finalMove[1]);
			if(current >= 32){
				super.set(new Sprite(frames[2][frame]));
				super.setPosition(finalMove[0], finalMove[1]);
				move = false;
			}
		}else{
			super.set(new Sprite(frames[1][frame]));
			super.setPosition(finalMove[0]+(32-current), finalMove[1]);
			if(current >= 32){
				super.set(new Sprite(frames[1][frame]));
				super.setPosition(finalMove[0], finalMove[1]);
				move = false;
			}
		}
		time = System.currentTimeMillis();
	}
	*/
	
	public void draw (SpriteBatch spriteBatch) {
		//update(Gdx.graphics.getDeltaTime());
		super.draw(spriteBatch);
	}

	public boolean isMove() {
		if((currentAnim >= 4 && currentAnim < 8) || (currentAnim >= 28 && currentAnim < 32)){
			if(animations.get(currentAnim).isFinish()){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	
	public int getDir () {
		return dir;
	}
	
	public void drawCase(Batch batch, float x, float y){
		Sprite caseSpace = null;
		caseSpace = new Sprite(new Texture("bin/img/blueCase.png"));
		caseSpace.setPosition(x, y);
		caseSpace.setAlpha(0.25f);
		caseSpace.draw(batch);
	}
	
	public Animation getAnimation () {
		return animations.get(currentAnim);
	}
	
	public boolean isDispear() {
		return dispear;
	}
}
