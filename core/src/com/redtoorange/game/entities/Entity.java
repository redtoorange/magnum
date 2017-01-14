package com.redtoorange.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.redtoorange.game.components.Component;

/**
 * Entity.java - DESCRIPTION
 *
 * @author - Andrew M.
 * @version - 14/Jan/2017
 */
public abstract class Entity {
    protected Array<Component> components = new Array<Component>();

    public abstract void update(float deltaTime);
    public abstract void draw(SpriteBatch batch);

    public <T extends Component> T getComponent( Class<? extends Component> classOfInterest ) {
        T obj = null;

        for ( Component c : components ) {
            if( classOfInterest.isInstance( c ) ) obj = (T) c;
            if( obj != null ) return obj;
        }

        return obj;
    }

    public void addComponent( Component c ){
        components.add(c);
    }

    public void removeComponent( Component c){
        components.removeValue(c, true);
    }
}
