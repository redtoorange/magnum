package com.redtoorange.game.components;

import com.badlogic.gdx.utils.Disposable;
import com.redtoorange.game.entities.Entity;

/**
 * Component.java - Metaphorical piece of an entity.
 *
 * @author - Andrew M.
 * @version - 20/Jan/2017
 */
public abstract class Component implements Disposable {
	protected Entity parent;

	public Component( Entity parent ) {
		this.parent = parent;
	}

	public Entity getParent( ) {
		return parent;
	}
}
