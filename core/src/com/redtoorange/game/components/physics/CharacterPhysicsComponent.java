package com.redtoorange.game.components.physics;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.redtoorange.game.engine.Updateable;
import com.redtoorange.game.entities.characters.Character;
import com.redtoorange.game.factories.Box2DFactory;
import com.redtoorange.game.systems.PhysicsSystem;

/**
 * CharacterPhysicsComponent.java - Generic physics component that can be attached
 * to any character.
 *
 * @author - Andrew M.
 * @version - 14/Jan/2017
 */
public abstract class CharacterPhysicsComponent extends PhysicsComponent implements Updateable {
    protected float linearDampening;
    protected float angularDampening;
    protected float density;

    protected Character character;

    public CharacterPhysicsComponent(PhysicsSystem physicsSystem, Character character,
                                     float speed, float linearDampening, float angularDampening,
                                     float density) {
        super(physicsSystem, character);

        this.speed = speed;
        this.linearDampening = linearDampening;
        this.angularDampening = angularDampening;
        this.density = density;
        this.character = character;

        initPhysics(physicsSystem);
    }

    @Override
    public void update(float deltaTime) {
        body.setTransform(body.getPosition(), (float) Math.toRadians(character.getRotation()));
        body.applyLinearImpulse(character.getDeltaInput().nor().scl(speed), body.getWorldCenter(), true);

        character.setPosition(body.getPosition());
    }

    protected void initPhysics(PhysicsSystem physicsSystem) {
        body = Box2DFactory.createBody(physicsSystem, character.getSpriteComponent().getBoundingBox(), BodyDef.BodyType.DynamicBody,
                density, 0f, 0f, false, false );

        body.setUserData(character);

        body.setFixedRotation(true);
        body.setLinearDamping(linearDampening);
        body.setAngularDamping(angularDampening);
        body.setSleepingAllowed(false);
    }

    @Override
    public void dispose( ) {
        //nothing really
    }
}