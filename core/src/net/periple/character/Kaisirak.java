package net.periple.character;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;

import net.periple.game.Animation;

public class Kaisirak extends Monster{
	static int[][] collision = {{3,2},{3,1},{3,0},
								{2,3},{2,2},{2,1},{2,0},{2,-1},{2,-2},
								{1,3},{1,2},{1,1},{1,0},{1,-1},{1,-2},
								{0,3},{0,2},{0,1},{0,0},{0,-1},{0,-2},
								{-1,3},{-1,2},{-1,1},{-1,0},{-1,-1},{-1,-2},
								{-2,3},{-2,2},{-2,1},{-2,0},{-2,-1},{-2,-2},
								{-3,2},{-3,1},{-3,0}};

	public Kaisirak(Sprite sprite, int vie, int mana) {
		super(sprite, vie, mana, collision);
	}

	@Override
	public List<Animation> getAnim() {
		List<Animation> animations = new ArrayList<Animation>();
		animations.add(new Animation("Kaisirak", "Idle1", this));
		animations.add(new Animation("Kaisirak", "Idle2", this));
		return animations;
		
	}

	@Override
	public void animation(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		super.getAnim(super.getCurrentAnim()).update(super.getPosX(), super.getPosY());
		
	}

	@Override
	public void moveUp() {
		super.up();
	}

	@Override
	public void moveDown() {
		super.down();
	}

	@Override
	public void moveRight() {
		super.right();
	}

	@Override
	public void moveLeft() {
		super.left();
	}

	@Override
	public boolean isMove() {
		// TODO Auto-generated method stub
		return false;
	}

}
