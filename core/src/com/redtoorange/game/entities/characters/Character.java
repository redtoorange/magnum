package com.redtoorange.game.entities.characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.redtoorange.game.Global;
import com.redtoorange.game.components.physics.CharacterPhysicsComponent;
import com.redtoorange.game.components.rendering.SpriteComponent;
import com.redtoorange.game.entities.Entity;
import com.redtoorange.game.systems.PhysicsSystem;

public class Character extends Entity implements Disposable{
    protected CharacterPhysicsComponent physicsComponent;
    protected SpriteComponent spriteComponent;

	protected Vector2 deltaInput = new Vector2();

    public Character(){

    }

    public Character( PhysicsSystem physicsSystem ) {
    }

    public void update(float deltaTime) {
    	super.update(deltaTime);
    }

    public void draw(SpriteBatch batch) {
        if(physicsComponent != null)
    	    spriteComponent.setCenter(physicsComponent.getBodyPosition());

        super.draw(batch);
    }


    public Vector2 getPosition2D() {
        return spriteComponent.getCenter();
    }

    public Vector3 getPosition3D() {
        Vector2 pos = getPosition2D();
        return new Vector3(pos.x, pos.y, 0);
    }

    public SpriteComponent getSpriteComponent(){
        return spriteComponent;
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
    public void dispose() {
        if(Global.DEBUG)
            System.out.println("Player disposed");

        if (spriteComponent != null)
            spriteComponent.dispose();
    }
}
