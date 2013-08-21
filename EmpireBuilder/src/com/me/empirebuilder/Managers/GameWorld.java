/*
 * Game World 
 * Author: Dustin Liang  
 * Version: 0.0.1 Pre-Alpha
 * Created: August 6, 2013
 * Last Modified: August 6, 2013
 * Description: Handles the primary game logic, combines all the event listeners together, main game loop here. 
 * 
 */

package com.me.empirebuilder.Managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.me.empirebuilder.EmpireBuilder;
import com.me.empirebuilder.Buildings.Building;
import com.me.empirebuilder.Enums.TileType;
import com.me.empirebuilder.Players.Player;
import com.me.empirebuilder.Tiles.Grassland;
import com.me.empirebuilder.Tiles.Mountain;
import com.me.empirebuilder.Tiles.Tile;
import com.me.empirebuilder.Units.Unit;

public class GameWorld {
	
	private EmpireBuilder game;
	private WorldRenderer renderer;

	//------------------------------------
	//	Arrays
	//------------------------------------
	private Array<Array<Tile>> tiles = new Array<Array<Tile>>();
	private Array<Building> buildings = new Array<Building>();
	private Array<Unit> units = new Array<Unit>();
	private Array<Tile> possiblePaths = new Array<Tile>();
	private Array<Vector2> targetPath = new Array<Vector2>();
	private Array<Array<Vector2>> possibleTargetPaths = new Array<Array<Vector2>>();

	private Array<Player> players = new Array<Player>();
		
	
	public GameWorld(EmpireBuilder game, Array<Player> players) {
		this.game = game;
		generateMap(renderer.MAP_SIZE);
		calculateAdjacentTiles();
		this.players = players;
	}
	
	
	private void generateMap(int size) {
		int random = 0;
		for (int i = 0; i < size; i++) {
			tiles.add(new Array<Tile>());
			for (int j = 0; j < size; j++) {
				random = MathUtils.random(0, 10);
				if (random == 5) {
					tiles.get(i).add(new Mountain(new Vector2(i, j), 50));
				} else {
					tiles.get(i).add(new Grassland(new Vector2(i, j), 50));
				}
				
			}
		}
	}
	
	public void update() {
		
	}
	
	public void setRenderer(WorldRenderer renderer) {
		this.renderer = renderer;
	}
	
	public WorldRenderer getRenderer() {
		return renderer;
	}
	
	public Array<Array<Tile>> getTiles() {
		return tiles;
	}
	
	public Tile getTile(Vector2 position) {
		return tiles.get((int)position.x).get((int)position.y);
	}
	
	public Array<Player> getPlayers() {
		return players;
	}
	
	public void addBuilding(Building building, Tile tile) {
		tile.setCanBuild(false);
		tile.setHasBuilding(true);
		buildings.add(building);
	}
	
	public Array<Building> getBuildings() {
		return buildings;
	}
	
	public Building getBuilding(Tile tile) {
		for (Building b : buildings) {
			if (b.getPosition().equals(tile.getPosition())) {
				return b;
			}
		}
		return null;
	}

	public Array<Unit> getUnits() {
		return units;
	}

	public void addUnits(Unit unit, Tile tile) {
		tile.setHasUnit(true);
		this.units.add(unit);
	}
	
	public Unit getUnit(Tile tile) {
		for (Unit u : units) {
			if (u.getPosition().equals(tile.getPosition())) {
				return u;
			}
		}
		return null;
	}
	
	private void calculateAdjacentTiles() {
		for (Array<Tile> a : tiles) {
			for (Tile t : a) {
				if (t.getPosition().x + 1 < renderer.MAP_SIZE) 
					t.addAdjacentTile(tiles.get((int)t.getPosition().x + 1).get((int)t.getPosition().y));
				if (t.getPosition().x - 1 >= 0) 
					t.addAdjacentTile(tiles.get((int)t.getPosition().x - 1).get((int)t.getPosition().y));
				if (t.getPosition().y + 1 < renderer.MAP_SIZE) 
					t.addAdjacentTile(tiles.get((int)t.getPosition().x).get((int)t.getPosition().y + 1));
				if (t.getPosition().y - 1 >= 0) 
					t.addAdjacentTile(tiles.get((int)t.getPosition().x).get((int)t.getPosition().y - 1));
			}
		}
	}
	
	public void resetTiles() {
		for (Array<Tile> a : tiles) {
			for (Tile t : a) {
				t.setCurrentCost(999);
				t.setPreviousTile(null);
			}
		}
	}
	
	public void calculatePossibleTargets(Tile currentTile, int moves) {
		if (moves >= 0) {
			switch (currentTile.getType()) {
				case MOUNTAIN:
					break;
				default:
					possiblePaths.add(currentTile);
					break;
			}
			if (moves == 0) {
				return;
			}
		}
		for (Tile t : currentTile.getAdjacentTiles()) {
			
			if (t.getType() == TileType.LAND || t.getType() == TileType.RESOURCE)
				calculatePossibleTargets(t, moves - 1);
		}
	}
	
	//Using Dijkstra's Algorithm, calculate the cost of each path starting at the current tile
	public void calculatePossiblePaths(Tile currentTile) {
		System.out.println("algorithm called");
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
	
	public List<Tile> getShortestPathTo(Tile target) {
		List<Tile> path = new ArrayList<Tile>();
		for (Tile tile = target; tile != null; tile = tile.getPrevious()) {
			path.add(tile);
		}
		Collections.reverse(path);
		return path;
	}
	
	public void clearPossibleTargets() {
		possiblePaths.clear();
	}
	
	public Array<Tile> getPossibleTargets() {
		return possiblePaths;
	}
	
	public void clearTargetPath() {
		targetPath.clear();
	}
	
	public void clearPossibleTargetPaths() {
		
		possibleTargetPaths.clear();
	}
	
	public Array<Vector2> getTargetPath() {
		return targetPath;
	}
}
