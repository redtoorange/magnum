package com.redtoorange.game.components.physics;

import com.redtoorange.game.entities.characters.enemies.Enemy;
import com.redtoorange.game.systems.PhysicsSystem;

/**
 * ${FILE_NAME}.java - Description
 *
 * @author
 * @version 20/Jan/2017
 */
public class EnemyPhysicsComponent extends CharacterPhysicsComponent{

	public EnemyPhysicsComponent( PhysicsSystem physicsSystem, Enemy enemy) {
		super(physicsSystem, enemy, 10f, 10f, 10f, 5f);
	}
}
