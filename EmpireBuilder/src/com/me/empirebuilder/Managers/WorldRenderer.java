/*
 * World Renderer
 * Author: Dustin Liang  
 * Version: 0.0.1 Pre-Alpha
 * Created: August 6, 2013
 * Last Modified: August 6, 2013
 * Description: Loads in textures, renders the world
 * 
 */

package com.me.empirebuilder.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.me.empirebuilder.Buildings.Building;
import com.me.empirebuilder.Enums.GamePhase;
import com.me.empirebuilder.Players.Player;
import com.me.empirebuilder.Stages.BottomStage;
import com.me.empirebuilder.Stages.DebugStage;
import com.me.empirebuilder.Tiles.Tile;
import com.me.empirebuilder.Units.Unit;
import com.me.empirebuilder.Units.UnitGroup;

public class WorldRenderer {
	private GameWorld world;
	
	//-----------------------------------------------
	//	Constants
	//-----------------------------------------------
	public static final float SCALE = 20;
	public static final int MAP_SIZE = 25;
	
	//-----------------------------------------------
	//	Sprite Batches
	//-----------------------------------------------
	private SpriteBatch gameBatch;
	private SpriteBatch hudBatch;
	private SpriteBatch debugBatch;
	
	//-----------------------------------------------
	//	Textures
	//-----------------------------------------------
	private Texture grassTileTexture, mountainTileTexture;
	private Texture forestTileTexture, ironTileTexture;
	private Texture highlightTileTexture, highlightBlueTexture;
	
	private Texture houseTexture, farmTexture, lumberCampTexture, ironMineTexture, keepTexture;
	private Texture houseGhostTexture, farmGhostTexture, lumberCampGhostTexture, ironMineGhostTexture, keepGhostTexture;
	private Texture barracksTexture, archeryRangeTexture, stableTexture, wallTexture;
	
	private Texture swordsmanTexture;
	private Texture swordsmanGhostTexture;

	//-----------------------------------------------
	//	Cameras
	//-----------------------------------------------
	public OrthographicCamera camera;
	public OrthographicCamera hudCamera;
	
	//-----------------------------------------------
	//	Arrays
	//-----------------------------------------------
	private Array<Array<Tile>> tiles = new Array<Array<Tile>>();
	private Array<Building> buildings = new Array<Building>();
	private Array<Unit> units = new Array<Unit>();
	private Array<Tile> possiblePaths = new Array<Tile>();
	private Array<Player> players = new Array<Player>();
	
	//-----------------------------------------------
	//	Variables
	//-----------------------------------------------
	public static float MINI_MAP_HEIGHT;
	public static float MINI_MAP_WIDTH;
	
	public float width, height;
	public float cameraWidth, cameraHeight;
	public float mouseX, mouseY;
	
	public BottomStage botStage;
	public DebugStage debugStage;
	
	public GamePhase phase;
	
	private Tile tile;
	
	
	
