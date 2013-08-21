package com.me.empirebuilder.Buildings;

import com.badlogic.gdx.math.Vector2;
import com.me.empirebuilder.Enums.BuildingTexture;

public class Farm extends Building {

	public Farm(Vector2 position, int size, int hitpoints, int cost) {
		super(position, size, hitpoints, cost);
		texture = BuildingTexture.FARM;
	}

	@Override
	public void printName() {
		System.out.println("FARM SELECTED");
	}

}
