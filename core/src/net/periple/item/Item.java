package net.periple.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Item extends Sprite{
	
	private int ID;
	private String type;
	private boolean equiped = false;
	private int foodValue;
	
	public Item (int ID, String s, String type, int foodValue){
		super(new Sprite(new Texture(s)));
		this.ID = ID;
		this.type = type;
		this.foodValue = foodValue;
	}
	
	public int getID () {
		return ID;
	}
	
	public void setPos (int x, int y){
		super.setX(x);
		super.setY(y);
	}

	public String getType() {
		return type;
	}

	public boolean isEquiped() {
		return equiped;
	}

	public void setEquiped(boolean equiped) {
		this.equiped = equiped;
	}

	public int getFoodValue() {
		return foodValue;
	}
}
