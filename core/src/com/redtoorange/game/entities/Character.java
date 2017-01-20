package com.redtoorange.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.redtoorange.game.Global;
import com.redtoorange.game.components.PhysicsComponent;
import com.redtoorange.game.components.SpriteComponent;
import com.redtoorange.game.systems.PhysicsSystem;

public class Character extends Entity implements Disposable{
	protected float rotation = 0;
	protected Vector2 deltaInput = new Vector2();
    
    protected PhysicsComponent physicsComponent;
    protected SpriteComponent spriteComponent;


    public Character( PhysicsSystem physicsSystem ) {
//        loadAssets();
//
//        //physicsComponent = new PhysicsComponent(physicsSystem, this);
//        
//        addComponent(physicsComponent);
//        addComponent(spriteComponent);        
    }

    protected void loadAssets() {
//        Texture temp = new Texture("player.png");
//        Sprite sprite = new Sprite(temp);
//        sprite.setPosition(3f, 3f);
//        sprite.setSize(1f, 1f);
//        sprite.setOriginCenter();
//
//        spriteComponent = new SpriteComponent(sprite);
    }

    public void update(float deltaTime) {
    	super.update(deltaTime);

        //processInput();
        //rotatePlayer();
    }

    public void draw(SpriteBatch batch) {
    	//spriteComponent.setCenter(physicsComponent.getBodyPosition());
        super.draw(batch);
    }

    protected void rotatePlayer() {
        //rotation = Global.lookAt( spriteComponent.getCenter(), new Vector2(mousePosition.x, mousePosition.y));
        //spriteComponent.setRotation(rotation);
    }

    protected void processInput() {
        //deltaInput.set(0, 0);
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

    public float getRotation(){
        return rotation;
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
