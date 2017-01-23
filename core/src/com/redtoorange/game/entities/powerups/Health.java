package com.redtoorange.game.entities.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.redtoorange.game.engine.Engine;
import com.redtoorange.game.systems.PhysicsSystem;

/**
 * ${FILE_NAME}.java - Description
 *
 * @author
 * @version 23/Jan/2017
 */
public class Health extends PowerUp {
	public Health( Vector2 position, Engine engine, PhysicsSystem physicsSystem ) {
		super( engine , position, new Texture( "" ), physicsSystem );
	}
}
