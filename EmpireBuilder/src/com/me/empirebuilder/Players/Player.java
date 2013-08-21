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
		
	public Player(Resources resources, Vector2 start, int workers) {
		this.resources = resources;
		this.startPosition = start;
		this.workers = workers;
		units = new Array<Unit>();
		buildings = new Array<Building>();
		resourceGen = new ResourceGeneration(0, 0, 0, 0, 0, 0, 0);
		resourceGen.goldGen = workers * 10;
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


	public Array<Building> getBuildings() {
		return buildings;
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


	public abstract void endTurn();
}
