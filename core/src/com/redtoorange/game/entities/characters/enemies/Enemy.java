package com.redtoorange.game.entities.characters.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.redtoorange.game.Global;
import com.redtoorange.game.components.input.EnemyInputComponent;
import com.redtoorange.game.components.physics.character.EnemyPhysicsComponent;
import com.redtoorange.game.components.rendering.SpriteComponent;
import com.redtoorange.game.engine.Engine;
import com.redtoorange.game.entities.characters.EntityCharacter;
import com.redtoorange.game.entities.characters.Player;
import com.redtoorange.game.systems.PhysicsSystem;

public class Enemy extends EntityCharacter {
	protected Player player;

	public Enemy( PhysicsSystem physicsSystem, Engine engine, Vector2 spawnPosition, Player player ) {
		this( physicsSystem, engine, spawnPosition );
		this.player = player;
	}

	public Enemy( PhysicsSystem physicsSystem, Engine engine, Vector2 spawnPosition ) {
		super( spawnPosition, engine, physicsSystem );

		spriteComponent = new SpriteComponent( loadEnemySprite( ), this );
		addComponent( spriteComponent );

		inputComponent = new EnemyInputComponent( this );
		addComponent( inputComponent );

		physicsComponent = new EnemyPhysicsComponent( physicsSystem, this );
		addComponent( physicsComponent );
	}

	public void setPlayer( Player player ) {
		this.player = player;
	}

	protected Sprite loadEnemySprite( ) {
		Texture temp = new Texture( "zombie.png" );

		Sprite sprite = new Sprite( temp );
		sprite.setPosition( position.x, position.y );
		sprite.setSize( 1f, 1f );
		sprite.setOriginCenter( );

		return sprite;
	}

	@Override
	public void takeDamage( int amount ) {
		super.takeDamage( amount );

		spriteComponent.setColor( ( ( float ) health / ( float ) maxHealth ), 0, 0, 1 );
	}

	@Override
	protected void die( ) {
		dispose( );
	}

	public Player getPlayer(){
		return player;
	}

	@Override
	public float getRotation( ) {
		return ((SpriteComponent)getComponent( SpriteComponent.class )).getRotation();
	}
}
