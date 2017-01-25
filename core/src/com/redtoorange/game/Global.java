package com.redtoorange.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;

/**
 * Global.java - Collection of global constants and common functions that don't belong in a class.
 *
 * @author - Andrew M.
 * @version - 13/Jan/2017
 */
//TODO: Create a different class for the functions?
public class Global {

	public static final short ENEMY = 		0x0001;
	public static final short PLAYER = 		0x0002;
	public static final short BULLET_LIVE = 0x0004;
	public static final short BULLET_DEAD = 0x0008;
	public static final short WALL = 		0x0010;
	public static final short AMMO = 		0x0020;
	public static final short LIGHT = 		0x0040;





	/**
	 * Bit signature for a failed Array operation.
	 */
	public static final int FAILURE = -2;
	/**
	 * Bit signature for an Array push when the pushed object is already present.
	 */
	public static final int PRESENT = -1;
	/**
	 * Bit signature for an Array operation that was successful.
	 */
	public static final int SUCCESS = 0;
	/**
	 * Should the game run in Debug mode.  Provides additional output and physics debug information.
	 */
    public static boolean DEBUG = false;

    public static int WINDOW_WIDTH = 1920;
    public static int WINDOW_HEIGHT = 1080;
    public static String WINDOW_TITLE = "Some cool game thing";

    public static float VIRTUAL_WIDTH = 12f;
    public static float VIRTUAL_HEIGHT = 10;

    public static short BULLET_COL_GROUP = -1;

	/**
	 * Call to the OpenGL JNI to set the clear color and clear the screen.
	 */
	public static void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

	/**
	 * Helper function to translate one Vector2 to look at another Vector2.
	 * @param source		The starting position of the Vector2, the one that will be rotated.
	 * @param destination	The point the source should be looking at.
	 * @return				The angle (in degrees) that the source needs to rotate for it to be pointing at the destination.
	 */
    public static float lookAt(Vector2 source, Vector2 destination) {
        return (float) (Math.toDegrees(Math.atan2((destination.y - source.y), (destination.x - source.x))));
    }
}
