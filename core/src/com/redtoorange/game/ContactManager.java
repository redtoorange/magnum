package com.redtoorange.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

/**
 * ContactManager.java - DESCRIPTION
 *
 * @author - Andrew M.
 * @version - 13/Jan/2017
 */
public class ContactManager implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if( (bodyA.getUserData() instanceof Player && bodyB.getUserData() instanceof Bullet)  ||
            (bodyB.getUserData() instanceof Player && bodyA.getUserData() instanceof Bullet) ){
            System.out.println("bullet hit player");
        }

        if( (bodyA.getUserData() instanceof Bullet && bodyB.getUserData() instanceof Rectangle)  ||
                (bodyB.getUserData() instanceof Bullet && bodyA.getUserData() instanceof Rectangle) ){
            if(bodyA.getUserData() instanceof Bullet){
                ((Bullet)bodyA.getUserData()).setAlive(false);
            }
            else{
                ((Bullet)bodyB.getUserData()).setAlive(false);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
