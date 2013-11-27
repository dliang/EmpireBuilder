package com.me.empirebuilder.Buildings;

import com.badlogic.gdx.math.Vector2;
import com.me.empirebuilder.PlayerObject;
import com.me.empirebuilder.Enums.BuildingTexture;
import com.me.empirebuilder.Enums.ObjectType;

public abstract class Building extends PlayerObject {

	protected int hitpoints;
	protected int cost;
	protected BuildingTexture texture;
	protected int size;
	protected boolean isSelected;

	public Building(Vector2 position, int size, int hitpoints, int cost) {
		super(position);
		this.hitpoints = hitpoints;
		this.cost = cost;
		this.size = size;
		texture = BuildingTexture.HOUSE;
		objectType = ObjectType.BUILDING;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getHitpoints() {
		return hitpoints;
	}

	public void setHitpoints(int hitpoints) {
		this.hitpoints = hitpoints;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public BuildingTexture getTexture() {
		return texture;
	}

	public void setTexture(BuildingTexture texture) {
		this.texture = texture;
	}	
	
	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	public abstract void printName();

}
