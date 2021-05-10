package entities.items;

import com.badlogic.gdx.graphics.Texture;
import entities.GameItem;
import entities.Hero;
import interfaces.ItemVisitor;

//TODO: IMPLEMENT SPELLS
public class Spell extends GameItem {

    public Spell(String name, Texture sprite) {
        super(name, sprite);
    }

    @Override
    public void accept(ItemVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void onUse(Hero hero) {

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
}
