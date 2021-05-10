package interfaces;

import entities.items.Potion;
import entities.items.Spell;
import entities.items.Weapon;

public interface ItemVisitor {
    void visit(Weapon weapon);

    void visit(Potion potion);

    void visit(Spell spell);
}
