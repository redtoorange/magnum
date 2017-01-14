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
public class Global {
    public static int WINDOW_WIDTH = 1280;
    public static int WINDOW_HEIGHT = 1040;
    public static String WINDOW_TITLE = "Blue Steel";

    public static float VIRTUAL_WIDTH = 15f;
    public static float VIRTUAL_HEIGHT = 15f;

    public static short BULLET_COL_GROUP = -1;

    public static void clearScreen () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public static float lookAt(Vector2 source, Vector2 destination){
        double angleRad = Math.atan2( (destination.y - source.y), (destination.x - source.x) );
        return (float)((180f / Math.PI) * angleRad);
    }
}
