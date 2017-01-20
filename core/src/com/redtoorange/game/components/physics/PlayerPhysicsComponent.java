package com.redtoorange.game.components.physics;

import com.redtoorange.game.entities.characters.Player;
import com.redtoorange.game.systems.PhysicsSystem;

/**
 * PlayerPhysicsComponent.java - DESCRIPTION
 *
 * @author - Andrew M.
 * @version - 14/Jan/2017
 */
public class PlayerPhysicsComponent extends CharacterPhysicsComponent {
    public PlayerPhysicsComponent(PhysicsSystem physicsSystem, Player player) {
    	super(physicsSystem, player, 10f, 10f, 10f,
                5f);
    }
}