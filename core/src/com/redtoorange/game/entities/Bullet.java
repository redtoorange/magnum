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
    protected float lifeTime = 0.0f;
    protected float maxLife = 5.0f;
    protected boolean alive = false;

    protected BulletPhysicsComponent bulletPhysicsComponent;
    protected SpriteComponent spriteComponent;

    public Bullet(Sprite sprite, PhysicsSystem physicsSystem, Vector2 position) {
        super(position);

        spriteComponent = new SpriteComponent(sprite, this);
        bulletPhysicsComponent = new BulletPhysicsComponent(physicsSystem, this, spriteComponent);
        
        components.add(bulletPhysicsComponent);
        components.add(spriteComponent);
    }

    @Override
    public void update(float deltaTime) {
        if (alive) {
            super.update(deltaTime);

            lifeTime += deltaTime;
            if (lifeTime >= maxLife)
                kill();
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (alive) {
            super.draw(batch);
        }
    }

    public void fire(Vector2 position, Vector2 velocity, float rotation) {
        spriteComponent.setCenter(position);
        spriteComponent.setRotation(rotation);

        bulletPhysicsComponent.fire(position, velocity, rotation);

        alive = true;
    }

    public void kill(){
        this.alive = false;
        lifeTime = 0.0f;
        bulletPhysicsComponent.killVelocity();
    }

    public boolean isAlive(){
        return alive;
    }

    @Override
    public void dispose( ) {
        spriteComponent.dispose();
        bulletPhysicsComponent.dispose();
    }
}