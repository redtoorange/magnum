package com.redtoorange.game.entities.characters;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.redtoorange.game.components.input.InputComponent;
import com.redtoorange.game.components.physics.PhysicsComponent;
import com.redtoorange.game.components.physics.character.CharacterPhysicsComponent;
import com.redtoorange.game.components.rendering.SpriteComponent;
import com.redtoorange.game.engine.Engine;
import com.redtoorange.game.entities.Entity;
import com.redtoorange.game.systems.PhysicsSystem;

public abstract class EntityCharacter extends Entity {
	protected CharacterPhysicsComponent physicsComponent = null;
	protected SpriteComponent spriteComponent = null;
	protected InputComponent inputComponent = null;

	protected PhysicsSystem physicsSystem;

	protected int maxHealth = 10;
	protected int health = maxHealth;

	public EntityCharacter( Vector2 position, Engine engine, PhysicsSystem physicsSystem ) {
		super( position, engine );
		this.physicsSystem = physicsSystem;
	}

	public Vector3 getPosition3D( ) {
		return new Vector3( position.x, position.y, 0 );
	}

//	public SpriteComponent getSpriteComponent( ) {
//		return spriteComponent;
//	}
//
//	public PhysicsComponent getPhysicsComponent( ) {
//		return physicsComponent;
//	}

	public float getRotation( ) {
		return spriteComponent.getRotation( );
	}

	protected void setRotation( float rotation ) {
		spriteComponent.setRotation( rotation );
	}

//	public InputComponent getInputComponent( ) {
//		return inputComponent;
//	}

	@Override
	public void dispose( ) {
		super.dispose( );
	}

	public void takeDamage( int amount ) {
		health -= amount;
		if ( health <= 0 ) {
			die( );
		}
	}

	protected abstract void die( );
}