package com.redtoorange.game.components.physics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.redtoorange.game.components.rendering.SpriteComponent;
import com.redtoorange.game.engine.Updateable;
import com.redtoorange.game.entities.Bullet;
import com.redtoorange.game.factories.Box2DFactory;
import com.redtoorange.game.systems.PhysicsSystem;

/**
 * BulletPhysicsComponent.java - DESCRIPTION
 *
 * @author - Andrew M.
 * @version - 14/Jan/2017
 */
public class BulletPhysicsComponent extends PhysicsComponent implements Updateable {
    private Rectangle bounding;
    private float speed;

    public BulletPhysicsComponent(PhysicsSystem physicsSystem, Bullet bullet, SpriteComponent sc) {
        super(physicsSystem, bullet);

        this.speed = bullet.getSpeed();
        bounding = new Rectangle(sc.getBoundingBox());
        bounding.setSize(sc.getWidth()/4f, sc.getHeight() / 4f);
    }

    public void fire(Vector2 position, Vector2 velocity, float rotation){
        body = Box2DFactory.createBody(physicsSystem, bounding, BodyDef.BodyType.DynamicBody, 10f, 0, 0, true, true);

        body.setUserData(parent);
        body.setTransform(position, (float) Math.toRadians(rotation));
        body.applyLinearImpulse(velocity.scl(speed), body.getWorldCenter(), true);
    }

    @Override
    public void dispose( ) {
        //nothing here
    }

    public void kill(){
        physicsSystem.destroyBody( body );
        body = null;
    }

    @Override
    public void update(float deltaTime) {
        parent.setPosition(body.getPosition());
    }
}
