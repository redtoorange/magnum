package com.redtoorange.game.systems.ui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import com.redtoorange.game.Global;
import com.redtoorange.game.systems.System;

/**
 * ${FILE_NAME}.java - Description
 *
 * @author
 * @version 23/Jan/2017
 */
public class GunUI extends System {
	private OrthographicCamera uiCamera;
	private Viewport uiViewport;
	private Table rootTable;
	private Stage uiStage;
	private Image currentImage;
	private TextureRegionDrawable regionDrawable = new TextureRegionDrawable( );

	public GunUI(){
		init();
	}

	public void update(float deltaTime){
		uiStage.act( deltaTime );
	}

	public void draw( SpriteBatch batch ){
		uiCamera.update();
		uiStage.draw();
	}

	private void init(){
		uiCamera = new OrthographicCamera( Global.WINDOW_WIDTH, Global.WINDOW_HEIGHT);
		uiViewport = new ExtendViewport(Global.WINDOW_WIDTH, Global.WINDOW_HEIGHT, uiCamera);

		uiStage = new Stage( uiViewport );
		rootTable = new Table( VisUI.getSkin() );
		uiStage.addActor( rootTable );
		rootTable.setFillParent( true );

		rootTable.add( "Ammo Count or Something" ).right().bottom().size( 100f, 100f ).expand();
		currentImage = new Image( regionDrawable );
		rootTable.add( currentImage ).bottom().right().size( 200, 200 );
	}

	public void resize(int width, int height) {
		uiViewport.update( width, height );
		uiCamera.update();
	}

	public void swapCurrentImage(TextureRegion region){
		regionDrawable.setRegion( region );
	}
}
