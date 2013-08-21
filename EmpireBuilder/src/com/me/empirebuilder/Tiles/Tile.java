package com.me.empirebuilder.Tiles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.me.empirebuilder.Enums.TileTexture;
import com.me.empirebuilder.Enums.TileType;

public abstract class Tile implements Comparable {

	protected float size;
	protected Vector2 position;
	protected boolean inBounds;
	protected TileType type;
	protected TileTexture texture;
	protected boolean canBuild;
	protected boolean hasUnit;
	protected boolean hasBuilding;
	protected Array<Tile> adjacentTiles;
	protected String visited;
	protected int movementCost;
	protected int currentCost;
	protected Tile previousTile;
	
	
	public Tile(Vector2 position, float size) {
		this.size = size;
		this.position = position;
		type = TileType.LAND;
		texture = TileTexture.GRASSLAND;
		inBounds = false;
		canBuild = true;
		hasUnit = false;
		adjacentTiles = new Array<Tile>();
		visited = "white";
		movementCost = 1;
		currentCost = 999;
		previousTile = null;
	}
	
	public void setPreviousTile(Tile tile) {
		previousTile = tile;
	}
	
	public Tile getPrevious() {
		return previousTile;
	}
	public int getMovementCost() {
		return movementCost;
	}

	public void setMovementCost(int movementCost) {
		this.movementCost = movementCost;
	}

	public int getCurrentCost() {
		return currentCost;
	}

	public void setCurrentCost(int currentCost) {
		this.currentCost = currentCost;
	}

	public String isVisited() {
		return visited;
	}
	
	public void setVisited(String bool) {
		visited = bool;
	}
	
	public Array<Tile> getAdjacentTiles() {
		return adjacentTiles;
	}
	
	public void addAdjacentTile(Tile tile) {
		adjacentTiles.add(tile);
	}
	
	public void printAdjacentTiles() {
		for (Tile t : adjacentTiles) {
			System.out.println("(" + t.getPosition().x + " ," + t.getPosition().y + ")");
		}
	}
	public boolean hasBuilding() {
		return hasBuilding;
	}

	public void setHasBuilding(boolean hasBuilding) {
		this.hasBuilding = hasBuilding;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public boolean isInBounds() {
		return inBounds;
	}

	public void setInBounds(boolean inBounds) {
		this.inBounds = inBounds;
	}

	public TileType getType() {
		return type;
	}

	public void setType(TileType type) {
		this.type = type;
	}
	
	public TileTexture getTexture() {
		return texture;
	}
	
	public void setTexture(TileTexture texture) {
		this.texture = texture;
	}
	
	public void checkBounds(float x, float f) {
		if (x > (position.x * size) && x < (position.x * size) + size && f > position.y * size && f < (position.y * size) + size) {
			inBounds = true;
		} else {
			inBounds = false;
		}
	}
	
	public void setCanBuild(boolean bool) {
		canBuild = bool;
	}
	
	public boolean canBuild() {
		return canBuild;
	}
	
	public void setHasUnit(boolean bool) {
		hasUnit = bool;
	}
	
	public boolean hasUnit() {
		return hasUnit;
	}
	
	public int compareTo(Tile t) {
		if (position.equals(t.getPosition()))
			return 0;
		return -1;
	}
	
	public void printPosition() {
		System.out.println("(" + position.x + ", " + position.y + ") ");
	}
	
	public abstract void printName();
}
