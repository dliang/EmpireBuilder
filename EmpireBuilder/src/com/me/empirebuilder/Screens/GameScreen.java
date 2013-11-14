/*
 * Game Screen 
 * Author: Dustin Liang  
 * Version: 0.0.1 Pre-Alpha
 * Created: August 6, 2013
 * Last Modified: August 6, 2013
 */

package com.me.empirebuilder.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.me.empirebuilder.EmpireBuilder;
import com.me.empirebuilder.Managers.GameWorld;
import com.me.empirebuilder.Managers.WorldRenderer;
import com.me.empirebuilder.Players.Computer;
import com.me.empirebuilder.Players.Human;
import com.me.empirebuilder.Players.Player;
import com.me.empirebuilder.Players.Resources;

public class GameScreen implements Screen {

	EmpireBuilder game;
	GameWorld world;
	WorldRenderer renderer;
	
	public GameScreen(EmpireBuilder game) {
		this.game = game;
		Array<Player> players = new Array<Player>();
		players.add(new Human("HUMAN 1", new Resources(500, 500, 500, 200, 50, 50, 0), new Vector2(2, 2), 10));
		players.add(new Human("HUMAN 2", new Resources(500, 500, 500, 200, 50, 50, 0), new Vector2(2, 2), 10));
//		players.add(new Computer("COMPUTER 1", new Resources(500, 500, 500, 500, 500, 500, 500), new Vector2(10, 10), 10));
		world = new GameWorld(game, players);
		renderer = new WorldRenderer(world);
		
	}
	
	
	@Override
	public void render(float delta) {
		world.update();
		renderer.render(delta);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}
