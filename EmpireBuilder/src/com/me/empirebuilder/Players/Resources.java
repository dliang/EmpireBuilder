package com.me.empirebuilder.Players;

public class Resources {

	public int food, wood, stone, gold, iron, coal, steel;

	public Resources(int food, int wood, int stone, int gold, int iron,	int coal, int steel) {
		this.food = food;
		this.wood = wood;
		this.stone = stone;
		this.gold = gold;
		this.iron = iron;
		this.coal = coal;
		this.steel = steel;
	}	
	
	public void addResources(Resources resources) {
		food += resources.food;
		wood += resources.wood;
		stone += resources.stone;
		gold += resources.gold;
		iron += resources.iron;
		coal += resources.coal;
		steel += resources.steel;
	}
	
	public void subResources(Resources resources) {
		food -= resources.food;
		wood -= resources.wood;
		stone -= resources.stone;
		gold -= resources.gold;
		iron -= resources.iron;
		coal -= resources.coal;
		steel -= resources.steel;
	}
	
	public String checkResources(Resources resources) {
		if (food - resources.food < 0) return "food";
		if (wood - resources.wood < 0) return "wood";
		if (stone - resources.stone < 0) return "stone";
		if (gold - resources.gold < 0) return "gold";
		if (iron - resources.iron < 0) return "iron";
		if (coal - resources.coal < 0) return "coal";
		if (steel - resources.steel < 0) return "steel";
		return "none";
	}
}
