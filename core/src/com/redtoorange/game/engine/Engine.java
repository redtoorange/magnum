package com.redtoorange.game.engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.redtoorange.game.Global;
import com.redtoorange.game.entities.Entity;
import com.redtoorange.game.systems.System;

public class Engine {
	public static final String TAG = Engine.class.getSimpleName();
	private Array<Entity> entities;
	
	public Engine( ){
		entities = new Array<Entity>();
	}
	
	public Engine( Array<System> systems, Array<Entity> entities ){
		this.entities = entities;
	}
	
	public void update(float deltaTime){
		for( Entity e : entities){
			e.update(deltaTime);
		}
	}
	
	public void render( SpriteBatch batch){
		for( Entity e : entities){
			e.draw(batch);
		}
	}
	
		/*
		 * Adds an Entity to the engine.  All attached Systems will parse the entities components for compatible types.
		 * Comparison for equivalence is always by reference.
		 * 
		 * @return		Returns -1 if the Entity is already present and 0 if it was added successfully. 
		 * @param	e	A subclass of Entity that needs to be added to the Engine. 
		 */
		public int addEntity( Entity e ){
			int result = Global.PRESENT;
			
			if( !entities.contains(e, true) ){
				result = Global.SUCCESS;
				
				entities.add(e);
			}
			
			return result;
		}
		
		/*
		 * Removes an Entity from the engine.  Dispose will NOT be called on the entity.  If this is the only reference, you will
		 * lose access. Comparison for equivalence is always by reference.
		 * 
		 * @return		Returns -2 if the Entity is not present and 0 if it was removed successfully. 
		 * @param	e	A subclass of Entity that needs to be removed from the engine.
		 */
		public int removeEntity( Entity e ){
			int result = Global.FAILURE;
			
			if(entities.contains(e, true)){
				result = Global.SUCCESS;
				
				entities.removeValue(e, true);
			}
			
			return result;
		}
}
