package com.redtoorange.game.factories;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Box2DFactory.java - Construct Boc2D bodies of common types.
 *
 * @author - Andrew M.
 * @version - 14/Jan/2017
 */
public class Box2DFactory {
    public static Body createStaticBody(World world, Rectangle rect){

        return createBody(world, rect, BodyDef.BodyType.StaticBody,
                1, 0, 0,
                false, false);
    }

    public static Body createBody(World world, Rectangle rect, BodyDef.BodyType type,
                                  float density, float friction, float restitution,
                                  boolean isSensor, boolean isBullet){
        BodyDef bDef = new BodyDef();
        bDef.position.set(rect.getX() + (rect.getWidth()/2f), rect.getY() + (rect.getHeight()/2f));
        bDef.type = type;
        bDef.bullet = isBullet;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(rect.getWidth()/2f, rect.getHeight()/2f);

        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.friction = friction;
        fDef.restitution = restitution;
        fDef.density = density;
        fDef.isSensor = isSensor;

        Body body = world.createBody(bDef);
        body.createFixture(fDef);

        shape.dispose();
        return body;
    }
}
