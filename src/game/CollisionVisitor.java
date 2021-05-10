package game;

import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import entities.Enemy;
import entities.GameItem;
import entities.Hero;
import interfaces.EntityVisitor;

@SuppressWarnings("ClassCanBeRecord")
public class CollisionVisitor implements EntityVisitor {
    private final Hero hero;
    private final FightController fightController;
    private final GameController gameController;

    CollisionVisitor(Hero hero, FightController fightController, GameController gameController) {
        this.hero = hero;
        this.fightController = fightController;
        this.gameController = gameController;
    }

    @Override
    public void visit(IEntity entity) {
        if ( entity instanceof Enemy ) {
            ((Enemy) entity).accept(this);
        }
        if ( entity instanceof GameItem ) {
            ((GameItem) entity).accept(this);
        }
    }

    @Override
    public void visit(Hero hero) {
    }


    @Override
    public void visit(Enemy enemy) {
        Point heroPosition = hero.getPosition();
        String heroDirection = hero.getDirection();
        float heroAttackRange = hero.getAttackRange();
        Point enemyPosition = enemy.getPosition();


        if ( hero.isUsingWeapon() ) {
            if ( heroDirection.equals("right") ) {

                if ( enemyPosition.x >= heroPosition.x &&
                        enemyPosition.x <= heroPosition.x + heroAttackRange &&
                        enemyPosition.y <= heroPosition.y + heroAttackRange &&
                        enemyPosition.y >= heroPosition.y - heroAttackRange &&
                        enemy.isAlive() ) {
                    fightController.start(hero, enemy);

                }
            }
            if ( heroDirection.equals("left") ) {
                if ( enemyPosition.x <= heroPosition.x &&
                        enemyPosition.x >= heroPosition.x - heroAttackRange &&
                        enemyPosition.y <= heroPosition.y + heroAttackRange &&
                        enemyPosition.y >= heroPosition.y - heroAttackRange &&
                        enemy.isAlive() ) {
                    fightController.start(hero, enemy);

                }
            }
            hero.setUsingWeapon(false);
        } else {
            if ( Math.round(enemyPosition.x) == Math.round(heroPosition.x) &&
                    Math.round(enemyPosition.y) == Math.round(heroPosition.y) &&
                    enemy.isAlive() ) {

                fightController.start(this.hero, enemy);
            }
        }


    }

    @Override
    public void visit(GameItem item) {

        if ( Math.round(item.getPosition().x) == Math.round(this.hero.getPosition().x) &&
                Math.round(item.getPosition().y) == Math.round(this.hero.getPosition().y) && gameController.getEntityController().getList().contains(item) ) {

            hero.addToInventory(item);
            gameController.getToRemove().add(item);
        }
    }
}
