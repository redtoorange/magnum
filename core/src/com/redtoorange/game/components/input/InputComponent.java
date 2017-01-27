package com.redtoorange.game.components.input;

import com.badlogic.gdx.math.Vector2;
import com.redtoorange.game.components.Component;
import com.redtoorange.game.components.rendering.SpriteComponent;
import com.redtoorange.game.engine.Updateable;
import com.redtoorange.game.entities.Entity;
import com.redtoorange.game.entities.characters.EntityCharacter;

/**
 * ${FILE_NAME}.java - Description
 *
 * @author
 * @version 27/Jan/2017
 */
public abstract class InputComponent extends Component implements Updateable{
	protected Vector2 deltaInput = new Vector2(  );
	protected SpriteComponent sc;

	public InputComponent( EntityCharacter parent ) {
		super( parent );
		sc = parent.getComponent( SpriteComponent.class );
	}

	public Vector2 getDeltaInput(){
		return deltaInput;
	}
}
