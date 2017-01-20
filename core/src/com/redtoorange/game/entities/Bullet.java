package com.redtoorange.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.redtoorange.game.components.physics.BulletPhysicsComponent;
import com.redtoorange.game.components.rendering.SpriteComponent;
import com.redtoorange.game.systems.PhysicsSystem;

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

    public Bullet(Sprite sprite, PhysicsSystem physicsSystem) {
        spriteComponent = new SpriteComponent(sprite, this);
        bulletPhysicsComponent = new BulletPhysicsComponent(physicsSystem, this, spriteComponent);
        
        components.add(bulletPhysicsComponent);
        components.add(spriteComponent);
    }

    @Override
    public void update(float deltaTime) {
    	super.update(deltaTime);
    	
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
            super.draw(batch);
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
