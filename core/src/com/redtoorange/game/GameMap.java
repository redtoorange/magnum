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

    public Array<Rectangle> walls = new Array<Rectangle>();
    public Array<Rectangle> playerSpawns = new Array<Rectangle>();

    public GameMap(String mapPath, SpriteBatch batch) {
        TmxMapLoader mapLoader = new TmxMapLoader(new InternalFileHandleResolver());
        map = mapLoader.load(mapPath);

        mapRenderer = new OrthogonalTiledMapRenderer(map, 1f / 16f, batch);

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
        Array<RectangleMapObject> rects = map.getLayers().get("walls").getObjects().getByType(RectangleMapObject.class);
        for (RectangleMapObject r : rects) {
            Rectangle rectangle = r.getRectangle();

            Vector2 size = new Vector2();
            Vector2 center = new Vector2();

            rectangle.getSize(size);
            rectangle.getCenter(center);

            size.scl(1 / 16f);
            center.scl(1 / 16f);

            rectangle.setSize(size.x, size.y);
            rectangle.setCenter(center.x, center.y);

            walls.add(rectangle);
        }
    }

    private void buildPlayerSpawns() {

    }
}