	public WorldRenderer(GameWorld world) {
		this.world = world;
		world.setRenderer(this);
		tiles = world.getTiles();
		buildings = world.getBuildings();
		units = world.getUnits();
		players = world.getPlayers();
		possiblePaths = world.getPossibleTargets();
		
		phase = GamePhase.FREE_PHASE;
		
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		cameraWidth = width / 5;
		cameraHeight = height / 5;
		MINI_MAP_HEIGHT = height / 5;
		MINI_MAP_WIDTH = width / 5;
		
		//load in the two separate cameras
		camera = new OrthographicCamera();
		camera.setToOrtho(false, cameraWidth, cameraHeight);
		camera.update();		
		hudCamera = new OrthographicCamera();
		hudCamera.setToOrtho(false, width, height);
		hudCamera.zoom = SCALE;
		
		//move the camera to the the center of the map and zoom out:
		camera.position.set(500, 500, 0);
		camera.viewportHeight += (camera.viewportHeight / 25) * 20;
		camera.viewportWidth += (camera.viewportWidth / 25) * 20;
		
		//instantiate the two Sprite Batches
		gameBatch = new SpriteBatch();
		hudBatch = new SpriteBatch();
		debugBatch = new SpriteBatch();
		
		//load in all game textures here
		grassTileTexture = loadTexture("tiles/grassland.png", TextureFilter.Linear);
		mountainTileTexture = loadTexture("tiles/mountain.png", TextureFilter.Linear);
		
		houseTexture = loadTexture("buildings/house.png", TextureFilter.Linear);
		farmTexture = loadTexture("buildings/farm.png", TextureFilter.Linear);
		
		barracksTexture = loadTexture("buildings/barracks.png", TextureFilter.Linear);
		keepTexture = loadTexture("buildings/keep.png", TextureFilter.Linear);
		wallTexture = loadTexture("buildings/wall.png", TextureFilter.Linear);
		
		swordsmanTexture = loadTexture("units/swordsman.png", TextureFilter.Linear);
		
		highlightTileTexture = loadTexture("tiles/highlight.png", TextureFilter.Linear);
		highlightBlueTexture = loadTexture("tiles/blue_highlight.png", TextureFilter.Linear);
		houseGhostTexture = loadTexture("buildings/house_ghost.png", TextureFilter.Linear);
		swordsmanGhostTexture = loadTexture("units/swordsman_ghost.png", TextureFilter.Linear);
		
		//instantiate the stages (game HUD)
		botStage = new BottomStage(this, world);
		botStage.setStageDefault();
		debugStage = new DebugStage(this);
		debugStage.showDebug();
		
		//set the input handlers here
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(new GameInputHandler(this, world));
		multiplexer.addProcessor(botStage);
		multiplexer.addProcessor(debugStage);
		Gdx.input.setInputProcessor(multiplexer);
	}
	
	public Texture loadTexture(String filepath, TextureFilter filter) {
		Texture texture = new Texture(filepath);
		texture.setFilter(filter, filter);
		return texture;
		
	}
	
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		gameBatch.setProjectionMatrix(camera.combined);
		gameBatch.begin();
		
		//draw tiles
		for (Array<Tile> a : tiles) {
			for (Tile t : a) {
				switch(t.getTexture()) {
					case GRASSLAND:
						gameBatch.draw(grassTileTexture, t.getPosition().x * t.getSize(), t.getPosition().y * t.getSize(), t.getSize(), t.getSize());
						break;
					case MOUNTAIN:
						gameBatch.draw(mountainTileTexture, t.getPosition().x * t.getSize(), t.getPosition().y * t.getSize(), t.getSize(), t.getSize());
						break;
					default:
						break;
				}				
			}
		}
		for (Player p : players) {
			for (Building b : p.getBuildings()) {
				switch(b.getTexture()) {
					case HOUSE:
						gameBatch.draw(houseTexture, b.getPosition().x * b.getSize(), b.getPosition().y * b.getSize(), b.getSize(), b.getSize());
						break;
					case FARM:
						gameBatch.draw(farmTexture, b.getPosition().x * b.getSize(), b.getPosition().y * b.getSize(), b.getSize(), b.getSize());
						break;
					default:
						break;
				}
			}
			
			for (UnitGroup g : p.getUnitGroups()) {
			//	for (Unit u : g.getUnits()) {
					switch (g.getTexture()) {
						case SWORDSMAN:
							gameBatch.draw(swordsmanTexture, g.getPosition().x * 50, g.getPosition().y * 50, 50, 50);
							break;
						default:
							break;
					}
					
					if (g.isSelected()) {
						//draw unit paths
						for (Tile t : possiblePaths) {
							gameBatch.draw(highlightTileTexture, t.getPosition().x * t.getSize(), t.getPosition().y * t.getSize(), t.getSize(), t.getSize());
						}
						//draw target path
						for (Tile t : g.getPath()) {
							gameBatch.draw(highlightBlueTexture, t.getPosition().x * t.getSize(), t.getPosition().y * t.getSize(), t.getSize(), t.getSize());
						}
						//draw selected texture:
						switch (g.getTexture()) {
							case SWORDSMAN:
								gameBatch.draw(swordsmanGhostTexture, g.getPosition().x * 50, g.getPosition().y * 50, 50, 50);
								break;
							default:
								break;
						}
						
			//		}
				}
			}
		}
		

