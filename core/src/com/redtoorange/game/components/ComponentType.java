package com.redtoorange.game.components;

public class ComponentType {
	private static int current = 0;
	private int key;
	
	public ComponentType(){
		key = current;
		current++;
	}
	
	public int getKey(){
		return key;
	}
	
	public boolean isKey(int key){
		return this.key == key;
	}
}
