package com.redtoorange.game.components.rendering;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.redtoorange.game.engine.Drawable;
import com.redtoorange.game.entities.GameMap;

/**
 * MapRenderComponent.java - Description
 *
 * @author	Andrew J. McGuiness
 * @version 20/Jan/2017
 */
public class MapRenderComponent extends RenderComponent {
	private TiledMapRenderer mapRenderer;

	private GameMap gameMap;
	private OrthographicCamera camera;


	public MapRenderComponent(GameMap gameMap, TiledMap map, float mapScale, SpriteBatch batch, OrthographicCamera camera){
		super(gameMap);

		this.gameMap = gameMap;
		this.camera = camera;

		mapRenderer = new OrthogonalTiledMapRenderer(map, mapScale, batch);
	}

	@Override
	public void draw( SpriteBatch batch ) {
		if(batch.isDrawing())
			batch.end();

		mapRenderer.setView(camera);
		mapRenderer.render();

		if(!batch.isDrawing())
			batch.begin();
	}

	@Override
	public void dispose( ) {
		//Nothing to dispose here
	}
}