package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import entities.Chest;
import entities.DungeonQuest;
import entities.Enemy;
import entities.GameItem;
import entities.Hero;
import entities.Projectile;
import entities.Trap;
import interfaces.EntityVisitor;
import java.util.Random;

/** Klasse für die Erkennung einer Kolision im Spiel mit verschiedenen Objekteb */
public class CollisionVisitor implements EntityVisitor {
  private final Hero hero;
  private final FightController fightController;
  private final GameController gameController;

  CollisionVisitor(Hero hero, FightController fightController, GameController gameController) {
    this.hero = hero;
    this.fightController = fightController;
    this.gameController = gameController;
  }

  /*
   * Methode für di Aufrufe der Kollisionsfunktionen für Klassen, die aus Ientity erben*/
  @Override
  public void visit(IEntity entity) {
    if (entity instanceof Enemy) {
      ((Enemy) entity).accept(this);
    }
    if (entity instanceof GameItem) {
      ((GameItem) entity).accept(this);
    }
    if (entity instanceof Projectile) {
      ((Projectile) entity).accept(this);
    }
    if (entity instanceof Chest) {
      ((Chest) entity).accept(this);
    }
    if (entity instanceof Trap) {
      ((Trap) entity).accept(this);
    }
    if (entity instanceof DungeonQuest) {
      ((DungeonQuest) entity).accept(this);
    }
  }

  @Override
  public void visit(Hero hero) {}

  @Override
  public void visit(Enemy enemy) {
    Point heroPosition = hero.getPosition();
    String heroDirection = hero.getDirection();
    float heroAttackRange = hero.getAttackRange();
    Point enemyPosition = enemy.getPosition();

    if (hero.isUsingWeapon()) {
      // wenn der Held seine Waffe benutzt
      if (heroDirection.equals("right")) {
        // prüfen ob sich Gegner in der Range des Heldens befinden
        if (enemyPosition.x >= heroPosition.x
            && enemyPosition.x <= heroPosition.x + heroAttackRange
            && enemyPosition.y <= heroPosition.y + heroAttackRange
            && enemyPosition.y >= heroPosition.y - heroAttackRange
            && enemy.isAlive()) {
          fightController.start(hero, enemy);
        }
      }
      if (heroDirection.equals("left")) {
        // prüfen ob sich Gegner in der Range des Heldens befinden
        if (enemyPosition.x <= heroPosition.x
            && enemyPosition.x >= heroPosition.x - heroAttackRange
            && enemyPosition.y <= heroPosition.y + heroAttackRange
            && enemyPosition.y >= heroPosition.y - heroAttackRange
            && enemy.isAlive()) {
          fightController.start(hero, enemy);
        }
      }
      hero.setUsingWeapon(false);
    } else {
      if (Math.round(enemyPosition.x) == Math.round(heroPosition.x)
          &&
          // Wenn sich
          Math.round(enemyPosition.y) == Math.round(heroPosition.y)
          && enemy.isAlive()) {
        // Wenn sich Gegner und Held auf dem selben Feld befinden
        fightController.start(this.hero, enemy);
      }
    }
    if (Math.round(gameController.getTrap().getPosition().x) == Math.round(enemyPosition.x)
        && Math.round(gameController.getTrap().getPosition().y) == Math.round(enemyPosition.y)) {
      // Wenn Held an der Position der Falle befindet
      gameController.getTrap().setHidden(false);
      gameController.getTrap().setActive(true);
      enemy.setPosition(hero.getLevel().getRandomPointInDungeon());
    }
  }

  @Override
  public void visit(GameItem item) {

    if (Math.round(item.getPosition().x) == Math.round(this.hero.getPosition().x)
        && Math.round(item.getPosition().y) == Math.round(this.hero.getPosition().y)
        && gameController.getEntityController().getList().contains(item)) {

      hero.addToInventory(item);
      gameController.getToRemove().add(item);
    }
    if (Math.round(gameController.getTrap().getPosition().x) == Math.round(item.getPosition().x)
        && Math.round(gameController.getTrap().getPosition().y) == Math.round(item.getPosition().y)
        && gameController.getEntityController().getList().contains(item)) {
      gameController.getToRemove().add(item);
    }
  }

  @Override
  public void visit(Projectile projectile) {
    if (!hero.getLevel().isTileAccessible(projectile.getPosition())) {
      gameController.getToRemove().add(projectile);
    }
    for (IEntity enemy : gameController.getEntityController().getList()) {
      if (enemy instanceof Enemy) {
        Point enemyPosition = ((Enemy) enemy).getPosition();
        if (Math.round(enemyPosition.x) == Math.round(projectile.getPosition().x)
            && Math.round(enemyPosition.y) == Math.round(projectile.getPosition().y)
            && ((Enemy) enemy).isAlive()) {

          ((Enemy) enemy).setBaseHealth(((Enemy) enemy).getBaseHealth() - projectile.getDamage());
          System.out.println("a");
          gameController.getToRemove().add(projectile);
          if (!((Enemy) enemy).isAlive()) {
            hero.addExpPoints(((Enemy) enemy).getExpDrop());
            gameController.getToRemove().add(enemy);
          }
        }
      }
    }
  }

  public void visit(Chest chest) {}

  public void visit(Trap trap) {
    Point trapPosition = trap.getPosition();
    Point heroPosition = hero.getPosition();

    if (Math.round(trapPosition.x) == Math.round(heroPosition.x)
        &&
        // Wenn sich
        Math.round(trapPosition.y) == Math.round(heroPosition.y)) {
      trap.setActive(true);
      if (trap.isHidden()) {
        trap.setHidden(false);
      }
      if (trap.isActive()) {
        Random random = new Random();
        int effect = random.nextInt(2 - 1 + 1) + 1;
        if (effect == 1) {
          hero.setBaseHealth(hero.getBaseHealth() - 50.0f);

          hero.movePositionX(-2.0f);
        }
        if (effect == 2) {
          hero.setPosition(hero.getLevel().getRandomPointInDungeon());
        }

        trap.setActive(false);
      }
    }
  }

  public void visit(DungeonQuest quest) {
    Point questPosition = quest.getPosition();
    Point heroPosition = hero.getPosition();
    boolean accepted;

    if (Math.round(questPosition.x) == Math.round(heroPosition.x)
        && Math.round(questPosition.y) == Math.round(heroPosition.y)) {
      gameController.QuestText(quest);

      accepted = Gdx.input.isKeyPressed(Input.Keys.J);

    } else {
      accepted = false;
    }

    if (accepted) {
      System.out.println("nase");
      // entferne Questbubble
      hero.addQuest(quest.getQuest());
      gameController.getToRemove().add(quest);
      gameController.deleteQuestText();
      gameController.questProgressText();

    } else if (Gdx.input.isKeyPressed(Input.Keys.W)
        || Gdx.input.isKeyPressed(Input.Keys.A)
        || Gdx.input.isKeyPressed(Input.Keys.S)
        || Gdx.input.isKeyPressed(Input.Keys.D)) {
      gameController.deleteQuestText();
    }
  }
}
