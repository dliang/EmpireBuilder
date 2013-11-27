package com.me.empirebuilder;

import com.badlogic.gdx.math.Vector2;
import com.me.empirebuilder.Enums.ObjectType;

public abstract class PlayerObject {
	
	protected Vector2 position;
	protected ObjectType objectType;
	
	public PlayerObject(Vector2 position) {
		this.position = position;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public ObjectType getType() {
		return objectType;
	}
}
