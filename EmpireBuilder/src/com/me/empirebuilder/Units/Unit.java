package com.me.empirebuilder.Units;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.me.empirebuilder.PlayerObject;
import com.me.empirebuilder.Enums.ObjectType;
import com.me.empirebuilder.Enums.UnitTexture;
import com.me.empirebuilder.Players.Resources;
import com.me.empirebuilder.Tiles.Tile;

public abstract class Unit extends PlayerObject {
	protected int hitpoints;
	protected List<Tile> testPath;
	protected boolean isSelected;
	protected UnitTexture texture;
	protected int movePoints;
	protected int movePointsRemaining;
	protected Resources unitCost;
	protected int upkeepGold, upkeepFood;
	protected String name;
	protected int groupIndex;
	
	public Unit(Vector2 position) {
		super(position);
		isSelected = false;
		texture = UnitTexture.SWORDSMAN;
		movePoints = 0;
		movePointsRemaining = 0;
		testPath = new ArrayList<Tile>();
		unitCost = new Resources(0, 0, 0, 0, 0, 0, 0);
		upkeepGold = 0;
		upkeepFood = 0;
		hitpoints = 0;
		name = "";
		groupIndex = 0;
		objectType = ObjectType.UNIT;
	}

	public String getName() {
		return name;
	}
	
	public void setGroupIndex(int i) {
		groupIndex = i;
	}
	
	public int getGroupIndex() {
		return groupIndex;
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
	
	public void decrementMovePointsRemaining(int m) {
		movePointsRemaining -= m;
	}
	
	public void resetMovePointsRemaining() {
		movePointsRemaining = movePoints;
	}
	
	public int getMovePoints() {
		return movePoints;
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
	
	public void move() {
		setPosition(testPath.get(0).getPosition());
		testPath.remove(0);
		decrementMovePointsRemaining(1);
	}
}
