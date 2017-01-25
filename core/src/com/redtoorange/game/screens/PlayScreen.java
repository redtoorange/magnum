package com.redtoorange.game.screens;

import box2dLight.ConeLight;
import box2dLight.Light;
import box2dLight.PointLight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import com.redtoorange.game.ContactManager;
import com.redtoorange.game.Core;
import com.redtoorange.game.Global;
import com.redtoorange.game.PerformanceCounter;
import com.redtoorange.game.engine.Engine;
import com.redtoorange.game.entities.GameMap;
import com.redtoorange.game.entities.characters.Player;
import com.redtoorange.game.entities.characters.enemies.Enemy;
import com.redtoorange.game.entities.powerups.Ammo;
import com.redtoorange.game.factories.Box2DFactory;
import com.redtoorange.game.systems.GunUI;
import com.redtoorange.game.systems.PhysicsSystem;

/**
 * PlayScreen.java - Primary playing screen that the user will interact with.
 *
 * @author - Andrew M.
 * @version - 13/Jan/2017
 */
public class PlayScreen extends ScreenAdapter {
    private static final int ENEMY_COUNT = 15;

    private Core core;

    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;

    private GameMap gameMap;
    private Player player;

    private Box2DDebugRenderer debugRenderer;
    private ContactManager contactManager;

    private float cameraSmoothing = 0.1f;
    
    private Engine engine;
    private PhysicsSystem physicsSystem;
    private GunUI gunui;

    private ConeLight flashLight;
    private PointLight playerLight;

    public PlayScreen(Core core) {
        this.core = core;
    }

    @Override
    public void show() {
        init();
    }

    private void init() {
        VisUI.load( );

        Gdx.input.setCursorCatched(true);
        Gdx.input.setCursorPosition(Global.WINDOW_WIDTH / 2, Global.WINDOW_HEIGHT / 2);

        debugRenderer = new Box2DDebugRenderer();
        contactManager = new ContactManager();
        engine = new Engine();

        gunui = new GunUI();
        physicsSystem = new PhysicsSystem();
        physicsSystem.getWorld().setContactListener(contactManager);

        camera = new OrthographicCamera(Global.WINDOW_WIDTH, Global.WINDOW_HEIGHT);
        viewport = new ExtendViewport(Global.VIRTUAL_WIDTH, Global.VIRTUAL_HEIGHT, camera);
        batch = new SpriteBatch();

        gameMap = new GameMap("tilemaps/test_map.tmx", null, batch, camera, 1/16f);

        Vector2 playerSpawn = new Vector2( );
        gameMap.playerSpawns.first().getCenter( playerSpawn );

        player = new Player(engine, camera, this, physicsSystem, playerSpawn);
        System.out.println( playerSpawn );

        initWalls();
        


        for(int i = 0; i < ENEMY_COUNT; i++){
            engine.addEntity( new Enemy( physicsSystem, engine, new Vector2( MathUtils.random( 1, 19 ), MathUtils.random( 1, 19 ) ), player ) );
        }

        for(int i = 0; i < ENEMY_COUNT; i++){
            engine.addEntity( new Ammo( new Vector2( MathUtils.random( 1, 19 ), MathUtils.random( 1, 19 ) ), engine, physicsSystem ) );
        }


        engine.addEntity( player );

        playerLight = new PointLight(physicsSystem.getRayHandler(), 10, new Color( 1,1,1, .75f ), 1f, playerSpawn.x, playerSpawn.y);
        flashLight = new ConeLight(physicsSystem.getRayHandler(), 10, new Color(1, 1, 1, .75f), 10f, playerSpawn.x, playerSpawn.y, 0, 30f);
        Filter f = new Filter();
        f.maskBits = Global.ENEMY;
        Light.setGlobalContactFilter( f );
    }

    private void initWalls() {
        for (Rectangle r : gameMap.walls) {
            Filter w = new Filter();
            w.groupIndex = 1;
            Body b = Box2DFactory.createStaticBody(physicsSystem, r);
            b.getFixtureList().first().setFilterData(w);
            b.setUserData(r);
        }
    }

    private PerformanceCounter updateCounter = new PerformanceCounter( "Update:" );
    /**
     *
     * @param deltaTime
     */
    public void update(float deltaTime) {
        updateCounter.start();
    	physicsSystem.update(deltaTime);

    	engine.update(deltaTime);
        gameMap.update( deltaTime );
        gunui.update( deltaTime );

        updateCameraPosition();

        //System.out.println( updateCounter );
    }


    private PerformanceCounter drawCounter = new PerformanceCounter( "Draw:" );
    /**
     *
     */
    public void draw() {
        drawCounter.start();

        camera.update();

        gameMap.draw( batch );

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        engine.render(batch);
        batch.end();

        flashLight.setDirection( player.getRotation() );
        flashLight.setPosition( player.getPosition() );
        playerLight.setPosition( player.getPosition() );

        physicsSystem.render( camera );

        gunui.draw( batch );
        //System.out.println( drawCounter );

        if(Global.DEBUG)
            debugRenderer.render(physicsSystem.getWorld(), camera.combined);

        //System.out.println( drawCounter );
    }

    private void updateCameraPosition() {
        camera.position.lerp(player.getPosition3D(), cameraSmoothing);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        gunui.resize(width, height);
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

        if (physicsSystem != null)
        	physicsSystem.dispose();

        if(engine != null)
            engine.dispose();
    }

    public GunUI getGunUI(){
        return gunui;
    }
}