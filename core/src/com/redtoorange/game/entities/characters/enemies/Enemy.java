package com.redtoorange.game.entities.characters.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.redtoorange.game.Global;
import com.redtoorange.game.components.physics.EnemyPhysicsComponent;
import com.redtoorange.game.components.rendering.SpriteComponent;
import com.redtoorange.game.entities.characters.Character;
import com.redtoorange.game.entities.characters.Player;
import com.redtoorange.game.systems.PhysicsSystem;

public class Enemy extends Character {
    protected Vector2 playerPosition;
    protected Player player;

    public Enemy(PhysicsSystem physicsSystem, Vector2 spawnPosition, Player player) {
        this(physicsSystem, spawnPosition);

        this.player = player;
        playerPosition = new Vector2( player.getPosition2D() );
    }

    public Enemy(PhysicsSystem physicsSystem, Vector2 spawnPosition ) {
        super(physicsSystem);

        playerPosition = new Vector2( 0, 0 );

        spriteComponent = new SpriteComponent( loadEnemySprite(spawnPosition), this );
        physicsComponent = new EnemyPhysicsComponent( physicsSystem, this );

        addComponent( spriteComponent );
        addComponent( physicsComponent );
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    protected Sprite loadEnemySprite( Vector2 position ){
        Texture temp = new Texture("zombie.png");

        Sprite sprite = new Sprite(temp);
        sprite.setPosition(position.x, position.y);
        sprite.setSize(1f, 1f);
        sprite.setOriginCenter();

        return sprite;
    }

    @Override
    public void dispose( ) {
        super.dispose();
    }

    @Override
    public void update( float deltaTime ) {
        super.update( deltaTime );

        calculateDeltaInput();
        rotateToFacePlayer();
    }

    protected void rotateToFacePlayer(){
        playerPosition.set( player.getPosition2D() );
        spriteComponent.setRotation( Global.lookAt( spriteComponent.getCenter(), playerPosition ) );
    }

    protected void calculateDeltaInput(){
        //TODO: figure out how to rotate this around to face player
        deltaInput.set( 1, 0 );
        deltaInput.rotate( getRotation() );
    }

    @Override
    public void takeDamage( int amount ) {
        super.takeDamage( amount );

        spriteComponent.setColor( ((float)health/ (float) maxHealth), 0, 0, 1 );
    }

    @Override
    protected void die( ) {
        removeComponent( spriteComponent );
        removeComponent( physicsComponent );

        physicsComponent.destroy();
    }
}
