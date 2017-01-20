package com.redtoorange.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.redtoorange.game.entities.Bullet;
import com.redtoorange.game.entities.characters.Player;

/**
 * ContactManager.java - Handle the contacts between different entities.
 *
 * @author - Andrew M.
 * @version - 13/Jan/2017
 */
//TODO: Steamline the contacts with presolving.
//TODO: Implement Filters for all major entities.
public class ContactManager implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if ((bodyA.getUserData() instanceof Player && bodyB.getUserData() instanceof Bullet) ||
                (bodyB.getUserData() instanceof Player && bodyA.getUserData() instanceof Bullet)) {
            if(Global.DEBUG)
                System.out.println("bullet hit player");
        }

        if ((bodyA.getUserData() instanceof Bullet && bodyB.getUserData() instanceof Rectangle) ||
                (bodyB.getUserData() instanceof Bullet && bodyA.getUserData() instanceof Rectangle)) {
            if(Global.DEBUG)
                System.out.println("bullet hit wall");
            if (bodyA.getUserData() instanceof Bullet) {
                ((Bullet) bodyA.getUserData()).setAlive(false);
            } else {
                ((Bullet) bodyB.getUserData()).setAlive(false);
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
