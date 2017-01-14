package com.redtoorange.game.components;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * SpriteComponent.java - DESCRIPTION
 *
 * @author - Andrew M.
 * @version - 14/Jan/2017
 */
public class SpriteComponent extends Component {
    private Sprite sprite;

    //does a deep copy of the sprite and stores it
    public SpriteComponent(Sprite sprite) {
        this.sprite = new Sprite(sprite);
        sprite.setOriginCenter();
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public Sprite getSprite(){
        return sprite;
    }

    public void setRotation( float rotation ){
        sprite.setRotation(rotation);
    }

    public float getRotation(){
        return sprite.getRotation();
    }

    public void setCenter(Vector2 center){
        sprite.setCenter(center.x, center.y);
    }

    public void setCenterX(float x){
        sprite.setCenter(x, getCenterY());
    }

    public void setCenterY(float y){
        sprite.setCenter(getCenterX(), y);
    }


    public Vector2 getCenter( ){
        return new Vector2(getCenterX(), getCenterY());
    }

    public float getCenterX(){
        return (sprite.getX() + (sprite.getWidth()/2f));
    }

    public float getCenterY(){
        return (sprite.getY() + (sprite.getHeight()/2f));
    }

    public Rectangle getBoudningBox(){
        return sprite.getBoundingRectangle();
    }

    public float getWidth(){
        return sprite.getWidth();
    }

    public float getHeight(){
        return sprite.getHeight();
    }
}
