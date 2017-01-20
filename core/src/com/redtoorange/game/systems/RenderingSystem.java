package com.redtoorange.game.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.redtoorange.game.engine.Drawable;

//TODO: Stub class.  Need to implement the entire System.
//TODO: Z-Ordering Code.
/**
 * RenderingSystem.java - A system that will manage and render all Render Components registered with the engine.
 * Handles Z-Ordering of RenderComponents.
 * @author - Andrew M.
 * @version - 20/Jan/2017
 */
public class RenderingSystem extends System implements Drawable{
	public static final String TAG = RenderingSystem.class.getSimpleName();

	/**
	 * Draw all the RenderComponents to the screen.
	 * @param batch 	What SpriteBatch to use to draw all the Render Components.
	 *                 	The SpriteBatch should already have begun.
	 */
	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub
	}
}