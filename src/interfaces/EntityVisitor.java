package interfaces;

import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import entities.Enemy;
import entities.GameItem;
import entities.Hero;

public interface EntityVisitor {
    void visit(IEntity entity);

    void visit(Hero hero);

    void visit(Enemy enemy);

    void visit(GameItem item);
}
