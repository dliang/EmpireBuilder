package com.me.empirebuilder.Players;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.me.empirebuilder.Buildings.Building;
import com.me.empirebuilder.Units.Unit;

public abstract class Player {

	protected Resources resources;
	protected ResourceGeneration resourceGen;
	protected Vector2 startPosition;
	protected int workers;
	protected Array<Unit> units;
	protected Array<Building> buildings;
	protected int maxPopulation, currentPopulation;
	protected String name;
	protected Array<Array<Unit>> unitGroups;
		
	public Player(String name, Resources resources, Vector2 start, int workers) {
		this.name = name;
		this.resources = resources;
		this.startPosition = start;
		this.workers = workers;
		units = new Array<Unit>();
		buildings = new Array<Building>();
		resourceGen = new ResourceGeneration(0, 0, 0, 0, 0, 0, 0);
		resourceGen.goldGen = workers * 10;
		unitGroups = new Array<Array<Unit>>();
	}

	public void printName() {
		System.out.println(name);
	}
	public Resources getResources() {
		return resources;
	}

	public void setResources(Resources resources) {
		this.resources = resources;
	}


	public ResourceGeneration getResourceGen() {
		return resourceGen;
	}


	public void setResourceGen(ResourceGeneration resourceGen) {
		this.resourceGen = resourceGen;
	}


	public int getWorkers() {
		return workers;
	}


	public void setWorkers(int workers) {
		this.workers = workers;
	}


	public Array<Unit> getUnits() {
		return units;
	}


	public void setUnits(Array<Unit> units) {
		this.units = units;
	}

	public void addUnit(Unit unit) {
		this.units.add(unit);
		unitGroups.add(new Array<Unit>());
		unit.setGroupIndex(unitGroups.size - 1);
		unitGroups.get(unitGroups.size - 1).add(unit);
	}
	
	public Array<Array<Unit>> getUnitGroups() {
		return unitGroups;
	}
	
	public void addUnitToGroup(Unit targetUnit, Unit selectedUnit) {
		unitGroups.get(targetUnit.getGroupIndex()).add(selectedUnit);
		unitGroups.get(selectedUnit.getGroupIndex()).removeIndex(getUnitIndex(selectedUnit));
		selectedUnit.setGroupIndex(targetUnit.getGroupIndex());
	}
	
	public int getUnitIndex(Unit unit) {
		for(Array<Unit> a : unitGroups) {
			for (int j = 0; j < a.size; j++) {
				if (a.get(j) == unit) {
					return j;
				}
			}
		}
		return -1;
	}

	public Array<Building> getBuildings() {
		return buildings;
	}

	public void addBuilding(Building building) {
		this.buildings.add(building);
	}

	public void setBuildings(Array<Building> buildings) {
		this.buildings = buildings;
	}


	public int getMaxPopulation() {
		return maxPopulation;
	}


	public void setMaxPopulation(int maxPopulation) {
		this.maxPopulation = maxPopulation;
	}


	public int getCurrentPopulation() {
		return currentPopulation;
	}


	public void setCurrentPopulation(int currentPopulation) {
		this.currentPopulation = currentPopulation;
	}
		
	public void resetUnitMovements() {
		for (Unit u : units) {
			u.resetMovePointsRemaining();
		}
	}
	public abstract void endTurn();
	public abstract boolean isHuman();
	public abstract void startTurn();
}
