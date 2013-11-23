package com.me.empirebuilder.Tasks;

import java.util.Stack;

import com.badlogic.gdx.utils.Array;
import com.me.empirebuilder.Managers.GameWorld;
import com.me.empirebuilder.Players.Player;
import com.me.empirebuilder.Units.Unit;
import com.me.empirebuilder.Units.UnitGroup;

public class GameLoop implements Runnable {	

	private boolean isRunning;
	private Player currentPlayer;
	private int playerIter;
	private GameWorld world;
	private boolean human;
	private Array<Unit> moveUnits;
	private Array<UnitGroup> moveUnitGroups;
	private Stack<Integer> removeIndex;
	
	public GameLoop(GameWorld world) {
		this.world = world;
		isRunning = true;
		playerIter = 0;
		currentPlayer = world.getPlayers().get(playerIter);
		human = currentPlayer.isHuman();
		world.setPlayerTurn(human);
		moveUnits = new Array<Unit>();
		moveUnitGroups = new Array<UnitGroup>();
		removeIndex = new Stack<Integer>();
	}
	
	@Override
	public void run() {		
		while (isRunning) {
			
			//check to see if the current player is human
			if (human) {
				//starting the next turn when a human is done will happen in GameInputHandler.
				//at the start: reset all units move points.
				//increase perturn resources. 
				
				int i = 0;
				//move the units during turn here:
//				for (Unit u : moveUnits) {
////					//merge unit groups
////					if (world.getTile(u.getNewPath().get(0).getPosition()).hasUnit()) {
////						currentPlayer.addUnitToGroup(world.getUnit(world.getTile(u.getNewPath().get(0).getPosition())), u);
////						System.out.println("unit group merged");
////					}
//					world.getTile(u.getPosition()).setHasUnit(false);
//					u.move();
//					world.getTile(u.getPosition()).setHasUnit(true);
//					world.clearPossibleTargets();
//					world.calculatePossibleTargets(world.getTile(u.getPosition()), u.getMovePointsRemaining());
//
//					
//					if (u.getMovePointsRemaining() == 0) {
//						removeIndex.add(i);
//					}
//					i++;
//				}
//				while (!removeIndex.empty()) {
//					moveUnits.removeIndex(removeIndex.pop());
//				}
				for (UnitGroup g : moveUnitGroups) {
//					//merge unit groups
//					if (world.getTile(u.getNewPath().get(0).getPosition()).hasUnit()) {
//						currentPlayer.addUnitToGroup(world.getUnit(world.getTile(u.getNewPath().get(0).getPosition())), u);
//						System.out.println("unit group merged");
//					}
					world.getTile(g.getPosition()).setHasUnit(false);
					g.move();
					world.getTile(g.getPosition()).setHasUnit(true);
					world.clearPossibleTargets();
					world.calculatePossibleTargets(world.getTile(g.getPosition()), g.getMovePointsRemaining());

					
					if (g.getMovePointsRemaining() == 0) {
						removeIndex.add(i);
					}
					i++;
				}
				while (!removeIndex.empty()) {
					moveUnitGroups.removeIndex(removeIndex.pop());
				}
				
				//at the end of the turn, if there are units that have a set path but needs to move, move them here:
			} else {
				//computer player's turn. Will do their gaming logic, and then increment the iterator in world.
				//disable player controls during a computer's turn (building things, etc. ) 
				//units should still be able to be selected to see stats. 
				startNextPlayerTurn();
			}
		}
	}
	
	/**
	 * Start the turn for the next player in the list. Do so by incrementing the iterator, and setting the current player.
	 * 
	 */
	public void startNextPlayerTurn() {
		incrementIter();
		currentPlayer = world.getPlayers().get(playerIter);
		currentPlayer.printName();
		human = currentPlayer.isHuman();
		currentPlayer.startTurn();
		world.setPlayerTurn(human);
	}
	
	private void incrementIter() {
		playerIter++;
		if (playerIter >= world.getPlayers().size) {
			playerIter = 0;
		}
	}
	
	public void moveUnit(Unit u) {
		//add the unit to a list to be moved in run();
		moveUnits.add(u);
	}
	
	public void moveUnitGroup(UnitGroup g) {
		moveUnitGroups.add(g);
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public int getCurrentPlayerIndex() {
		return playerIter;
	}

}
