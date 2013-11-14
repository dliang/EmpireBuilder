package com.me.empirebuilder.Players;

import com.badlogic.gdx.math.Vector2;

public class Human extends Player {

	public Human(String name, Resources resources, Vector2 start, int workers) {
		super(name, resources, start, workers);
	}

	@Override
	public void endTurn() {
		
	}

	@Override
	public boolean isHuman() {		
		return true;
	}

	/**
	 * Function for what happens at the start of a human player's turn:
	 * reset all the unit movement points.
	 * increment resources
	 * build times on building/units adjusted. 
	 */
	@Override
	public void startTurn() {
		resetUnitMovements();
		
	}

}
