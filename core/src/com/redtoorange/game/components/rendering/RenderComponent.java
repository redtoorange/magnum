package com.redtoorange.game.components.rendering;

import com.redtoorange.game.components.Component;
import com.redtoorange.game.engine.Drawable;
import com.redtoorange.game.entities.Entity;

/**
 * RenderComponent.java - DESCRIPTION
 *
 * @author - Andrew M.
 * @version - 20/Jan/2017
 */
public abstract class RenderComponent extends Component implements Drawable {
	public RenderComponent( Entity parent ) {
		super( parent );
	}
}