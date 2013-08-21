package com.me.empirebuilder.Units;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.me.empirebuilder.Enums.UnitTexture;
import com.me.empirebuilder.Players.Resources;
import com.me.empirebuilder.Tiles.Tile;

public abstract class Unit {
	protected Vector2 position;
	protected int hitpoints;
	protected List<Tile> testPath;
	protected boolean isSelected;
	protected UnitTexture texture;
	protected int movePoints;
	protected int movePointsRemaining;
	protected Resources unitCost;
	protected int upkeepGold, upkeepFood;
	
	public Unit(Vector2 position) {
		this.position = position;
		isSelected = false;
		texture = UnitTexture.SWORDSMAN;
		movePoints = 0;
		movePointsRemaining = 0;
		testPath = new ArrayList<Tile>();
		unitCost = new Resources(0, 0, 0, 0, 0, 0, 0);
		upkeepGold = 0;
		upkeepFood = 0;
		hitpoints = 0;
	}

	public void setNewPath(List<Tile> list) {
		this.testPath = list;
	}
	
	public List<Tile> getNewPath() {
		return testPath;
	}
	
	public void clearNewPath() {
		testPath.clear();
	}
	
	public int getMovePointsRemaining() {
		return movePointsRemaining;
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

	public Resources getCost() {
		return unitCost;
	}

	public void setCost(Resources cost) {
		this.unitCost = cost;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public UnitTexture getTexture() {
		return texture;
	}

	public void setTexture(UnitTexture texture) {
		this.texture = texture;
	}	
}
