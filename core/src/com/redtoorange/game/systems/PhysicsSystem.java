package com.redtoorange.game.systems;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.redtoorange.game.engine.Updateable;

/**
 * PhysicsSystem.java - Box2D Physics World encapsulation.  Will handle adding and removal of bodies
 * gracefully while still allowing the World to step.
 *
 * @author - Andrew M.
 * @version - 20/Jan/2017
 */
public class PhysicsSystem extends System implements Updateable, Disposable{
	private World world;
	private boolean cullBodies = false;
	private Array<Body> deadBodies = new Array<Body>();

	private boolean createBodies = false;
	private Array<BodyDef> newBodies = new Array<BodyDef>();

	/**
	 * Create a new Box2D World with no gravity.
	 */
	public PhysicsSystem() {
		super();
		world = new World(new Vector2(0f, 0f), true);
	}

	/**
	 * Create a new Box2D World with the specified gravity and sleeping.
	 * @param gravity			How much x,y gravity to apply per time step.
	 * @param allowSleeping		Should the World allow bodies to sleep.
	 */
	public PhysicsSystem( Vector2 gravity, boolean allowSleeping) {
		super();
		world = new World(new Vector2(gravity), allowSleeping);
	}

	/**
	 * Step the world based on the deltaTime.  All bodies will be stepped.  If there are any bodies to be destroyed,
	 * it will happen after the world is stepped.
	 * @param deltaTime		The amount of time to step the world by.
	 */
	@Override
	public void update(float deltaTime) {
		world.step(deltaTime, 6, 2);
		
		if(cullBodies)
			destroyBodies();
		if(createBodies)
			spawnBodies();
	}
	
	/**
	 * Helper function to prevent hanging bodies in the world.
	 */
	private void destroyBodies(){
		cullBodies = false;
		for(int i = deadBodies.size-1; i >= 0; i--){
			if(deadBodies.get(i) != null)
				world.destroyBody( deadBodies.get(i) );
			
			deadBodies.removeIndex(i);
		}
	}

	/**
	 * Register the body for destruction.  It will cull the body after the time step on the first frame the
	 * body was registered for destruction.
	 * @param body
	 */
	public void destroyBody(Body body){
		cullBodies = true;
		deadBodies.add(body);
	}

	/**
	 * Create a new body in the world.  The world will handle the disposal of all bodies when it is destroyed.
	 * @param bDef	Box2D Body Definition to used when creating the body.
	 * @return		A reference to the body.
	 */
	public Body createBody( BodyDef bDef){
		return world.createBody(bDef);
	}

	public void spawnBodies(){
		createBodies = false;
		for(int i = newBodies.size-1; i >= 0; i--){
			if(newBodies.get(i) != null)
				world.createBody( newBodies.get(i) );

			newBodies.removeIndex(i);
		}
	}

	/**
	 * Get a reference to the World.
	 * @return a reference to the World.  This will allow you to alter the World.
	 */
	public World getWorld(){
		return world;
	}

	@Override
	public void dispose() {
		if(world != null)
			world.dispose();
	}
}
