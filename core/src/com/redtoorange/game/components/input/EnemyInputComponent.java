package com.redtoorange.game.components.input;

import com.redtoorange.game.Global;
import com.redtoorange.game.entities.Entity;
import com.redtoorange.game.entities.characters.EntityCharacter;
import com.redtoorange.game.entities.characters.enemies.Enemy;

/**
 * ${FILE_NAME}.java - Description
 *
 * @author
 * @version 27/Jan/2017
 */
public class EnemyInputComponent extends InputComponent{

	private float rotation;
	public EnemyInputComponent( EntityCharacter parent ) {
		super( parent );
	}

	@Override
	public void update( float deltaTime ) {
		rotateToFacePlayer();
		calculateDeltaInput();
	}

	protected void rotateToFacePlayer( ) {
		rotation = Global.lookAt( parent.getPosition(), (( Enemy)parent).getPlayer().getPosition( ) );
		sc.setRotation( rotation );
	}

	protected void calculateDeltaInput( ) {
		deltaInput.set( 1, 0 );
		deltaInput.rotate( rotation );
	}

	@Override
	public void dispose( ) {

	}
}
