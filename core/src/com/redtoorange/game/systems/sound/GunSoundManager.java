package com.redtoorange.game.systems.sound;

/**
 * GunSoundManager.java - DESCRIPTION
 *
 * @author - Andrew M.
 * @version - 23/Jan/2017
 */
public class GunSoundManager extends SoundManager {
	public GunSoundManager( ) {
		super( );

		sounds.put( "gunshot", new SoundEffect( "sounds/gunshot.wav", 0.15f ) );
		sounds.put( "bullethit", new SoundEffect( "sounds/bullethit.wav", 0.15f ) );
		sounds.put( "reloaded", new SoundEffect( "sounds/reloaded.wav", 2f ) );
		sounds.put( "nobullets", new SoundEffect( "sounds/nobullets.wav", 0.25f ) );
	}
}
