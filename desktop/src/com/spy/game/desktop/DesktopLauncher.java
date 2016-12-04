package com.spy.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.spy.game.BaseStage;
import com.spy.game.Spy;
import com.spy.game.Stage1;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1024;
		config.height = 576;
		config.title = "SpyGame Test";
		new LwjglApplication(new BaseStage(), config);
	}
}
