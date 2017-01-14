package com.redtoorange.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.redtoorange.game.*;

/**
 * PlayScreen.java - Primary playing screen that the user will interact with.
 *
 * @author - Andrew M.
 * @version - 13/Jan/2017
 */
//TODO: Move physics to a new Systems. Encapsulate the physics update loop.
public class PlayScreen extends ScreenAdapter {
    private Core core;

    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;

    private GameMap gameMap;
    private Player player;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private ContactManager contactManager;

    public PlayScreen(Core core) {
        this.core = core;
    }

    @Override
    public void show() {
        init();
    }

    private void init() {
        Gdx.input.setCursorCatched(true);
        Gdx.input.setCursorPosition(Global.WINDOW_WIDTH / 2, Global.WINDOW_HEIGHT / 2);

        world = new World(new Vector2(0, 0), true);
        debugRenderer = new Box2DDebugRenderer();
        contactManager = new ContactManager();

        world.setContactListener(contactManager);

        camera = new OrthographicCamera(Global.WINDOW_WIDTH, Global.WINDOW_HEIGHT);
        viewport = new ExtendViewport(Global.VIRTUAL_WIDTH, Global.VIRTUAL_HEIGHT, camera);
        batch = new SpriteBatch();

        gameMap = new GameMap("tilemaps/test_map.tmx", batch);
        player = new Player(camera, world);

        for (Rectangle r : gameMap.walls) {
            BodyDef bDef = new BodyDef();

            Vector2 center = new Vector2();
            r.getCenter(center);
            bDef.position.set(center);
            bDef.type = BodyDef.BodyType.StaticBody;

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(r.getWidth() / 2f, r.getHeight() / 2f);

            Body body = world.createBody(bDef);

            FixtureDef fDef = new FixtureDef();
            fDef.filter.groupIndex = 1;
            fDef.density = 1f;
            fDef.shape = shape;

            body.createFixture(fDef);
            body.setUserData(r);

            shape.dispose();
        }
    }

    public void draw() {
        camera.update();
        gameMap.render(camera);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        player.draw(batch);

        batch.end();

        if(Global.DEBUG)
            debugRenderer.render(world, camera.combined);
    }

    public void update(float deltaTime) {
        player.update(deltaTime);
        updateCameraPosition();
        world.step(deltaTime, 6, 2);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.update();
    }

    @Override
    public void dispose() {
        if(Global.DEBUG)
            System.out.println("PlayScreen disposed");

        Gdx.input.setCursorCatched(false);

        if (gameMap != null)
            gameMap.dispose();

        if (batch != null)
            batch.dispose();

        if (player != null)
            player.dispose();

        if (world != null)
            world.dispose();
    }

    private void updateCameraPosition() {
        camera.position.set(player.getPosition3());
    }
}
