package com.redtoorange.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.redtoorange.game.screens.PlayScreen;

/**
 * Core.java - Core game class that handles the different screens.
 *
 * @author - Andrew M.
 * @version - 13/Jan/2017
 */
public class Core extends Game {
	private FPSLogger logger = new FPSLogger( );
	private PlayScreen playScreen;

	@Override
	public void create( ) {
		playScreen = new PlayScreen( this );
		setScreen( playScreen );
	}

	@Override
	public void setScreen( Screen screen ) {
		if ( this.screen != null )
			this.screen.dispose( );

		this.screen = screen;

		if ( this.screen != null )
			this.screen.show( );
	}

	@Override
	public void resize( int width, int height ) {
		screen.resize( width, height );
		super.resize( width, height );
	}

	@Override
	public void render( ) {
		if ( Global.DEBUG )
			logger.log( );

		update( );

		draw( );
	}

	public void update( ) {
		playScreen.update( Gdx.graphics.getDeltaTime( ) );
	}

	public void draw( ) {
		Global.clearScreen( );
		playScreen.draw( );
	}

	@Override
	public void dispose( ) {
		if ( Global.DEBUG )
			System.out.println( "Core disposed" );

		if ( screen != null )
			screen.dispose( );
	}
}
