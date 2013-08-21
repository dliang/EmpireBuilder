package com.me.empirebuilder.Buildings;

import com.badlogic.gdx.math.Vector2;
import com.me.empirebuilder.Enums.BuildingTexture;

public abstract class Building {

	protected Vector2 position;
	protected int hitpoints;
	protected int cost;
	protected BuildingTexture texture;
	protected int size;
	protected boolean isSelected;

	public Building(Vector2 position, int size, int hitpoints, int cost) {
		this.position = position;
		this.hitpoints = hitpoints;
		this.cost = cost;
		this.size = size;
		texture = BuildingTexture.HOUSE;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
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
