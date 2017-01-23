package com.redtoorange.game.entities.characters;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.redtoorange.game.components.physics.character.CharacterPhysicsComponent;
import com.redtoorange.game.components.physics.PhysicsComponent;
import com.redtoorange.game.components.rendering.SpriteComponent;
import com.redtoorange.game.entities.Entity;
import com.redtoorange.game.systems.PhysicsSystem;

public abstract class Character extends Entity {
    protected CharacterPhysicsComponent physicsComponent;
    protected SpriteComponent spriteComponent;
    protected PhysicsSystem physicsSystem;
	protected Vector2 deltaInput = new Vector2();

    protected int maxHealth = 10;
    protected int health = maxHealth;

    public Character(Vector2 position, PhysicsSystem physicsSystem){
        super(position);
        this.physicsSystem = physicsSystem;
    }

    public Vector3 getPosition3D() {
        return new Vector3(position.x, position.y, 0);
    }

    public SpriteComponent getSpriteComponent(){
        return spriteComponent;
    }

    public PhysicsComponent getPhysicsComponent(){
        return physicsComponent;
    }

    protected void setRotation(float rotation){
        spriteComponent.setRotation(rotation);
    }

    public float getRotation(){
        return spriteComponent.getRotation();
    }

    public Vector2 getDeltaInput(){
        return deltaInput;
    }

    @Override
    public void dispose( ) {
        if(physicsComponent != null)
            physicsComponent.dispose();
        if(spriteComponent != null)
            spriteComponent.dispose();
    }

    public void takeDamage( int amount ){
        health -= amount;
        if(health <= 0){
            die();
        }
    }

    protected abstract void die();
}