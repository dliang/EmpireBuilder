package com.me.empirebuilder.Units;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.me.empirebuilder.Enums.UnitTexture;
import com.me.empirebuilder.Tiles.Tile;

public class UnitGroup {
	private Array<Unit> units;
	private Vector2 position;
	private boolean isSelected;
	private int movePoints;
	private int movePointsRemaining;
	private UnitTexture texture;
	private List<Tile> movePath;
	
	public UnitGroup(Vector2 position, Unit unit) {
		this.position = position;
		units = new Array<Unit>();
		units.add(unit);
		isSelected = false;
		movePoints = unit.getMovePoints();
		movePointsRemaining = unit.getMovePointsRemaining();
		movePath = new ArrayList<Tile>();
		this.texture = UnitTexture.SWORDSMAN;
	}

	public int getUnitIndex(Unit unit) {
		for (int j = 0; j < units.size; j++) {
			if (units.get(j) == unit) {
				return j;
			}
		}
		return -1;
	}
	
	/**
	 * Recalculate the max move points that the entire group can do. (determined by which unit in the group can move the least). 
	 */
	public void recalculateMovePoints() {
		int minimum = 0;
		for (Unit u : units) {
			if (minimum == 0) 
				minimum = u.getMovePoints();
			if (u.getMovePoints() < minimum) 
				minimum = u.getMovePoints();
		}
	}
	
	public void setNewPath(List<Tile> list) {
		movePath = list;
	}
	
	public List<Tile> getPath() {
		return movePath;
	}
	
	public void clearPath() {
		movePath.clear();
	}
	
	public Array<Unit> getUnits() {
		return units;
	}
	
	public void setPosition(Vector2 pos) {
		position = pos;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public boolean isSelected() {
		return isSelected;
	}
	
	public void setSelected(boolean bool) {
		isSelected = bool;
	}
	
	public void mergeUnitGroup(UnitGroup group2) {
		
	}
	
	public void splitUnitGroup() {
		
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
	
	
	private void calculatePossiblePaths(Tile currentTile) {
		System.out.println("algorithm called!");
		currentTile.setCurrentCost(0);
		PriorityQueue<Tile> tileQueue = new PriorityQueue<Tile>();
		tileQueue.add(currentTile);
		
		while (!tileQueue.isEmpty()) {
			Tile tile = tileQueue.poll();
			for (Tile t : tile.getAdjacentTiles()) {
				int weight = t.getMovementCost();
				int distanceThrough = tile.getCurrentCost() + weight;
				if (distanceThrough < t.getCurrentCost()) {
					tileQueue.remove(t);
					t.setCurrentCost(distanceThrough);
					t.setPreviousTile(tile);
//					t.printPosition();
					tileQueue.add(t);
				}
			}
		}
	}
	
	public void createShortestPath(Tile current, Tile target) {
		calculatePossiblePaths(current);
		List<Tile> path = new ArrayList<Tile>();
		for (Tile tile = target; tile != null; tile = tile.getPrevious()) {
			path.add(tile);
		}
		path.remove(path.size() - 1);
		Collections.reverse(path);
		movePath = new ArrayList<Tile>(path);
		for (Tile t : movePath) {
			System.out.println(t.getPosition().x);
		}
	}
	
	public void move() {
		setPosition(movePath.get(0).getPosition());
		movePath.remove(0);
		decrementMovePointsRemaining(1);
	}
	
	public UnitTexture getTexture() {
		return texture;
	}

	public void setTexture(UnitTexture texture) {
		this.texture = texture;
	}	
}
