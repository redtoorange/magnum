package com.redtoorange.game.components.physics;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.redtoorange.game.Global;
import com.redtoorange.game.components.rendering.SpriteComponent;
import com.redtoorange.game.entities.Entity;
import com.redtoorange.game.factories.Box2DFactory;
import com.redtoorange.game.systems.PhysicsSystem;

/**
 * ${FILE_NAME}.java - Description
 *
 * @author
 * @version 23/Jan/2017
 */
public class PowerUpPhysicsComponent extends PhysicsComponent {
	public PowerUpPhysicsComponent( PhysicsSystem physicsSystem, Entity parent, SpriteComponent sc ) {
		super( physicsSystem, parent );

		initPhysics( sc );
	}

	private void initPhysics( SpriteComponent sc ) {
		body = Box2DFactory.createBody( physicsSystem, sc.getBoundingBox( ), BodyDef.BodyType.DynamicBody, 10f, 0, 0, true, false );

		body.setUserData( parent );

		Filter f = body.getFixtureList( ).first( ).getFilterData( );
		f.categoryBits = Global.AMMO;
		body.getFixtureList( ).first( ).setFilterData( f );

		body.setTransform( sc.getCenter( ), ( float ) Math.toRadians( sc.getRotation( ) ) );
	}

	@Override
	public void dispose( ) {
		destroy( );
	}
}
