package entities.items;

import com.badlogic.gdx.graphics.Texture;
import entities.GameItem;
import entities.Hero;
import interfaces.ItemVisitor;

public class Potion extends GameItem {
    int healAmount;

    public Potion(String name, Texture sprite, int heal) {
        super(name, sprite);
        this.healAmount = heal;
    }

    @Override
    public void accept(ItemVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void onUse(Hero hero) {
        hero.setHealth(hero.getHealth() + this.healAmount);
        hero.removeEquipment(this);
    }

    @Override
    public void onEquip(Hero hero) {

    }

    @Override
    public void onUnequip(Hero hero) {

    }

    @Override
    public void update() {
        this.draw();
    }

    public int getHealAmount() {
        return healAmount;
    }
}
