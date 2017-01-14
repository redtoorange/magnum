package com.redtoorange.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * Player.java - Generic player class.
 *
 * @author - Andrew M.
 * @version - 13/Jan/2017
 */
//TODO: Implement into ECS format
//TODO: Pull bullet system out
//TODO: optimize camera controller
public class Player implements Disposable {
    private Sprite playerSprite;
    private float speed = 10f;
    private float linearDampening = 10f;
    private float angularDampening = 10f;

    private Sprite crossHair;
    private Vector3 mousePosition = new Vector3();
    private Vector2 deltaInput = new Vector2();

    //TODO: Should be moved
    private Texture bulletTexture;
    private Array<Bullet> bulletController = new Array<Bullet>();
    private int bulletIndex = 0;
    private final int MAX_BULLETS = 15;
    private float timeTillFire = 0.0f;
    private float rotation = 0;
    private float coolDown = 0.05f;
    private boolean fireBullet = false;
    //end move

    //Reference to the main camera
    private OrthographicCamera camera;
    private Body body;
    private Vector2 bodyPosition = new Vector2();

    public Player(OrthographicCamera camera, World world) {
        this.camera = camera;

        Texture temp = new Texture("player.png");
        playerSprite = new Sprite(temp);
        playerSprite.setPosition(3f, 3f);
        playerSprite.setSize(1f, 1f);
        playerSprite.setOriginCenter();

        Texture cross = new Texture("crosshair.png");
        crossHair = new Sprite(cross);
        crossHair.setPosition(0f, 0f);
        crossHair.setSize(1f, 1f);

        initPhysics(world);
        initBullets(world);
    }

    private void initPhysics(World world) {
        BodyDef bDef = new BodyDef();
        bDef.position.set(playerSprite.getX() + (playerSprite.getWidth() / 2f), playerSprite.getY() + (playerSprite.getHeight() / 2f));
        bDef.type = BodyDef.BodyType.DynamicBody;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(playerSprite.getWidth() / 2f, playerSprite.getHeight() / 2f);

        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.density = 5f;
        fDef.restitution = 0f;
        fDef.filter.groupIndex = 1;

        body = world.createBody(bDef);
        body.createFixture(fDef);
        body.setUserData(this);
        body.setFixedRotation(true);
        body.setLinearDamping(linearDampening);
        body.setAngularDamping(angularDampening);

        shape.dispose();
    }

    public void update(float deltaTime) {
        mousePosition = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0f));
        crossHair.setCenter(mousePosition.x, mousePosition.y);

        processInput();
        rotatePlayer();
        updateBullets(deltaTime);
    }

    private void rotatePlayer() {
        Vector2 spritePos = new Vector2();
        playerSprite.getBoundingRectangle().getCenter(spritePos);

        rotation = Global.lookAt(spritePos, new Vector2(mousePosition.x, mousePosition.y));
        playerSprite.setRotation(rotation);

        body.setTransform(body.getPosition(), (float) Math.toRadians(rotation));
        //body.setTransform( getPosition2(), (float)Math.toRadians(rotation) );
    }


    public void draw(SpriteBatch batch) {
        bodyPosition = body.getPosition();
        playerSprite.setCenter(bodyPosition.x, bodyPosition.y);

        playerSprite.draw(batch);
        crossHair.draw(batch);

        drawBullets(batch);
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

        body.applyLinearImpulse(deltaInput.nor().scl(speed), body.getWorldCenter(), true);

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && timeTillFire <= 0f) {
            fireBullet = true;
        }
    }


    public Vector2 getPosition2() {
        return new Vector2(playerSprite.getX() + (playerSprite.getWidth() / 2f), playerSprite.getY() + (playerSprite.getHeight() / 2f));
    }

    public Vector3 getPosition3() {
        return new Vector3(playerSprite.getX() + (playerSprite.getWidth() / 2f), playerSprite.getY() + (playerSprite.getHeight() / 2f), 0);
    }

    @Override
    public void dispose() {
        if(Global.DEBUG)
            System.out.println("Player disposed");

        if (playerSprite != null)
            playerSprite.getTexture().dispose();

        bulletTexture.dispose();
    }

    private void updateBullets(float deltaTime) {
        if (fireBullet)
            fireBullet();


        for (Bullet b : bulletController) {
            b.update(deltaTime);
        }

        if (timeTillFire >= 0f) {
            timeTillFire -= deltaTime;
        }
    }

    private void drawBullets(SpriteBatch batch) {
        for (Bullet b : bulletController) {
            b.draw(batch);
        }
    }

    private void fireBullet() {
        fireBullet = false;
        timeTillFire += coolDown;

        Bullet b = bulletController.get(bulletIndex);
        bulletIndex++;
        if (bulletIndex == MAX_BULLETS)
            bulletIndex = 0;

        Vector2 bulletPosition = new Vector2();
        playerSprite.getBoundingRectangle().getCenter(bulletPosition);
        bulletPosition.add(new Vector2(0.35f, -0.3f).rotate(rotation));

        Vector2 velocity = new Vector2(mousePosition.x - bulletPosition.x, mousePosition.y - bulletPosition.y).nor();

        b.fire(bulletPosition, velocity, rotation);
    }

    private void initBullets(World world) {
        bulletTexture = new Texture("bullet.png");
        Sprite bulletSprite = new Sprite(bulletTexture);
        bulletSprite.setSize(1f, 1f);
        bulletSprite.setOriginCenter();

        for (int i = 0; i < MAX_BULLETS; i++) {
            bulletController.add(new Bullet(new Sprite(bulletSprite), world));
        }
    }
}
