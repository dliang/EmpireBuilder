/*
 * How many resources are being added each turn
 */
package com.me.empirebuilder.Players;

public class ResourceGeneration {
	public int foodGen, woodGen, stoneGen, goldGen, ironGen, coalGen, steelGen;

	public ResourceGeneration(int foodGen, int woodGen, int stoneGen, int goldGen, int ironGen, int coalGen, int steelGen) {
		this.foodGen = foodGen;
		this.woodGen = woodGen;
		this.stoneGen = stoneGen;
		this.goldGen = goldGen;
		this.ironGen = ironGen;
		this.coalGen = coalGen;
		this.steelGen = steelGen;
	}
	public void setFoodGen(int foodGen) {
		this.foodGen = foodGen;
	}
	public void setWoodGen(int woodGen) {
		this.woodGen = woodGen;
	}

	public void setStoneGen(int stoneGen) {
		this.stoneGen = stoneGen;
	}

	public void setGoldGen(int goldGen) {
		this.goldGen = goldGen;
	}

	public void setIronGen(int ironGen) {
		this.ironGen = ironGen;
	}

	public void setCoalGen(int coalGen) {
		this.coalGen = coalGen;
	}

	public void setSteelGen(int steelGen) {
		this.steelGen = steelGen;
	}
	
	
	
}
