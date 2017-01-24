package com.redtoorange.game.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.redtoorange.game.Global;
import com.redtoorange.game.engine.Drawable;
import com.redtoorange.game.engine.Engine;
import com.redtoorange.game.engine.Updateable;
import com.redtoorange.game.entities.Bullet;
import com.redtoorange.game.entities.characters.Player;
import com.redtoorange.game.entities.powerups.GunType;
import com.redtoorange.game.entities.powerups.Inventory;
import com.redtoorange.game.screens.PlayScreen;
import com.redtoorange.game.systems.GunSoundManager;
import com.redtoorange.game.systems.PhysicsSystem;
import com.redtoorange.game.systems.SoundEffect;

/**
 * PlayerGunComponent.java - DESCRIPTION
 *
 * @author - Andrew M.
 * @version - 14/Jan/2017
 */
public class PlayerGunComponent extends Component implements Updateable, Drawable {
    private final GunType type = GunType.REVOLVER;
    private Inventory playerInventory;

    private Texture bulletTexture;
    private Array<Bullet> bulletController = new Array<Bullet>();

    private int bulletIndex = 0;
    private final int MAX_BULLETS = 6;
    private float timeTillFire = 0.0f;
    private float coolDown = .25f;
    private boolean fireBullet = false;
    private float speed = 5f;

    private int maxBulletsInGun = 6;
    private int bulletsInGun = maxBulletsInGun;

    private TextureRegion[] bulletTextures = new TextureRegion[maxBulletsInGun + 1];

    private boolean needsReload = false;

    private final Player player;
    private PlayScreen playScreen;

    private GunSoundManager gsm = new GunSoundManager();
    private boolean reloading = false;

    public PlayerGunComponent( PhysicsSystem physicsSystem, Engine engine, Player player, PlayScreen playScreen) {
        super(player);

        this.playScreen = playScreen;
        this.player = player;
        playerInventory = player.getInventoy();

        initBullets(physicsSystem, engine);

        for(int i = 0; i <= maxBulletsInGun; i++){
            bulletTextures[i] = new TextureRegion( new Texture( "weapons/revolver/revolver_" + i + ".png" ) );
        }

        playScreen.getGunUI().swapCurrentImage( bulletTextures[maxBulletsInGun] );
    }

    public void update(float deltaTime) {
        processInput();
        updateBullets(deltaTime);
    }

    public void draw(SpriteBatch batch) {
        drawBullets(batch);
    }

    private void processInput(){
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && timeTillFire <= 0f) {
            fireBullet = true;
        }

        if (Gdx.input.isKeyJustPressed( Input.Keys.R) && !reloading ) {
            reload();
        }
    }

    private void updateBullets(float deltaTime) {
        gsm.update( deltaTime );

        if (fireBullet && !needsReload && !reloading) {
            gsm.playSound( "gunshot" );
            bulletsInGun--;
            playScreen.getGunUI().swapCurrentImage( bulletTextures[bulletsInGun] );

            if(bulletsInGun <= 0) {
                needsReload = true;
            }

            fireBullet( );
        }
        else if(fireBullet && needsReload && !reloading){
            SoundEffect se = gsm.getSoundEffect("nobullets");

            if(!se.isPlaying())
                gsm.playSound( "nobullets" );

            fireBullet = false;
        }
        else{
            fireBullet = false;
        }


        for (Bullet b : bulletController) {
            b.update(deltaTime);
        }

        if (timeTillFire >= 0f) {
            timeTillFire -= deltaTime;
        }

        if( reloading ){
            if(!gsm.getSoundEffect("reloaded").isPlaying())
                reloading = false;
        }
    }

    private void reload(){
        if(bulletsInGun > 0){
            playerInventory.pickup( type, bulletsInGun );
        }
        int bullets = Math.min(maxBulletsInGun, playerInventory.remaining( type ) );
        playerInventory.consume( type, bullets );
        bulletsInGun = bullets;

        playScreen.getGunUI().swapCurrentImage( bulletTextures[bulletsInGun] );

        if(bulletsInGun > 0) {
            SoundEffect se = gsm.getSoundEffect("reloaded");

            if(!se.isPlaying())
                gsm.playSound( "reloaded" );

            needsReload = false;
            reloading = true;
        }
    }

    private void drawBullets(SpriteBatch batch) {
        for (Bullet b : bulletController) {
            b.draw(batch);
        }
    }

    private void fireBullet() {
        fireBullet = false;
        timeTillFire += coolDown;

        Bullet b = bulletController.get(bulletIndex);
        bulletIndex++;
        if (bulletIndex == MAX_BULLETS)
            bulletIndex = 0;

        Vector2 bulletPosition = player.getSpriteComponent().getCenter();
        bulletPosition.add(new Vector2(0.35f, -0.3f).rotate(player.getRotation()));

        Vector2 velocity = new Vector2( player.getMousePosition().x - bulletPosition.x,
                                        player.getMousePosition().y - bulletPosition.y).nor();
        velocity.scl( speed );

        b.fire(bulletPosition, velocity, player.getRotation());
    }

    private void initBullets(PhysicsSystem physicsSystem, Engine engine) {
        bulletTexture = new Texture("bullet.png");
        Sprite bulletSprite = new Sprite(bulletTexture);
        bulletSprite.setSize(1f, 1f);
        bulletSprite.setOriginCenter();

        for (int i = 0; i < MAX_BULLETS; i++) {
            bulletController.add(new Bullet(new Sprite(bulletSprite), engine, physicsSystem, new Vector2(-1000, -1000), speed));
        }
    }

    @Override
    public void dispose() {
        for(TextureRegion t : bulletTextures){
            t.getTexture().dispose();
        }
        if(Global.DEBUG)
            System.out.println("GunComponent disposed");

        bulletTexture.dispose();
    }
}
