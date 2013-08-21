package com.me.empirebuilder.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.me.empirebuilder.Enums.GamePhase;
import com.me.empirebuilder.Managers.WorldRenderer;

public class DebugStage extends Stage {

	private Skin skin;
	private TextureAtlas buttonAtlas;
	private Button createUnitButton;
	private WorldRenderer renderer;
	
	public DebugStage(WorldRenderer renderer) {
		super();
		
		this.renderer = renderer;
		
		buttonAtlas = new TextureAtlas(Gdx.files.internal("data/debug_pack.atlas"));
		skin = new Skin();
		skin.addRegions(buttonAtlas);
		
		createUnitButton = createButton("swordsman up", "swordsman down");
		
		setButtons();
	}
	
	private Button createButton(String up, String down) {
		Button button = new Button(skin.getDrawable(up), skin.getDrawable(down));
		return button;
	}
	
	private void setButtons() {
		createUnitButton.setX(0);
		createUnitButton.setY(800);
		createUnitButton.setWidth(75);
		createUnitButton.setHeight(75);
		createUnitButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				renderer.phase = GamePhase.SWORDSMAN_PHASE;
			}
		});
	}
	
	public void showDebug() {
		this.clear();
		this.addActor(createUnitButton);
	}
	
	public void hideDebug() {
		this.clear();
	}
}
