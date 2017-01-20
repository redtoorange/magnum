package com.redtoorange.game.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import com.redtoorange.game.engine.Updateable;
import com.redtoorange.game.entities.Player;
import com.redtoorange.game.factories.Box2DFactory;
import com.redtoorange.game.systems.PhysicsSystem;
import com.redtoorange.game.entities.Character;

/**
 * PlayerPhysicsComponent.java - DESCRIPTION
 *
 * @author - Andrew M.
 * @version - 14/Jan/2017
 */
public class PlayerPhysicsComponent extends PhysicsComponent {
    private float speed = 10f;
    private float linearDampening = 10f;
    private float angularDampening = 10f;
    private float playerDensity = 5f;

    private Body body;
    private Vector2 bodyPosition = new Vector2();
    private Player player;

    public PlayerPhysicsComponent(PhysicsSystem physicsSystem, Player player) {
    	super(physicsSystem, player);
    	
        this.player = player;
        initPhysics(physicsSystem);
    }

    public void update(float deltaTime) {
        bodyPosition = body.getPosition();
        body.setTransform(body.getPosition(), (float) Math.toRadians(player.getRotation()));
        body.applyLinearImpulse(player.getDeltaInput().nor().scl(speed), body.getWorldCenter(), true);
    }

    private void initPhysics(PhysicsSystem physicsSystem) {
        body = Box2DFactory.createBody(physicsSystem, player.getSpriteComponent().getBoudningBox(), BodyDef.BodyType.DynamicBody,
                playerDensity, 0f, 0f, false, false );

        body.setUserData(this);
        body.setFixedRotation(true);
        body.setLinearDamping(linearDampening);
        body.setAngularDamping(angularDampening);
        body.setSleepingAllowed(false);
    }

    public Vector2 getBodyPosition(){
        return bodyPosition;
    }
}
