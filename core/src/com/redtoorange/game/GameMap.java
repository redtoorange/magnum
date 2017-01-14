package com.redtoorange.game;

import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * GameMap.java - Encapsulated TiledMap renderer
 *
 * @author - Andrew M.
 * @version - 13/Jan/2017
 */

//TODO: Detect and populate player spawn points
//TODO: Add Monster spawners.
//TODO: Add Item Spawners.

public class GameMap implements Disposable {
    private TiledMapRenderer mapRenderer;
    private TiledMap map;
    private float mapScale = 1f;

    public Array<Rectangle> walls = new Array<Rectangle>();
    public Array<Rectangle> playerSpawns = new Array<Rectangle>();

    public GameMap(String mapPath, SpriteBatch batch, float mapScale) {
        this.mapScale = mapScale;

        TmxMapLoader mapLoader = new TmxMapLoader(new InternalFileHandleResolver());
        map = mapLoader.load(mapPath);

        mapRenderer = new OrthogonalTiledMapRenderer(map, mapScale, batch);

        buildWalls();
        buildPlayerSpawns();
    }

    public void render(Camera camera) {
        mapRenderer.setView((OrthographicCamera) camera);
        mapRenderer.render();
    }

    @Override
    public void dispose() {
        if(Global.DEBUG)
            System.out.println("GameMap disposed");

        if (map != null)
            map.dispose();
    }

    private void buildWalls() {
        Array<RectangleMapObject> wallMapObjects = map.getLayers().get("walls").getObjects().getByType(RectangleMapObject.class);
        pullAndAddRectangles(wallMapObjects, walls);
    }

    private void buildPlayerSpawns() {
        Array<RectangleMapObject> playerspawnMapObjects = map.getLayers().get("playerspawn").getObjects().getByType(RectangleMapObject.class);
        pullAndAddRectangles(playerspawnMapObjects, playerSpawns);
    }

    private void pullAndAddRectangles(Array<RectangleMapObject> source, Array<Rectangle> destination){
        for (RectangleMapObject r : source) {
            Rectangle rectangle = r.getRectangle();

            Vector2 size = new Vector2();
            Vector2 center = new Vector2();

            rectangle.getSize(size);
            rectangle.getCenter(center);

            size.scl(mapScale);
            center.scl(mapScale);

            rectangle.setSize(size.x, size.y);
            rectangle.setCenter(center.x, center.y);

            destination.add(rectangle);
        }
    }
}
