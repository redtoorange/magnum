package com.redtoorange.game.entities.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.redtoorange.game.Global;
import com.redtoorange.game.components.PlayerGunComponent;
import com.redtoorange.game.components.physics.PlayerPhysicsComponent;
import com.redtoorange.game.components.rendering.SpriteComponent;
import com.redtoorange.game.screens.PlayScreen;
import com.redtoorange.game.systems.PhysicsSystem;

/**
 * Player.java - Generic player class.
 *
 * @author - Andrew M.
 * @version - 13/Jan/2017
 */
//TODO: optimize camera character
public class Player extends Character {
    private Sprite crossHair;
    private Vector3 mousePosition = new Vector3();

    private OrthographicCamera camera;    //Reference to the main camera
    private PlayerGunComponent gunComponent;

    public Player( OrthographicCamera camera, PlayScreen playScreen, PhysicsSystem physicsSystem, Vector2 spawnPoint) {
        super(spawnPoint, physicsSystem);

        this.camera = camera;
        loadAssets( spawnPoint );

        gunComponent = new PlayerGunComponent(physicsSystem, this, playScreen);
        physicsComponent = new PlayerPhysicsComponent(physicsSystem, this);
        
        addComponent(physicsComponent);
        addComponent(gunComponent);
        addComponent(spriteComponent);
    }

    protected void loadAssets(Vector2 spawnPoint ) {
        Texture temp = new Texture("player.png");

        Sprite sprite = new Sprite(temp);
        sprite.setPosition(spawnPoint.x, spawnPoint.y);
        sprite.setSize(1f, 1f);
        sprite.setOriginCenter();
        
        spriteComponent = new SpriteComponent(sprite, this);

        Texture cross = new Texture("crosshair.png");
        crossHair = new Sprite(cross);
        crossHair.setPosition(0f, 0f);
        crossHair.setSize(1f, 1f);
    }

    public void update(float deltaTime) {
    	super.update(deltaTime);
    	
        mousePosition = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0f));
        crossHair.setCenter(mousePosition.x, mousePosition.y);

        processInput();
        rotatePlayerToMouseDirection();
    }

    @Override
    public void draw(SpriteBatch batch) {
    	crossHair.draw(batch);
        super.draw(batch);
    }

    
    protected void rotatePlayerToMouseDirection() {
        if(spriteComponent != null){
            float rotation = Global.lookAt( spriteComponent.getCenter(), new Vector2(mousePosition.x, mousePosition.y));
            setRotation(rotation);
        }
    }

    protected void processInput() {
        deltaInput.set(0, 0);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();

        if (Gdx.input.isKeyPressed(Input.Keys.W))
            deltaInput.y = 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            deltaInput.y = -1;
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            deltaInput.x = -1;
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            deltaInput.x = 1;
    }

    public Vector3 getMousePosition(){
        return mousePosition;
    }

    @Override
    public void dispose( ) {
        super.dispose( );

        if(gunComponent != null)
            gunComponent.dispose();
    }

    @Override
    protected void die( ) {
        removeComponent( spriteComponent );
        removeComponent( physicsComponent );
    }
}