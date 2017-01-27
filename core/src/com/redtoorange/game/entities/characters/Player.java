package com.redtoorange.game.entities.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.redtoorange.game.Global;
import com.redtoorange.game.components.PlayerGunComponent;
import com.redtoorange.game.components.input.InputComponent;
import com.redtoorange.game.components.input.PlayerInputComponent;
import com.redtoorange.game.components.physics.character.PlayerPhysicsComponent;
import com.redtoorange.game.components.rendering.SpriteComponent;
import com.redtoorange.game.engine.Engine;
import com.redtoorange.game.engine.PostLightingDraw;
import com.redtoorange.game.entities.powerups.GunType;
import com.redtoorange.game.entities.powerups.Inventory;
import com.redtoorange.game.screens.PlayScreen;
import com.redtoorange.game.systems.PhysicsSystem;

/**
 * Player.java - Generic player class.
 *
 * @author - Andrew M.
 * @version - 13/Jan/2017
 */
//TODO: optimize camera entityCharacter
public class Player extends EntityCharacter implements PostLightingDraw {
	private final OrthographicCamera camera;    //Reference to the main camera
	private Inventory ammo = new Inventory( );
	private Sprite crossHair;
//
//	private PlayerGunComponent gunComponent;
//	private PlayerInputComponent inputComponent;

	public Player( Engine engine, OrthographicCamera camera, PlayScreen playScreen, PhysicsSystem physicsSystem, Vector2 spawnPoint ) {
		super( spawnPoint, engine, physicsSystem );

		this.camera = camera;
		loadAssets( spawnPoint );

		//addComponent( spriteComponent );

		//inputComponent = new PlayerInputComponent( this, camera );
		//addComponent( inputComponent );
		addComponent( new PlayerInputComponent( this, camera ) );

		//physicsComponent = new PlayerPhysicsComponent( physicsSystem, this );
		//addComponent( physicsComponent );
		addComponent( new PlayerPhysicsComponent( physicsSystem, this ) );

		//gunComponent = new PlayerGunComponent( physicsSystem, engine, this, playScreen );
		//addComponent( gunComponent );
		addComponent( new PlayerGunComponent( physicsSystem, engine, this, playScreen ) );

	}

	protected void loadAssets( Vector2 spawnPoint ) {
		Texture temp = new Texture( "player.png" );

		Sprite sprite = new Sprite( temp );
		sprite.setPosition( spawnPoint.x, spawnPoint.y );
		sprite.setSize( 1f, 1f );
		sprite.setOriginCenter( );

		addComponent( new SpriteComponent( sprite, this ) );

		Texture cross = new Texture( "crosshair.png" );
		crossHair = new Sprite( cross );
		crossHair.setPosition( 0f, 0f );
		crossHair.setSize( 1f, 1f );
	}

	public void update( float deltaTime ) {
		super.update( deltaTime );

		PlayerInputComponent ic = getComponent(PlayerInputComponent.class);
		crossHair.setCenter( ic.getMousePosition().x, ic.getMousePosition().y );
	}

	@Override
	public void draw( SpriteBatch batch ) {
		super.draw( batch );
	}

	@Override
	public void postLightingDraw( SpriteBatch batch ) {
		crossHair.draw( batch );
	}

	@Override
	public void dispose( ) {
		super.dispose( );
	}

	@Override
	protected void die( ) {
		dispose( );
	}

	public void grabAmmo( GunType type, int amount ) {
		ammo.pickup( type, amount );
	}

	public void grabHealth( ) {

	}

	public Inventory getInventoy( ) {
		return ammo;
	}
}