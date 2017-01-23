package com.redtoorange.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.redtoorange.game.components.Component;
import com.redtoorange.game.engine.Drawable;
import com.redtoorange.game.engine.Engine;
import com.redtoorange.game.engine.Updateable;

/**
 * Entity.java - DESCRIPTION
 *
 * @author - Andrew M.
 * @version - 14/Jan/2017
 */
public abstract class Entity implements Disposable{
    protected Array<Component> components = new Array<Component>();
    public Vector2 position = new Vector2(0, 0);
	protected Engine engine;

    public Entity(Vector2 position, Engine engine){
        this.position.set(position);
		this.engine = engine;
    }

    public Vector2 getPosition(){
        return position;
    }

    public void setPosition(Vector2 position){
        this.position.set(position);
    }

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

    public Engine getEngine(){
		return engine;
	}

	@Override
	public void dispose( ) {
		for( Component c : components)
			c.dispose();
		components.clear();

		if(engine != null )
			engine.removeEntity( this );
	}
}
