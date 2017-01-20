package com.redtoorange.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.redtoorange.game.Global;
import com.redtoorange.game.components.PlayerGunComponent;
import com.redtoorange.game.components.PlayerPhysicsComponent;
import com.redtoorange.game.components.SpriteComponent;
import com.redtoorange.game.systems.PhysicsSystem;

/**
 * Player.java - Generic player class.
 *
 * @author - Andrew M.
 * @version - 13/Jan/2017
 */
//TODO: Implement into ECS format
//TODO: Pull bullet system out
//TODO: optimize camera controller
public class Player extends Character implements Disposable {
    private Sprite crossHair;
    private Vector3 mousePosition = new Vector3();
    private Vector2 deltaInput = new Vector2();
    private OrthographicCamera camera;    //Reference to the main camera

    private PlayerGunComponent gunComponent;
    private PlayerPhysicsComponent physicsComponent;

    private float rotation = 0;

    public Player(OrthographicCamera camera, PhysicsSystem physicsSystem) {
    	super( physicsSystem);
    	
        this.camera = camera;
        loadAssets();

        gunComponent = new PlayerGunComponent(physicsSystem, this);
        physicsComponent = new PlayerPhysicsComponent(physicsSystem, this);
        
        addComponent(physicsComponent);
        addComponent(gunComponent);
        addComponent(spriteComponent);
    }

    protected void loadAssets() {
        Texture temp = new Texture("player.png");
        Sprite sprite = new Sprite(temp);
        sprite.setPosition(3f, 3f);
        sprite.setSize(1f, 1f);
        sprite.setOriginCenter();
        
        spriteComponent = new SpriteComponent(sprite);

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
        rotatePlayer();
    }

    @Override
    public void draw(SpriteBatch batch) {
    	spriteComponent.setCenter(physicsComponent.getBodyPosition());
    	
    	
        crossHair.draw(batch);
        super.draw(batch);
    }

    
    protected void rotatePlayer() {
        rotation = Global.lookAt( spriteComponent.getCenter(), new Vector2(mousePosition.x, mousePosition.y));
        spriteComponent.setRotation(rotation);
    }

    protected void processInput() {
        deltaInput.set(0, 0);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            deltaInput.y = 1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            deltaInput.y = -1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            deltaInput.x = -1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            deltaInput.x = 1;
        }
    }

    public Vector3 getMousePosition(){
        return mousePosition;
    }
}
