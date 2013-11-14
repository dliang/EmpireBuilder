package com.me.empirebuilder.Players;

import com.badlogic.gdx.math.Vector2;

public class Computer extends Player {

	public Computer(String name, Resources resources, Vector2 start, int workers) {
		super(name, resources, start, workers);
	}

	@Override
	public void endTurn() {
	}

	@Override
	public boolean isHuman() {		
		return false;
	}

	@Override
	public void startTurn() {
		// TODO Auto-generated method stub
		
	}
}
