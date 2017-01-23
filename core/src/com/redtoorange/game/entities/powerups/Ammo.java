package com.redtoorange.game.entities.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.redtoorange.game.engine.Engine;
import com.redtoorange.game.entities.characters.EntityCharacter;
import com.redtoorange.game.entities.characters.Player;
import com.redtoorange.game.systems.PhysicsSystem;
import com.redtoorange.game.systems.SoundManager;

/**
 * ${FILE_NAME}.java - Description
 *
 * @author
 * @version 23/Jan/2017
 */
public class Ammo extends PowerUp{
	private GunType type = GunType.REVOLVER;
	private int amount;

	public Ammo( Vector2 position, Engine engine, PhysicsSystem physicsSystem ) {
		super( engine , position, new Texture( "weapons/revolver/bullets.png" ), physicsSystem );

		amount = MathUtils.random( 1, 3 );
	}

	@Override
	public void absorbed( EntityCharacter c ) {
		System.out.println( "Player pickedup " + amount + " " + type + " bullets."  );
		SoundManager.S.playSound( SoundManager.SoundType.AMMOPICKUP );
		((Player )c).grabAmmo( type, amount  );
		super.absorbed( c );
	}
}
