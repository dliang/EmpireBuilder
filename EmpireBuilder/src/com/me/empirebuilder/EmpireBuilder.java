/*
 * Empire Builder Game
 * Author: Dustin Liang  
 * Version: 0.0.1 Pre-Alpha
 * Created: August 6, 2013
 * Last Modified: August 6, 2013
 */

package com.me.empirebuilder;

import com.badlogic.gdx.Game;
import com.me.empirebuilder.Screens.GameScreen;


public class EmpireBuilder extends Game {

	@Override
	public void create() {
		setScreen(new GameScreen(this));
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {	
		super.render();
//		log.log();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
