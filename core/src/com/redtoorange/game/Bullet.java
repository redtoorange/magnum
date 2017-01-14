package com.redtoorange.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Bullet.java - Basic bullet class.  Will be initialized in an Array and spawned/reset as needed (pooled for efficiency).
 *
 * @author - Andrew M.
 * @version - 13/Jan/2017
 */
public class Bullet {
    private Sprite sprite;
    private Vector2 velocity;

    private float lifeTime = 0.0f;
    private float maxLife = 5.0f;

    private boolean alive = false;
    private float speed = 15.0f;

    private Body body;

    public Bullet(Sprite sprite, World world) {
        this.sprite = sprite;
        velocity = new Vector2(0, 0);

        initPhysics(world);
    }

    public void fire(Vector2 position, Vector2 velocity, float rotation) {
        sprite.setCenter(position.x, position.y);
        sprite.setRotation(rotation);

        body.setTransform(position, (float) Math.toRadians(rotation));
        body.setLinearVelocity(0, 0);
        body.applyLinearImpulse(velocity.scl(speed), body.getWorldCenter(), true);

        alive = true;
        lifeTime = 0.0f;
    }

    public void update(float deltaTime) {
        if (alive) {
            lifeTime += deltaTime;
            if (lifeTime >= maxLife) {
                alive = false;
            }
        }
    }

    public void draw(SpriteBatch batch) {
        if (alive) {
            sprite.setCenter(body.getPosition().x, body.getPosition().y);
            sprite.draw(batch);
        }
    }

    private void initPhysics(World world) {
        BodyDef bDef = new BodyDef();
        bDef.position.set(sprite.getX() + (sprite.getWidth() / 2f), sprite.getY() + (sprite.getHeight() / 2f));
        bDef.type = BodyDef.BodyType.DynamicBody;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.getWidth() / 4f, sprite.getHeight() / 4f);

        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.isSensor = true;
        fDef.filter.groupIndex = Global.BULLET_COL_GROUP;

        body = world.createBody(bDef);
        body.createFixture(fDef);
        body.setBullet(true);
        body.setUserData(this);

        shape.dispose();
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
