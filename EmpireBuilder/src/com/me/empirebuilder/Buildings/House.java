package com.me.empirebuilder.Buildings;

import com.badlogic.gdx.math.Vector2;
import com.me.empirebuilder.Enums.BuildingTexture;

public class House extends Building {

	public House(Vector2 position, int size, int hitpoints, int cost) {
		super(position, size, hitpoints, cost);
		texture = BuildingTexture.HOUSE;
	}

	@Override
	public void printName() {
		System.out.println("HOUSE SELECTED");
	}

}
