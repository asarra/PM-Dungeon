package entities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.*;

/**
 * Klasse zum generieren der Gegner.
 * Zuständig für das Generieren von zufälligen gegnern
 */
public class EnemyFactory {
    private static final Logger LOGGER = Logger.getLogger(EnemyFactory.class.getName());

    static {
        LOGGER.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.CONFIG);
        LOGGER.addHandler(consoleHandler);

        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler("logs/" + EnemyFactory.class.getName() + ".log");
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.FINE);
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        LOGGER.addHandler(fileHandler);
        LOGGER.setLevel(Level.CONFIG);
    }

    public EnemyFactory() {

    }

    /**
     * Generiert bestimmte Gegner
     *
     * @param enemy entitites.Enemy aus dem entitites.Enemies Eum
     * @see Enemies
     */
    public Enemy getEnemy(Enemies enemy) {
        switch ( enemy ) {
            case IMP -> {
                return new Imp();

            }
            case MONSTER -> {
                return new Monster();
            }
            default -> {
                return null;
            }

        }

    }

    /**
     * Generiert einen zufälligen Gegner
     */
    public Enemy getRandomEnemy() {
        Random random = new Random();
        int randomNumber = random.nextInt(Enemies.values().length);
        return getEnemy(Enemies.values()[randomNumber]);

    }

    /**
     * Generiert ein Array mit zufälligen Gegnern
     *
     * @param size Die Größe des zu erstellenden Arrays
     * @return entitites.Enemy Array mit zufälligen Gegnern
     */
    public ArrayList<Enemy> getRandomEnemyArrayList(int size) {
        LOGGER.info("GENERATING " + size + " RANDOM ENEMIES");

        ArrayList<Enemy> randomEnemies = new ArrayList<>();
        for ( int i = 0; i < size; i++ ) {
            Enemy randomEnemy = getRandomEnemy();
            randomEnemies.add(randomEnemy);
        }
        return randomEnemies;
    }

}
