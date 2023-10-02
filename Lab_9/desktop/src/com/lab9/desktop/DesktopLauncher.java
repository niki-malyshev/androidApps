package com.lab9.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lab9.MyGdxGame;
import com.lab9.lab9;

public class DesktopLauncher {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Lab_9";
		config.width = 720/2;
		config.height = 1200/2;

		new LwjglApplication(new MyGdxGame(), config);
	}
}
