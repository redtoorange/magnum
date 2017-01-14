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

/**
 * Player.java - Generic player class.
 *
 * @author - Andrew M.
 * @version - 13/Jan/2017
 */
//TODO: Implement into ECS format
//TODO: Pull bullet system out
//TODO: optimize camera controller
public class Player extends Entity implements Disposable {
    private Sprite playerSprite;
    private Sprite crossHair;
    private Vector3 mousePosition = new Vector3();
    private Vector2 deltaInput = new Vector2();
    private OrthographicCamera camera;    //Reference to the main camera

    private PlayerGunComponent gunComponent;
    private PlayerPhysicsComponent physicsComponent;

    private float rotation = 0;

    public Player(OrthographicCamera camera, World world) {
        this.camera = camera;
        loadAssets();

        gunComponent = new PlayerGunComponent(world, this);
        physicsComponent = new PlayerPhysicsComponent(world, this);
    }

    private void loadAssets() {
        Texture temp = new Texture("player.png");
        playerSprite = new Sprite(temp);
        playerSprite.setPosition(3f, 3f);
        playerSprite.setSize(1f, 1f);
        playerSprite.setOriginCenter();

        Texture cross = new Texture("crosshair.png");
        crossHair = new Sprite(cross);
        crossHair.setPosition(0f, 0f);
        crossHair.setSize(1f, 1f);
    }

    public void update(float deltaTime) {
        mousePosition = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0f));
        crossHair.setCenter(mousePosition.x, mousePosition.y);

        processInput();
        rotatePlayer();

        physicsComponent.update(deltaTime);
        gunComponent.update(deltaTime);
    }

    public void draw(SpriteBatch batch) {
        playerSprite.setCenter(physicsComponent.getBodyPosition().x, physicsComponent.getBodyPosition().y);
        playerSprite.draw(batch);
        crossHair.draw(batch);

        physicsComponent.draw(batch);
        gunComponent.draw(batch);
    }

    private void rotatePlayer() {
        rotation = Global.lookAt(   new Vector2(playerSprite.getX() + (playerSprite.getWidth() / 2f), playerSprite.getY() + (playerSprite.getHeight()/2f)),
                                    new Vector2(mousePosition.x, mousePosition.y));
        playerSprite.setRotation(rotation);
    }

    private void processInput() {
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


    public Vector2 getPosition2D() {
        return new Vector2(playerSprite.getX() + (playerSprite.getWidth() / 2f), playerSprite.getY() + (playerSprite.getHeight() / 2f));
    }

    public Vector3 getPosition3D() {
        return new Vector3(playerSprite.getX() + (playerSprite.getWidth() / 2f), playerSprite.getY() + (playerSprite.getHeight() / 2f), 0);
    }

    @Override
    public void dispose() {
        if(Global.DEBUG)
            System.out.println("Player disposed");

        if (playerSprite != null)
            playerSprite.getTexture().dispose();
    }

    public Sprite getPlayerSprite(){
        return playerSprite;
    }

    public Vector3 getMousePosition(){
        return mousePosition;
    }

    public float getRotation(){
        return rotation;
    }

    public Vector2 getDeltaInput(){
        return deltaInput;
    }
}
