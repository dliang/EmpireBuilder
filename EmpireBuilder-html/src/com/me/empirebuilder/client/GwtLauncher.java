package com.me.empirebuilder.client;

import com.me.empirebuilder.EmpireBuilder;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.google.gwt.core.client.Scheduler;

public class GwtLauncher extends GwtApplication {
	@Override
	public GwtApplicationConfiguration getConfig () {
		GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(1600, 900);
		return cfg;
	}

	@Override
	public ApplicationListener getApplicationListener () {
		return new EmpireBuilder();
	}
}