package com.redtoorange.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.redtoorange.game.components.BulletPhysicsComponent;
import com.redtoorange.game.components.SpriteComponent;

/**
 * Bullet.java - Basic bullet class.  Will be initialized in an Array and spawned/reset as needed (pooled for efficiency).
 *
 * @author - Andrew M.
 * @version - 13/Jan/2017
 */
public class Bullet extends Entity{
    private float lifeTime = 0.0f;
    private float maxLife = 5.0f;
    private boolean alive = false;

    private BulletPhysicsComponent bulletPhysicsComponent;
    private SpriteComponent spriteComponent;

    public Bullet(Sprite sprite, World world) {
        spriteComponent = new SpriteComponent(sprite);
        bulletPhysicsComponent = new BulletPhysicsComponent(world, this, spriteComponent);
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
            spriteComponent.setCenter(bulletPhysicsComponent.getBodyPosition());
            spriteComponent.draw(batch);
        }
    }

    public void fire(Vector2 position, Vector2 velocity, float rotation) {
        spriteComponent.setCenter(position);
        spriteComponent.setRotation(rotation);

        bulletPhysicsComponent.fire(position, velocity, rotation);

        alive = true;
        lifeTime = 0.0f;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
