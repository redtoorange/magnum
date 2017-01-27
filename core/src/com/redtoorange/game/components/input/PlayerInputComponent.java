package com.redtoorange.game.components.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.redtoorange.game.Global;
import com.redtoorange.game.entities.characters.EntityCharacter;
import com.redtoorange.game.entities.characters.Player;

/**
 * ${FILE_NAME}.java - Description
 *
 * @author
 * @version 27/Jan/2017
 */
public class PlayerInputComponent extends InputComponent{
	private Vector3 mousePosition = new Vector3( );
	private OrthographicCamera camera;

	public PlayerInputComponent( Player player, OrthographicCamera camera){
		super( player );
		this.camera = camera;
	}

	protected void updateDeltaInput( ) {
		deltaInput.set( 0, 0 );

		if ( Gdx.input.isKeyPressed( Input.Keys.ESCAPE ) )
			Gdx.app.exit( );

		if ( Gdx.input.isKeyPressed( Input.Keys.W ) )
			deltaInput.y = 1;
		if ( Gdx.input.isKeyPressed( Input.Keys.S ) )
			deltaInput.y = -1;
		if ( Gdx.input.isKeyPressed( Input.Keys.A ) )
			deltaInput.x = -1;
		if ( Gdx.input.isKeyPressed( Input.Keys.D ) )
			deltaInput.x = 1;
	}

	public void updateRotation(){
		float rotation = Global.lookAt( parent.getPosition(), new Vector2( mousePosition.x, mousePosition.y ) );
		sc.setRotation( rotation );
	}

	private void updateMousePosition(){
		mousePosition = camera.unproject( new Vector3( Gdx.input.getX( ), Gdx.input.getY( ), 0f ) );
	}

	public void update(float deltaTime){
		updateDeltaInput();
		updateMousePosition();
		updateRotation();
	}

	public Vector3 getMousePosition( ) {
		return mousePosition;
	}

	public Vector2 getDeltaInput(){
		return deltaInput;
	}

	@Override
	public void dispose( ) {

	}
}
