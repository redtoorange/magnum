package com.redtoorange.game.entities.powerups;

/**
 * ${FILE_NAME}.java - Description
 *
 * @author
 * @version 23/Jan/2017
 */
public class Inventory {
	private int[] ammoAmounts = new int[GunType.TOTAL_GUNS.getValue()];

	public Inventory(){
		for(int i = 0; i < ammoAmounts.length; i++){
			ammoAmounts[i] = 0;
		}
	}

	public void consume(GunType type, int amount){
		ammoAmounts[type.getValue()] -= amount;
	}

	public void pickup(GunType type, int amount){
		ammoAmounts[type.getValue()] += amount;
	}

	public int remaining(GunType type){
		return ammoAmounts[type.getValue()];
	}
}
