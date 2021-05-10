package game;

import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.EntityController;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.MainController;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import entities.*;
import entities.items.ItemFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.*;

public class GameController extends MainController {
    private static final Logger LOGGER = Logger.getLogger(MainController.class.getName());
    private final static int ENEMIESPERHEROLEVEL = 2;

    static {
        LOGGER.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.CONFIG);
        LOGGER.addHandler(consoleHandler);

        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler("logs/" + GameController.class.getName() + ".log");
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.FINE);
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        LOGGER.addHandler(fileHandler);
        LOGGER.setLevel(Level.CONFIG);
    }

    private final ArrayList<IEntity> toRemove = new ArrayList<>();
    //Unser Held & Monster
    private Hero hero;
    private Chest chest;
    private ArrayList<Enemy> enemies;
    private game.FightController fightController;
    private EnemyFactory enemyFactory;
    private ItemFactory itemFactory;
    private CollisionVisitor collisionVisitor;
    private boolean isFirstDungeon;
    private ArrayList<GameItem> items;

    public ArrayList<IEntity> getToRemove() {
        return toRemove;
    }

    @Override
    protected void setup() {

        LOGGER.config("Initializing Game");
        LOGGER.fine("Loading level");

        //Erstellung unseres Helden
        this.hero = new Hero();

        this.enemyFactory = new EnemyFactory();
        this.itemFactory = new ItemFactory();

        this.fightController = new game.FightController(this);
        this.collisionVisitor = new game.CollisionVisitor(hero, fightController, this);

        this.enemies = new ArrayList<>();
        this.items = new ArrayList<>();

        //Ab jetzt kümmert sich der EntityController um das aufrufen von Held.update
        entityController.addEntity(hero);

        this.enemies = enemyFactory.getRandomEnemyArrayList(hero.getExpLevel() * ENEMIESPERHEROLEVEL);
        for ( Enemy enemy : enemies ) {
            entityController.addEntity(enemy);
        }

        this.chest = new Chest();
        entityController.addEntity(chest);

        //unsere Kamera soll sich immer auf den Helden zentrieren.
        camera.follow(hero);

        isFirstDungeon = true;
        setupHeroItems();
    }

    /**
     * Resettet das Spiel, den Held und die Gegner
     */
    public void reset() {
        LOGGER.info("resetting game");
        this.hero.setLevel(levelController.getDungeon());
        this.hero.resetStats();
        this.chest.setLevel(levelController.getDungeon());
        resetEnemies();
        resetItems();


    }

    @Override
    protected void beginFrame() {
        if ( !toRemove.isEmpty() ) {
            for ( IEntity entity : toRemove ) {
                entityController.removeEntity(entity);
            }
            toRemove.clear();
        }
        //Am Anfang jedes Frames ausführen
        if ( hero.isDroppingItem() & hero.getItemToDrop() != null & !entityController.getList().contains(hero.getItemToDrop()) ) {
            entityController.addEntity(hero.getItemToDrop());
            items.add(hero.getItemToDrop());
            hero.setItemToDrop(null);

        }

        isHeroCollisionCheck();
        isChestCollisionCheck();
    }

    @Override
    protected void endFrame() {
        //Am Ende jedes Frames ausführen
        //Prüfe ob der übergebene Point auf der Leiter ist
        if ( levelController.checkForTrigger(this.hero.getPosition()) ) {
            //Lade im nächsten Frame das nächste Level
            levelController.triggerNextStage();
            if ( isFirstDungeon ) {
                isFirstDungeon = false;
            }
            LOGGER.info("Loading next level");

        }


    }

    @Override
    public void onLevelLoad() {
        //Beim Laden des nächsten Levels den Helden platzieren
        this.hero.setLevel(levelController.getDungeon());

        for ( Enemy enemy : this.enemies ) {
            entityController.removeEntity(enemy);
        }



        entityController.removeEntity(this.chest);
        this.chest = new Chest();
        this.chest.setLevel(levelController.getDungeon());
        entityController.addEntity(this.chest);

        enemies = enemyFactory.getRandomEnemyArrayList(hero.getExpLevel() * ENEMIESPERHEROLEVEL);
        for (Enemy enemy : enemies) {

            enemy.setLevel(levelController.getDungeon());
            entityController.addEntity(enemy);

        }
        resetItems();

        LOGGER.info("Level loaded");
    }

    /**
     * Prüft, ob der Held und die Gegner sich auf der gleichen Position befinden, um einen Kampf zu starten.
     */
    private void isHeroCollisionCheck() {
        for ( IEntity entiy : entityController.getList() ) {
            collisionVisitor.visit(entiy);
        }
        //Prüfen, ob sich die Gegner und der Held auf den gleichen Koordinaten befinden.
        hero.accept(collisionVisitor);

    }

    private void isChestCollisionCheck() {
        //Prüfen, ob sich die Chest und der Held auf dem gleichen Koordinaten befinden.
        if (Math.round(this.chest.getPosition().x) == Math.round(this.hero.getPosition().x) &&
                Math.round(this.chest.getPosition().y) == Math.round(this.hero.getPosition().y)){
            //Wenn Hero sich zu der Chest bewegt
            this.chest.open(hero);
        }
        else {
                //Wenn Hero weggeht
                this.chest.close();
            }

    }

    private void resetEnemies() {
        //restliche Gegner entfernen
        for ( Enemy enemy : enemies ) {
            entityController.removeEntity(enemy);
        }
        //Gegner Array neu initialisieren mit zufälligen Gegnern
        this.enemies = enemyFactory.getRandomEnemyArrayList(hero.getExpLevel() * ENEMIESPERHEROLEVEL);

        for ( Enemy enemy : enemies ) {
            enemy.setLevel(levelController.getDungeon());
            entityController.addEntity(enemy);
        }
    }

    private void resetItems() {

        for ( GameItem item : items ) {
            entityController.removeEntity(item);
        }
        items.clear();
    }


    private void setupHeroItems() {
        //this.hero.addToInventory(this.itemFactory.getWeapon(Weapons.SWORD));
        //this.hero.addToInventory(this.itemFactory.getWeapon(Weapons.AXE));
        //this.hero.addToInventory(this.itemFactory.getPotion());

    }

    public EntityController getEntityController() {
        return entityController;
    }
}
