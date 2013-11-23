package com.me.empirebuilder.Managers;


import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.me.empirebuilder.Buildings.Building;
import com.me.empirebuilder.Buildings.Farm;
import com.me.empirebuilder.Buildings.House;
import com.me.empirebuilder.Enums.GamePhase;
import com.me.empirebuilder.Players.Player;
import com.me.empirebuilder.Tasks.GameLoop;
import com.me.empirebuilder.Tiles.Tile;
import com.me.empirebuilder.Units.Swordsman;
import com.me.empirebuilder.Units.Unit;
import com.me.empirebuilder.Units.UnitGroup;

public class GameInputHandler implements InputProcessor {

	WorldRenderer renderer;
	GameWorld world;
	private Vector2 last;
	private Vector2 lastTouch = new Vector2();
	private Tile tile, prevTile;
	private Building building;
	private Unit unit;
	private UnitGroup unitGroup;
	private boolean middleDown = false;
	private boolean firstRight = true;
	private Player selectedUnitOfPlayer;
	
	public float camNonOriginX, camNonOriginY;
	
	public GameInputHandler(WorldRenderer renderer, GameWorld world) {
		this.renderer = renderer;
		this.world = world;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
			case Keys.MINUS:
				renderer.camera.viewportWidth += renderer.camera.viewportWidth / 5;
				renderer.camera.viewportHeight += renderer.camera.viewportHeight / 5;
				break;
			case Keys.EQUALS:				
				renderer.camera.viewportWidth -= renderer.camera.viewportWidth / 5;
				renderer.camera.viewportHeight -= renderer.camera.viewportHeight / 5;
				break;
			default:
				break;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode) {
			case Keys.ESCAPE:
				renderer.botStage.setStageDefault();
				renderer.phase = GamePhase.FREE_PHASE;
				deselectAll();
				break;		
			default:
				break;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		switch(button) {
			case Input.Buttons.MIDDLE:
				middleDown = true;
				break;
			default:
				break;
		}
		lastTouch.set(screenX, renderer.height - screenY);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		prevTile = tile;
		tile = renderer.getTile(renderer.mouseX, renderer.mouseY);
		
		switch(button) {
			case Input.Buttons.LEFT:
				switch(renderer.phase) {
					case FREE_PHASE:
						if (tile.hasUnit()) {
							deselectAll();
							unitGroup = world.getUnitGroup(tile);
							world.calculatePossibleTargets(tile, unitGroup.getMovePointsRemaining());
							unitGroup.setSelected(true);
							//get the unit at a current tile:
							//cycle through all the players, and find the unit at that tile:
							for (Player p : world.getPlayers()) {
								for (UnitGroup g : p.getUnitGroups()) {
									if (g.getPosition() == tile.getPosition()) {
										System.out.println("PROPERTY OF PLAYER " + p.getName());
										selectedUnitOfPlayer = p;
										break;
									}
								}
							}
						} else if (tile.hasBuilding()) {
							deselectAll();
							building = world.getBuilding(tile);
							building.printName();
							building.setSelected(true);
														
						} else {
							deselectAll();
//							tile.printName();
//							tile.printAdjacentTiles();
						}
						break;
					case HOUSE_PHASE:
						if (!tile.canBuild()) {
							System.out.println("cannot build here");
							break;
						}
						world.addBuilding(new House(tile.getPosition(), 50, 1000, 100), tile);
						renderer.phase = GamePhase.FREE_PHASE;
						break;
					case FARM_PHASE:
						if (!tile.canBuild()) {
							System.out.println("cannot build here");
							break;
						}
						world.addBuilding(new Farm(tile.getPosition(), 50, 1000, 100), tile);
						renderer.phase = GamePhase.FREE_PHASE;
						break;
					case SWORDSMAN_PHASE:
//						world.addUnits(new Swordsman(tile.getPosition()), tile);
						world.getPlayers().get(world.getGameLoop().getCurrentPlayerIndex()).addUnit(new Swordsman(tile.getPosition()));
						tile.setHasUnit(true);
						renderer.phase = GamePhase.FREE_PHASE;
						break;						
					default:
						break;
				}
				break;
			case Input.Buttons.RIGHT:
				renderer.phase = GamePhase.FREE_PHASE;
				if (building != null) building.setSelected(false);
				if (unitGroup != null && unitGroup.isSelected()) {
					//cannot command unit if the unit does not belong to the current player:
					if (selectedUnitOfPlayer != world.getGameLoop().getCurrentPlayer()) {
						System.out.println("cannot command unit");
						break;
					}
					//check to see if it's the first right click at that same tile for the unit. If it isn't, then move the unit. 
					if (prevTile == null || prevTile.getPosition() != tile.getPosition()) {
						firstRight = true;
						if (unitGroup.getPath().size() > 0) {
							if (unitGroup.getPath().get(unitGroup.getPath().size() - 1).getPosition() == tile.getPosition()) {
								firstRight = false;
							}
						}
					} else if (prevTile.getPosition() == tile.getPosition()) {
						firstRight = false;
					}
					if (firstRight) {
//						System.out.println(world.getTile(unit.getPosition()).getPosition().x + " --> " + tile.getPosition().x);
						unitGroup.createShortestPath(world.getTile(unitGroup.getPosition()), tile);
						
//						for (Tile t : unit.getNewPath()) {
//							System.out.println(t.getPosition().x + ", " + t.getPosition().y);
//						}
					} else {
						//move the unit here during the same turn
						if (unitGroup.getMovePointsRemaining() > 0)
							world.getGameLoop().moveUnitGroup(unitGroup);
						System.out.println("not the first right click at the same location");
					}
				}
				break;
			case Input.Buttons.MIDDLE:
				middleDown = false;
				break;
			default:
				break;
		}
		
		return false;
	}
	
