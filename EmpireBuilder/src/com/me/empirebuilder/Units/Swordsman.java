package com.me.empirebuilder.Units;

import com.badlogic.gdx.math.Vector2;
import com.me.empirebuilder.Enums.UnitTexture;
import com.me.empirebuilder.Players.Resources;

public class Swordsman extends Unit{

	public Swordsman(Vector2 position) {
		super(position);
		name = "Swordsman";
		texture = UnitTexture.SWORDSMAN;
		movePoints = 2;
		movePointsRemaining = 2;
		unitCost = new Resources(300, 0, 0, 50, 10, 0, 0);
		upkeepGold = 10;
		upkeepFood = 20;
		hitpoints = 200;
	}

}
