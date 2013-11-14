package com.me.empirebuilder.Units;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class UnitGroup {
	private Array<Unit> units;
	private Vector2 position;
	private boolean isSelected;
	
	public UnitGroup(Vector2 position, Unit unit) {
		this.position = position;
		units = new Array<Unit>();
		units.add(unit);
		isSelected = false;
	}

	public int getUnitIndex(Unit unit) {
		for (int j = 0; j < units.size; j++) {
			if (units.get(j) == unit) {
				return j;
			}
		}
		return -1;
	}
	
	public void setPosition(Vector2 pos) {
		position = pos;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public boolean isSelected() {
		return isSelected;
	}
	
	public void setSelected(boolean bool) {
		isSelected = bool;
	}
	
	public void mergeUnitGroup(UnitGroup group2) {
		
	}
	
	public void splitUnitGroup() {
		
	}
}
