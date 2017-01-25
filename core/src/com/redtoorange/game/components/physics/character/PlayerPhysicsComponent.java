package com.redtoorange.game.components.physics.character;

import com.badlogic.gdx.physics.box2d.Filter;
import com.redtoorange.game.Global;
import com.redtoorange.game.entities.characters.Player;
import com.redtoorange.game.systems.PhysicsSystem;

/**
 * PlayerPhysicsComponent.java - DESCRIPTION
 *
 * @author - Andrew M.
 * @version - 14/Jan/2017
 */
public class PlayerPhysicsComponent extends CharacterPhysicsComponent {
	public PlayerPhysicsComponent( PhysicsSystem physicsSystem, Player player ) {
		super( physicsSystem, player, 5f, 10f, 10f,
				5f );
		Filter f = body.getFixtureList( ).first( ).getFilterData( );
		f.categoryBits = Global.PLAYER;
		f.maskBits = Global.WALL | Global.ENEMY | Global.AMMO;
		body.getFixtureList( ).first( ).setFilterData( f );
	}
}