	private void deselectAll() {
		if (building != null) building.setSelected(false);
		if (unit != null) {
			unit.setSelected(false);
			world.resetTiles();
			world.clearPossibleTargets();
		}
		if (unitGroup != null) {
			unitGroup.setSelected(false);
			world.resetTiles();
			world.clearPossibleTargets();
		}
		
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (middleDown) {
			if ((renderer.height - screenY) > renderer.MINI_MAP_HEIGHT) {
				if ((renderer.height - screenY) > (renderer.height / renderer.SCALE))
					moveCamera(screenX, screenY);
			}
		}
		
		return false;
	}
	
	private void moveCamera(int touchX, int touchY) {
//		float x = renderer.camera.position.x + (touchX / renderer.camera.viewportWidth);
//		float y = renderer.camera.position.y + ((renderer.height - touchY) / renderer.camera.viewportHeight);
		Vector2 temp = new Vector2(touchX, renderer.height - touchY);
	//	temp = temp.sub(lastTouch).nor().mul((renderer.camera.viewportWidth / touchX) * 3f);
		renderer.camera.position.set(renderer.camera.position.x - (renderer.camera.viewportWidth / renderer.cameraWidth) * (temp.x - lastTouch.x), 
									renderer.camera.position.y - (renderer.camera.viewportHeight / renderer.cameraHeight) * (temp.y - lastTouch.y), 0);
		System.out.println(renderer.camera.viewportWidth + ", " + renderer.cameraWidth);
		
		lastTouch.set(touchX, renderer.height - touchY);
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		
		camNonOriginX = renderer.camera.position.x - renderer.camera.viewportWidth / 2;
		camNonOriginY = renderer.camera.position.y - renderer.camera.viewportHeight / 2;
		
		//zoom scaled to the original coords
		float x = (renderer.camera.viewportWidth / renderer.width) * screenX;
		float y = (renderer.camera.viewportHeight / renderer.height) * (renderer.height - screenY);
		//translate scale it next:
		x = x + camNonOriginX;
		y = y + camNonOriginY;
		
		renderer.mouseX = x;
		renderer.mouseY = y;
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		switch(amount) {
			case -1:
				renderer.camera.viewportWidth -= renderer.camera.viewportWidth / 25;
				renderer.camera.viewportHeight -= renderer.camera.viewportHeight / 25;
				break;
			case 1:
				renderer.camera.viewportWidth += renderer.camera.viewportWidth / 25;
				renderer.camera.viewportHeight += renderer.camera.viewportHeight / 25;
				break;
		}
	return false;
	}
	
}
