package com.redtoorange.game.components;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.redtoorange.game.entities.Bullet;
import com.redtoorange.game.factories.Box2DFactory;
import com.redtoorange.game.systems.PhysicsSystem;

/**
 * BulletPhysicsComponent.java - DESCRIPTION
 *
 * @author - Andrew M.
 * @version - 14/Jan/2017
 */
public class BulletPhysicsComponent extends Component {
    private Body body;
    private float speed = 15f;

    public BulletPhysicsComponent(PhysicsSystem physicsSystem, Bullet bullet, SpriteComponent sc) {
        Rectangle bounding = new Rectangle(sc.getBoudningBox());
        bounding.setSize(sc.getWidth()/4f, sc.getHeight() / 4f);

        body = Box2DFactory.createBody(physicsSystem, bounding, BodyDef.BodyType.DynamicBody, 0, 0, 0, true, true);
        body.setUserData(bullet);
    }

    public void fire(Vector2 position, Vector2 velocity, float rotation){
        body.setTransform(position, (float) Math.toRadians(rotation));
        body.setLinearVelocity(0, 0);
        body.applyLinearImpulse(velocity.scl(speed), body.getWorldCenter(), true);
    }

    public Vector2 getBodyPosition(){
        return body.getPosition();
    }
}
