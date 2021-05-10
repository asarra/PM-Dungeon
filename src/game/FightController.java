package game;

import entities.Enemy;
import entities.Hero;

import java.io.IOException;
import java.util.Random;
import java.util.logging.*;

/**
 * Das Kampfsystem für das Spiel
 */
public class FightController {
    private static final Logger LOGGER = Logger.getLogger(FightController.class.getName());

    static {
        LOGGER.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.INFO);
        LOGGER.addHandler(consoleHandler);
        LOGGER.setLevel(Level.INFO);

        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler("logs/" + FightController.class.getName() + ".log");
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.INFO);
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        LOGGER.addHandler(fileHandler);
    }

    private final GameController PARENTGAMECONTROLLER;

    /**
     * Konstruktor für das Kampfsystem
     *
     * @param parentGameController für das Neustarten des Spiels
     */
    public FightController(GameController parentGameController) {
        this.PARENTGAMECONTROLLER = parentGameController;
    }

    /**
     * Lässt einen Kampf starten. Es wird berechnet ob der Held den Gegner trifft oder vom Gegner getroffen wird auf Basis der Treffequote
     *
     * @param hero  Der Spieler zum Berechnen der Treffequote.
     * @param enemy Gegner zum Kämpfen
     */
    public void start(Hero hero, Enemy enemy) {
        //Treffer landet erst, wenn die zufällige Zahl kleiner ist als die Trefferquote
        Random random = new Random();
        if ( random.nextFloat() * 100 <= hero.getHitChance() ) {
            enemy.setHealth(enemy.getHealth() - hero.getStrength());

            if ( enemy.isAlive() ) {
                LOGGER.info(
                        "Hero hit enemy!\n" +
                                "Hero: " + hero.getHealth() + "HP left \n " +
                                enemy.getClass().getSimpleName() + ":" + enemy.getHealth() + "HP left.");
            }


            //Wenn der Gegner tot ist, diesen aus dem Spiel entfernen.
            if ( !enemy.isAlive() ) {
                LOGGER.info("Hero killed the " + enemy.getClass().getSimpleName());
                PARENTGAMECONTROLLER.getToRemove().add(enemy);
                //ENTITYCONTROLLER.removeEntity(enemy);

                LOGGER.info("Hero Health restored to " + hero.getMaxHealth());
                //Lebenspunkte wiederherstellen
                hero.restoreHealth();
            }
        } else {
            //Sollte dre Treffer nicht getroffen haben, wird der Held vom Gegner angegriffen
            if ( hero.getHealth() >= 0 ) {
                hero.setHealth(hero.getHealth() - enemy.getStrength());
                LOGGER.info(
                        "Hero was hit by Monster!\n" +
                                "Hero: " + hero.getHealth() + "HP left \n" +
                                enemy.getClass().getName() + ":" + enemy.getHealth() + "HP left.");
            }
            //Wenn sich der Gegner rechts vom Held befindet
            if ( enemy.getPosition().x >= hero.getPosition().x ) {
                //nach links schleudern
                hero.movePositionX(-2.0f);
                //sonst nach rechts schleudern
            } else {
                hero.movePositionX(2.0f);

            }
            //Wenn sich der Gegner unter dem Helden befindet
            if ( enemy.getPosition().y >= hero.getPosition().y ) {
                //nach oben schleudern
                hero.movePositionY(-2.0f);
                //sonst nach unten schleudern
            } else {
                hero.movePositionY(2.0f);

            }
            //Wenn der Held stirbt, das Spiel neustarten
            if ( hero.getHealth() < 0 ) {
                LOGGER.info("GAME OVER");
                PARENTGAMECONTROLLER.reset();
            }

        }
    }

}
