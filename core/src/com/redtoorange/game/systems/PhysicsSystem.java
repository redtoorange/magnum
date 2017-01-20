package com.redtoorange.game.systems;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.redtoorange.game.engine.Updateable;

public class PhysicsSystem extends System implements Updateable, Disposable{
	private World world;
	
	private boolean cullBodies = false;
	private Array<Body> deadBodies = new Array<Body>();
	
	
	public PhysicsSystem() {
		super();
		world = new World(new Vector2(0f, 0f), true);
	}
	
	public PhysicsSystem( Vector2 gravity, boolean allowSleeping) {
		super();
		world = new World(new Vector2(gravity), allowSleeping);
	}

	@Override
	public void update(float deltaTime) {
		world.step(deltaTime, 6, 2);
		
		if(cullBodies)
			destroyBodies();
	}
	
	/*
	 * Helper function to prevent hanging bodies in the world.
	 */
	private void destroyBodies(){
		for(int i = deadBodies.size-1; i >= 0; i--){
			if(deadBodies.get(i) != null)
				world.destroyBody( deadBodies.get(i) );
			
			deadBodies.removeIndex(i);
		}
	}
	
	public void destroyBody(Body b){
		cullBodies = true;
		deadBodies.add(b);
	}
	
	public Body createBody( BodyDef bDef){
		return world.createBody(bDef);
	}
	
	public World getWorld(){
		return world;
	}

	@Override
	public void dispose() {
		if(world != null)
			world.dispose();
	}
	
	
}
