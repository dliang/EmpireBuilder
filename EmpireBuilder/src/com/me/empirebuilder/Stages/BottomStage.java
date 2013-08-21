package com.me.empirebuilder.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.me.empirebuilder.Enums.GamePhase;
import com.me.empirebuilder.Managers.WorldRenderer;

public class BottomStage extends Stage {

	private final static float BUTTON_SIZE = 75;
	
	private Image background;
	
	private Texture backgroundTexture;
	
	//buttons
	private TextureAtlas buttonAtlas;
	private Skin skin;
	private Button militaryButton, econButton, backButton;
	private Button barracksButton, wallsButton;
	private Button houseButton, farmButton;
	
	private WorldRenderer renderer;
	
	private float commandsX, commandsY;
	
	
	
	public BottomStage(WorldRenderer renderer) {
		super();
		this.renderer = renderer;
		commandsX = renderer.width - WorldRenderer.MINI_MAP_WIDTH;
		commandsY = renderer.MINI_MAP_HEIGHT + 50;
		
		background = addImage(backgroundTexture, "data/marble_background.png", TextureFilter.Linear, WorldRenderer.MINI_MAP_WIDTH, 0, renderer.width - WorldRenderer.MINI_MAP_WIDTH, WorldRenderer.MINI_MAP_HEIGHT);
		
		buttonAtlas = new TextureAtlas(Gdx.files.internal("data/simple.atlas"));
		skin = new Skin();
		skin.addRegions(buttonAtlas);
		
		econButton = createButton("economy up", "economy down");
		militaryButton = createButton("military up", "military down");
		backButton = createButton("back up", "back down");
		
		houseButton = createButton("house up", "house down");
		farmButton = createButton("farm up", "farm down");
		
		barracksButton = createButton("barracks up", "barracks down");
		wallsButton = createButton("walls up", "walls down");
		
		
		
		setDefaultButtons();	
		setEconomyButtons();
		setMilitaryButtons();
	}
	
	private Image addImage(Texture texture, String filepath, TextureFilter filter, float x, float y, float width, float height) {
		texture = renderer.loadTexture(filepath, filter);
		Image image = new Image(texture);
		image.setX(x);
		image.setY(y);
		image.setWidth(width);
		image.setHeight(height);
		return image;
	}
	
	private Button createButton(String up, String down) {
		Button button = new Button(skin.getDrawable(up), skin.getDrawable(down));
		return button;
	}
	
	private void setDefaultButtons() {
		//Economy Buildings Button (0, 0)
		econButton.setX(commandsX);
		econButton.setY(commandsY - BUTTON_SIZE);
		econButton.setWidth(BUTTON_SIZE);
		econButton.setHeight(BUTTON_SIZE);
		econButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				setStageBuild();
			}
		});
		
		//Military Buildings Button (0, 1)
		militaryButton.setX(commandsX + BUTTON_SIZE);
		militaryButton.setY(commandsY - BUTTON_SIZE);
		militaryButton.setWidth(BUTTON_SIZE);
		militaryButton.setHeight(BUTTON_SIZE);
		militaryButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				setStageMilitary();
			}
		});
	}
	
	private void setEconomyButtons() {
		//House Button (0, 0)
		houseButton.setX(commandsX);
		houseButton.setY(commandsY - BUTTON_SIZE);
		houseButton.setWidth(BUTTON_SIZE);
		houseButton.setHeight(BUTTON_SIZE);
		houseButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				renderer.phase = GamePhase.HOUSE_PHASE;
			}
		});
		
		//Farm Button (1, 0)
		farmButton.setX(commandsX);
		farmButton.setY(commandsY - BUTTON_SIZE * 2);
		farmButton.setWidth(BUTTON_SIZE);
		farmButton.setHeight(BUTTON_SIZE);
		farmButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				renderer.phase = GamePhase.FARM_PHASE;
			}
		});
		
		//Back Button (2, 3)
		backButton.setX(commandsX + BUTTON_SIZE * 3);
		backButton.setY(commandsY - BUTTON_SIZE * 3);
		backButton.setWidth(BUTTON_SIZE);
		backButton.setHeight(BUTTON_SIZE);
		backButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				setStageDefault();
			}
		});
	}
	
	private void setMilitaryButtons() {
		//Barracks button (0, 0)
		barracksButton.setX(commandsX);
		barracksButton.setY(commandsY - BUTTON_SIZE);
		barracksButton.setWidth(BUTTON_SIZE);
		barracksButton.setHeight(BUTTON_SIZE);
		barracksButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

			}
		});
		
		//Walls button (1, 0)
		wallsButton.setX(commandsX);
		wallsButton.setY(commandsY - BUTTON_SIZE * 2);
		wallsButton.setWidth(BUTTON_SIZE);
		wallsButton.setHeight(BUTTON_SIZE);
		wallsButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
			}
		});
	}
	
	public void setStageDefault() {
		this.clear();
		this.addActor(background);
		this.addActor(econButton);
		this.addActor(militaryButton);
	}
	
	public void setStageBuild() {
		this.clear();		
		this.addActor(background);
		this.addActor(houseButton);
		this.addActor(farmButton);
		this.addActor(backButton);
	}
	
	public void setStageMilitary() {
		this.clear();
		this.addActor(background);
		this.addActor(barracksButton);
		this.addActor(wallsButton);
		this.addActor(backButton);
	}
	
	public void setStageBuilding() {
		this.clear();
		this.addActor(background);
	}
}
