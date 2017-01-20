package com.redtoorange.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.redtoorange.game.entities.Bullet;
import com.redtoorange.game.entities.characters.Player;
import com.redtoorange.game.entities.characters.enemies.Enemy;

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

        if ((bodyA.getUserData() instanceof Enemy && bodyB.getUserData() instanceof Bullet) ||
                (bodyB.getUserData() instanceof Enemy && bodyA.getUserData() instanceof Bullet)) {
            if(Global.DEBUG)
                System.out.println("bullet hit enemy");

            if (bodyA.getUserData() instanceof Bullet) {
                Bullet b =  ((Bullet) bodyA.getUserData());
                if(b.isAlive()){
                    b.kill();
                    ((Enemy)bodyB.getUserData()).takeDamage(1);
                }
            } else {
                Bullet b =  ((Bullet) bodyB.getUserData());
                if(b.isAlive()){
                    b.kill();
                    ((Enemy)bodyA.getUserData()).takeDamage(1);
                }
            }
        }

        if ((bodyA.getUserData() instanceof Bullet && bodyB.getUserData() instanceof Rectangle) ||
                (bodyB.getUserData() instanceof Bullet && bodyA.getUserData() instanceof Rectangle)) {
            if (bodyA.getUserData() instanceof Bullet) {
                ((Bullet) bodyA.getUserData()).kill();
            } else {
                ((Bullet) bodyB.getUserData()).kill();
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
