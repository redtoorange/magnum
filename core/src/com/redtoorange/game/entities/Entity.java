package com.redtoorange.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.redtoorange.game.components.Component;
import com.redtoorange.game.engine.Drawable;
import com.redtoorange.game.engine.Updateable;

/**
 * Entity.java - DESCRIPTION
 *
 * @author - Andrew M.
 * @version - 14/Jan/2017
 */
public abstract class Entity {
    protected Array<Component> components = new Array<Component>();

    public void update(float deltaTime){
    	for(Component c : components){
    		if(c instanceof Updateable)
    			((Updateable)c).update(deltaTime);
    	}
    }
    
    public void draw(SpriteBatch batch){
    	for(Component c : components){
    		if(c instanceof Drawable)
    			((Drawable)c).draw(batch);
    	}
    }

    public <T extends Component> T getComponent( Class<? extends Component> classOfInterest ) {
        T obj = null;

        for ( Component c : components ) {
            if( classOfInterest.isInstance( c ) ) obj = (T) c;
            if( obj != null ) return obj;
        }

        return obj;
    }

    protected void addComponent( Component c ){
        components.add(c);
    }

    protected void removeComponent( Component c){
        components.removeValue(c, true);
    }
    
    public Array<Component> getComponents(){
    	return components;
    }
}
