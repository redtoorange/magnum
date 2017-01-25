package com.redtoorange.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.redtoorange.game.Core;
import com.redtoorange.game.Global;

/**
 * DesktopLauncher.java - Basic Windows window handler.  Launches a new copy of Core. Program entry point.
 *
 * @author - Andrew M.
 * @version - 13/Jan/2017
 */
public class DesktopLauncher {

	public static void main( String[] arg ) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration( );

		config.title = Global.WINDOW_TITLE;

		config.width = Global.WINDOW_WIDTH;
		config.height = Global.WINDOW_HEIGHT;

		new LwjglApplication( new Core( ), config );
	}
}
