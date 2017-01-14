package com.redtoorange.game.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

/**
 * Component.java - DESCRIPTION
 *
 * @author - Andrew M.
 * @version - 14/Jan/2017
 */
public abstract class Component implements Disposable{
    public void update(float deltaTime){}
    public void draw(SpriteBatch batch){}
    public void dispose() {}
}
