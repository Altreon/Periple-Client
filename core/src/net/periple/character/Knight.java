package net.periple.character;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;

import net.periple.game.Animation;

public class Knight extends Monster {
	static int[][] collision = {{0,0}};

	public Knight(Sprite sprite, int vie, int mana) {
		super(sprite, vie, mana, collision);
		
	}

	@Override
	public List<Animation> getAnim() {
		List<Animation> animations = new ArrayList<Animation>();
		animations.add(new Animation("Knight", "IdleUp", this));
		animations.add(new Animation("Knight", "IdleDown", this));
		animations.add(new Animation("Knight", "IdleRight", this));
		animations.add(new Animation("Knight", "IdleLeft", this));
		animations.add(new Animation("Knight", "WalkUp", this));
		animations.add(new Animation("Knight", "WalkDown", this));
		animations.add(new Animation("Knight", "WalkRight", this));
		animations.add(new Animation("Knight", "WalkLeft", this));
		animations.add(new Animation("Knight", "AttackUp", this));
		animations.add(new Animation("Knight", "AttackDown", this));
		animations.add(new Animation("Knight", "AttackRight", this));
		animations.add(new Animation("Knight", "AttackLeft", this));
		animations.add(new Animation("Knight", "SuperAttack", this));
		animations.add(new Animation("Knight", "Hurt", this));
		animations.add(new Animation("Knight", "Dead", this));
		return animations;
	}

	@Override
	public void animation(String name) {
		if(name.equals("Attack")){
			super.play(8+super.getDir());
		}else if(name.equals("SuperAttack")){
			super.play(12);
		}else if(name.equals("Hurt")){
			super.play(13);
		}else if(name.equals("Dead")){
			super.play(14);
		}
	}

	@Override
	public void update() {
		if(super.getLife() > 0){
			if(!super.getAnim(super.getCurrentAnim()).isFinish()){
				if(super.getCurrentAnim() < 4 || super.getCurrentAnim() > 7){
					super.getAnim(super.getCurrentAnim()).update(super.getPosX(), super.getPosY());
				}
				if(!super.getAnim(super.getCurrentAnim()).isFinish() && super.getCurrentAnim() >= 4){
					long delta = System.currentTimeMillis() - super.getStartAnim()+1;
					super.setCurrent(delta/1000F);
					float add = 32*super.getCurrent();
					if(add > 32){
						add = 32;
					}
					if(super.getCurrentAnim() == 4){
						super.getAnim(super.getCurrentAnim()).update(super.getPosX(), super.getPosY()-32 + add);
					}else if(super.getCurrentAnim() == 5){
						super.getAnim(super.getCurrentAnim()).update(super.getPosX(), super.getPosY()+32 - add);
					}else if(super.getCurrentAnim() == 6){
						super.getAnim(super.getCurrentAnim()).update(super.getPosX()-32 + add, super.getPosY());
					}else if(super.getCurrentAnim() == 7){
						super.getAnim(super.getCurrentAnim()).update(super.getPosX()+32 - add, super.getPosY());
					}
				}else{
					if(super.getCurrentAnim() >= 4){
						super.getAnim(super.getCurrentAnim()).update(super.getPosX(), super.getPosY());
					}
				}
			}else{
				super.play(super.getDir());
			}
		}else{
			if(super.getCurrentAnim() != 14){
				if(super.getAnim(super.getCurrentAnim()).isFinish()){
					animation("Dead");
				}else{
					super.getAnim(super.getCurrentAnim()).update(super.getPosX(), super.getPosY());
				}
			}else{
				if(super.getAnim(super.getCurrentAnim()).isFinish()){
					super.setDispear(true);
				}else{
					super.getAnim(super.getCurrentAnim()).update(super.getPosX(), super.getPosY());
				}
			}
		}
		
	}

	@Override
	public void moveUp() {
		super.play(4);
		super.up();
	}

	@Override
	public void moveDown() {
		super.play(5);
		super.down();
	}

	@Override
	public void moveRight() {
		super.play(6);
		super.right();
	}

	@Override
	public void moveLeft() {
		super.play(7);
		super.left();
	}

	@Override
	public boolean isMove() {
		if(super.getCurrentAnim() >= 4){
			if(super.getAnim(super.getCurrentAnim()).isFinish()){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}

}
