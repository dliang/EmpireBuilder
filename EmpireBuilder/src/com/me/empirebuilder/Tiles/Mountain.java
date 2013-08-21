package com.me.empirebuilder.Tiles;

import com.badlogic.gdx.math.Vector2;
import com.me.empirebuilder.Enums.TileTexture;
import com.me.empirebuilder.Enums.TileType;

public class Mountain extends Tile implements Comparable{

	public Mountain(Vector2 position, float size) {
		super(position, size);
		type = TileType.MOUNTAIN;
		texture = TileTexture.MOUNTAIN;
		canBuild = false;
		movementCost = 99;
	}

	@Override
	public void printName() {
		System.out.println("MOUNTAIN SELECTED");
	}

	public int compareTo(Tile t) {
		if (this.position.equals(t.getPosition()))
			return 0;
		return -1;
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}
}
