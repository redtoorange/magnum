package com.redtoorange.game.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.redtoorange.game.Global;
import com.redtoorange.game.entities.Bullet;
import com.redtoorange.game.entities.Player;

/**
 * PlayerGunComponent.java - DESCRIPTION
 *
 * @author - Andrew M.
 * @version - 14/Jan/2017
 */
public class PlayerGunComponent extends Component {
    private Texture bulletTexture;
    private Array<Bullet> bulletController = new Array<Bullet>();
    private int bulletIndex = 0;
    private final int MAX_BULLETS = 15;
    private float timeTillFire = 0.0f;
    private float coolDown = 0.05f;
    private boolean fireBullet = false;

    private final Player player;

    public PlayerGunComponent(World world, Player player) {
        this.player = player;
        initBullets(world);
    }

    @Override
    public void update(float deltaTime) {
        processInput();
        updateBullets(deltaTime);
    }

    @Override
    public void draw(SpriteBatch batch) {
        drawBullets(batch);
    }

    private void processInput(){
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && timeTillFire <= 0f) {
            fireBullet = true;
        }
    }

    private void updateBullets(float deltaTime) {
        if (fireBullet)
            fireBullet();


        for (Bullet b : bulletController) {
            b.update(deltaTime);
        }

        if (timeTillFire >= 0f) {
            timeTillFire -= deltaTime;
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

        Vector2 bulletPosition = new Vector2();
        player.getPlayerSprite().getBoundingRectangle().getCenter(bulletPosition);
        bulletPosition.add(new Vector2(0.35f, -0.3f).rotate(player.getRotation()));

        Vector2 velocity = new Vector2( player.getMousePosition().x - bulletPosition.x,
                                        player.getMousePosition().y - bulletPosition.y).nor();

        b.fire(bulletPosition, velocity, player.getRotation());
    }

    private void initBullets(World world) {
        bulletTexture = new Texture("bullet.png");
        Sprite bulletSprite = new Sprite(bulletTexture);
        bulletSprite.setSize(1f, 1f);
        bulletSprite.setOriginCenter();

        for (int i = 0; i < MAX_BULLETS; i++) {
            bulletController.add(new Bullet(new Sprite(bulletSprite), world));
        }
    }

    @Override
    public void dispose() {
        if(Global.DEBUG)
            System.out.println("GunComponent disposed");

        bulletTexture.dispose();
    }
}
