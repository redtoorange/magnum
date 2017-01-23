package com.redtoorange.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;

/**
 * Global.java - Collection of global constants and common functions that don't belong in a class.
 *
 * @author - Andrew M.
 * @version - 13/Jan/2017
 */
//TODO: Create a different class for the functions?
public class Global {

	public static final short ENEMY = 1;
	public static final short PLAYER = 2;
	public static final short BULLET_LIVE = 3;
	public static final short BULLET_DEAD = 4;
	public static final short WALL = 5;




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
    public static boolean DEBUG = true;

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