		//show the building where it will be placed. Follows the mouse per tile. 
		tile = getTile(mouseX, mouseY);
		switch (phase) {
			case HOUSE_PHASE:
				gameBatch.draw(houseTexture, tile.getPosition().x * tile.getSize(), tile.getPosition().y * tile.getSize(), 50, 50);
				break;
			case FARM_PHASE:
				gameBatch.draw(farmTexture, tile.getPosition().x * tile.getSize(), tile.getPosition().y * tile.getSize(), 50, 50);
				break;
			case SWORDSMAN_PHASE:
				gameBatch.draw(swordsmanTexture, tile.getPosition().x * tile.getSize(), tile.getPosition().y * tile.getSize(), 50, 50);
				break;
			case FREE_PHASE:
				break;
			default:
				break;
		}
		
//		//draw unit paths:
//		for (Tile t : possiblePaths) {
//			gameBatch.draw(highlightTileTexture, t.getPosition().x * t.getSize(), t.getPosition().y * t.getSize(), t.getSize(), t.getSize());
//		}
		
		//draw unit target pathing:
//		for (Unit u : units) {
//			if (u.isSelected()) {
////				for (Vector2 t : u.getPath()) {
////					gameBatch.draw(highlightBlueTexture, t.x * 50, t.y * 50, 50, 50);
////				}
//				for (Tile t : u.getNewPath()) {
//					gameBatch.draw(highlightBlueTexture, t.getPosition().x * t.getSize(), t.getPosition().y * t.getSize(), t.getSize(), t.getSize());
//				}
//			}
//		}
		
		gameBatch.end();
		
		//Game Debugging batch
		debugBatch.setProjectionMatrix(camera.combined);
		debugBatch.begin();
		for (Building b : buildings) {
			if (b.isSelected()) {
				switch(b.getTexture()) {
					case HOUSE:
						debugBatch.draw(houseGhostTexture, b.getPosition().x * b.getSize(), b.getPosition().y * b.getSize(), b.getSize(), b.getSize());
						break;
					case FARM:
						debugBatch.draw(farmTexture, b.getPosition().x * b.getSize(), b.getPosition().y * b.getSize(), b.getSize(), b.getSize());
						break;
					default:
						break;
				}
			}
		}
		
		for (Unit u : units) {
			if (u.isSelected()) {
				switch (u.getTexture()) {
					case SWORDSMAN:
						debugBatch.draw(swordsmanGhostTexture, u.getPosition().x * 50, u.getPosition().y * 50, 50, 50);
						break;
					default:
						break;
				}
			}
		}
		debugBatch.end();
		
		botStage.act(delta);
		debugStage.act(delta);
		hudBatch.setProjectionMatrix(hudCamera.combined);
		hudBatch.begin();
		botStage.draw();
		debugStage.draw();
		hudBatch.end();
		
		
	}
	
	public Tile getTile(float x, float y) {
		int tileX = (int) (x / 50);
		int tileY = (int) (y / 50);
		
		if (tileX < 0) tileX = 0;
		if (tileY < 0) tileY = 0;
		if (tileX >= MAP_SIZE) tileX = MAP_SIZE - 1;
		if (tileY >= MAP_SIZE) tileY = MAP_SIZE - 1;
		return tiles.get(tileX).get(tileY);
	}
	
	public Tile getTile(Vector2 position) {
		return tiles.get((int)position.x).get((int)position.y);
	}
	
	public void dispose() {
		gameBatch.dispose();
		hudBatch.dispose();
		botStage.dispose();
		
		grassTileTexture.dispose();
		houseTexture.dispose();
	}
}
