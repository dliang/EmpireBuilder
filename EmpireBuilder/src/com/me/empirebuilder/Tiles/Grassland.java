package com.me.empirebuilder.Tiles;

import com.badlogic.gdx.math.Vector2;
import com.me.empirebuilder.Enums.TileTexture;
import com.me.empirebuilder.Enums.TileType;

public class Grassland extends Tile {

	public Grassland(Vector2 position, float size) {
		super(position, size);
		type = TileType.LAND;
		texture = TileTexture.GRASSLAND;
	}

	@Override
	public void printName() {
		System.out.println("GRASSLAND SELECTED");
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}
	
	public int compareTo(Tile t) {
		if (this.position.equals(t.getPosition()))
			return 0;
		return -1;
	}
}
