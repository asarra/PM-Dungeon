package entities.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import de.fhbielefeld.pmdungeon.vorgaben.game.GameSetup;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import entities.GameItem;
import entities.Hero;
import interfaces.ItemVisitor;

public class Weapon extends GameItem {
    private final int damage;
    private final float attackRange;
    private final int MAX_DURABILITY = 100;
    private boolean attacking;
    private float rotation = 0;
    private int durability = 100;


    public Weapon(String name, Texture sprite, int damage, float attackRange) {
        super(name, sprite);
        this.damage = damage;
        this.attackRange = attackRange;
    }

    @Override
    public void accept(ItemVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void onUse(Hero hero) {

        if ( !attacking ) {
            attacking = true;
            hero.setUsingWeapon(true);
        }

        durability -= 10;
        if ( durability <= 0 ) {
            hero.removeEquipment(this);
        }

    }

    public float getAttackRange() {
        return attackRange;
    }

    @Override
    public void onEquip(Hero hero) {
        hero.setStrength(hero.getStrength() + damage);
        hero.setAttackRange(hero.getAttackRange() + attackRange);

    }

    @Override
    public void onUnequip(Hero hero) {
        hero.setStrength(hero.getStrength() - damage);
        hero.setAttackRange(hero.getAttackRange() - attackRange);
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void draw(float xOffset, float yOffset) {

        Texture texture = this.getTexture();
        Sprite sprite = new Sprite(texture);
        //this will resize the texture. this is setuped for the textures used in the thesis
        sprite.setSize(texture.getWidth() * 0.05f, texture.getHeight() * 0.05f);
        sprite.setOrigin(sprite.getWidth() / 2.0f, sprite.getHeight() / 2.0f);
        sprite.setRotation(rotation);

        rotation = sprite.getRotation();

        //where to draw the sprite
        Point position = this.getPosition();
        sprite.setPosition(position.x + xOffset, position.y + yOffset);
        if ( attacking ) {
            rotation -= 15;
        } else if ( rotation < 0 ) {
            rotation += 15;
        }
        if ( attacking && rotation <= -90 ) {
            attacking = false;
        }


        //need to be called before drawing
        GameSetup.batch.begin();
        //draw sprite
        sprite.draw(GameSetup.batch);
        //need to be called after drawing
        GameSetup.batch.end();
    }

}


