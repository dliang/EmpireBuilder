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
import java.util.Timer;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.me.empirebuilder.EmpireBuilder;
import com.me.empirebuilder.Buildings.Building;
import com.me.empirebuilder.Enums.TileType;
import com.me.empirebuilder.Players.Player;
import com.me.empirebuilder.Tasks.GameLoop;
import com.me.empirebuilder.Tiles.Grassland;
import com.me.empirebuilder.Tiles.Mountain;
import com.me.empirebuilder.Tiles.Tile;
import com.me.empirebuilder.Units.Unit;
import com.me.empirebuilder.Units.UnitGroup;

public class GameWorld {
	
	private EmpireBuilder game;
	private WorldRenderer renderer;
	private Runnable gameLoop;
	private Thread gameLoopThread;

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
	private Player currentPlayer;
	private boolean playerTurn;
		
	
	public GameWorld(EmpireBuilder game, Array<Player> players) {
		this.game = game;
		generateMap(renderer.MAP_SIZE);
		calculateAdjacentTiles();
		this.players = players;
		playerTurn = false;
		startNewGame();
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
	public void startNewGame() {
		
//		currentPlayer = players.get(0);
//		currentPlayer.printName();
		
		//create the gaming thread here: 
		gameLoop = new GameLoop(this);
		gameLoopThread = new Thread(gameLoop);
		gameLoopThread.start();
	}	
	
	/**
	 * get the current player
	 * @return Player
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	/**
	 * set the player's turn to be true is human, false if computer. 
	 * @param boolean
	 */
	public void setPlayerTurn(boolean bool) {
		playerTurn = bool;
	}
	
	public boolean getPlayerTurn() {
		return playerTurn;
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

	//deprecated******************
	public Array<Unit> getUnits() {
		return units;
	}

	public void addUnits(Unit unit, Tile tile) {
		tile.setHasUnit(true);
		this.units.add(unit);
	}
	//****************************
	
	public UnitGroup getUnitGroup(Tile tile) {
		for (Player p : players) {
			for (UnitGroup g : p.getUnitGroups()) {
				if (g.getPosition().equals(tile.getPosition())) {
					return g;
				}
			}
		}
		return null;
	}
	public Unit getUnit(Tile tile) {
//		for (Unit u : units) {
//			if (u.getPosition().equals(tile.getPosition())) {
//				return u;
//			}
//		}
		for (Player p : players) {
			for (Unit u : p.getUnits()) {
				if (u.getPosition().equals(tile.getPosition())) {
					return u;
				}
			}
		}
		return null;
	}
	
//	public Array<Unit> getUnitGroup(Tile tile) {
//		for (Player p : players) {
//			for (Array<Unit> a : p.getUnitGroups()) {
//				for (Unit u : a){
//					if (u.getPosition().equals(tile.getPosition())) {
//						return a;
//					}
//				}
//			}
//		}
//		return null;
//	}
	
	public void moveUnit(Unit u) {
		((GameLoop) gameLoop).moveUnit(u);
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
	
	/**
	 * Shows all the possible targeted tiles given the current tile, and number of moves. 
	 * @param currentTile
	 * @param moves
	 */
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
	
	/**
	 * Using Dijkstra's Algorithm, calculate the cost of each path starting at the current tile
	 * A single shortest path will be created
	 * @param currentTile
	 */
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
	
	/**
	 * returns the Queue generated as a List
	 * @param target
	 * @return
	 */
	public List<Tile> getShortestPathTo(Tile target) {
		List<Tile> path = new ArrayList<Tile>();
		for (Tile tile = target; tile != null; tile = tile.getPrevious()) {
			path.add(tile);
		}
		path.remove(path.size() - 1);
		Collections.reverse(path);
		return path;
	}
	
	/**
	 * remove all the possible tiles generated. 
	 */
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
	
	public GameLoop getGameLoop() {
		return (GameLoop)gameLoop;
	}
}
