package com.redtoorange.game.entities;

import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.redtoorange.game.components.rendering.MapRenderComponent;
import com.redtoorange.game.engine.Engine;


//TODO: Detect and populate player spawn points
//TODO: Add Monster spawners.
//TODO: Add Item Spawners.

/**
 * GameMap.java - Encapsulated TiledMap renderer. Scaling is handled automatically.  Two Arrays will be
 * pulled for object layers name: "walls", "playerspawn".  Should NOT be added to the engine.
 *
 * @author - Andrew M.
 * @version - 13/Jan/2017
 */
public class GameMap extends Entity {
	/**
	 * Parsed from the TMX map and scale by the map scale.  "walls" must be the layer name.
	 */
	public Array<Rectangle> walls = new Array<Rectangle>( );
	/**
	 * Parsed from the TMX map and scaled by the map scale.  "playerspawns" must be the layer name.
	 */
	public Array<Rectangle> playerSpawns = new Array<Rectangle>( );
	private TiledMap map;
	private float mapScale = 1f;

	/**
	 * Build a GameMap that will encapsulate a Tiled TMX Map.  Scaling is handled automatically.  Two Arrays will be
	 * pulled from the TMX Map for object layers name: "walls", "playerspawn".
	 *
	 * @param mapPath  the complete path from the Asset folder for the TMX file.
	 * @param batch    The SpriteBatch to embed into the MapRenderer.
	 * @param mapScale The amount to resize the entire map by.  1/16f if you want 16 map pixels to equal 1 game unit.
	 */
	public GameMap( String mapPath, Engine engine, SpriteBatch batch, OrthographicCamera camera, float mapScale ) {
		super( new Vector2( 0, 0 ), engine );
		this.mapScale = mapScale;

		TmxMapLoader mapLoader = new TmxMapLoader( new InternalFileHandleResolver( ) );
		map = mapLoader.load( mapPath );

		addComponent( new MapRenderComponent( this, map, mapScale, batch, camera ) );

		buildWalls( );
		buildPlayerSpawns( );
	}

	@Override
	public void dispose( ) {
		if ( map != null )
			map.dispose( );

		super.dispose( );
	}

	private void buildWalls( ) {
		Array<RectangleMapObject> wallMapObjects = map.getLayers( ).get( "walls" ).getObjects( ).getByType( RectangleMapObject.class );
		pullAndAddRectangles( wallMapObjects, walls );
	}

	private void buildPlayerSpawns( ) {
		Array<RectangleMapObject> playerspawnMapObjects = map.getLayers( ).get( "playerspawn" ).getObjects( ).getByType( RectangleMapObject.class );
		pullAndAddRectangles( playerspawnMapObjects, playerSpawns );
	}

	private void pullAndAddRectangles( Array<RectangleMapObject> source, Array<Rectangle> destination ) {
		for ( RectangleMapObject r : source ) {
			Rectangle rectangle = r.getRectangle( );

			Vector2 size = new Vector2( );
			Vector2 center = new Vector2( );

			rectangle.getSize( size );
			rectangle.getCenter( center );

			size.scl( mapScale );
			center.scl( mapScale );

			rectangle.setSize( size.x, size.y );
			rectangle.setCenter( center.x, center.y );

			destination.add( rectangle );
		}
	}
}