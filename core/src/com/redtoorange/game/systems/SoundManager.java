package com.redtoorange.game.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

/**
 * ${FILE_NAME}.java - Description
 *
 * @author
 * @version 23/Jan/2017
 */
public class SoundManager {
	public static final SoundManager S = new SoundManager();

	private Sound gunShot;
	private Sound ammoPickup;
	private Sound bulletHit;
	private Sound reloaded;
	private Sound nobullets;

	public SoundManager(){
		gunShot = Gdx.audio.newSound( new FileHandle( "sounds/gunshot.wav" ) );
		ammoPickup = Gdx.audio.newSound( new FileHandle( "sounds/ammopickup.wav" ) );
		bulletHit = Gdx.audio.newSound( new FileHandle( "sounds/bullethit.wav" ) );
		reloaded = Gdx.audio.newSound( new FileHandle( "sounds/reloaded.wav" ) );
		nobullets = Gdx.audio.newSound( new FileHandle( "sounds/nobullets.wav" ) );
	}

	public void playSound( SoundType type ){
		switch(type){
			case GUNSHOT:
				gunShot.play();
				break;
			case AMMOPICKUP:
				ammoPickup.play();
				break;
			case BULLETHIT:
				bulletHit.play();
				break;
			case RELOADED:
				reloaded.play();
				break;
			case NOBULLETS:
				nobullets.play();
				break;
		}
	}

	public enum SoundType{
		GUNSHOT, AMMOPICKUP, BULLETHIT, RELOADED, NOBULLETS
	}
}