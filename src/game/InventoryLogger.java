package game;

import entities.items.Potion;
import entities.items.Spell;
import entities.items.Weapon;
import interfaces.ItemVisitor;
import java.util.logging.Logger;

public class InventoryLogger implements ItemVisitor {
  Logger logger;

  public InventoryLogger(Logger logger) {
    this.logger = logger;
  }

  @Override
  public void visit(Weapon weapon) {
    logger.info(
        weapon.getName()
            + ":\n"
            + "Damage: "
            + weapon.getDamage()
            + "\n"
            + "Range: "
            + weapon.getAttackRange());
  }

  @Override
  public void visit(Potion potion) {
    logger.info(potion.getName() + ":\n" + "Heal amount: " + potion.getHealAmount() + " HP \n");
  }

  // TODO: IMPLEMENT SPELLS
  @Override
  public void visit(Spell spell) {}
}
