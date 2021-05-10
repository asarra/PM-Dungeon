package game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.EntityController;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.MainController;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import entities.*;
import entities.items.ItemFactory;
import ui.ChestWindowManager;
import ui.HeartIcon;
import ui.InventoryWindowManager;

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
    public ArrayList<Projectile> projectiles;
    //Unser Held & Monster
    private Hero hero;
    private Chest chest;
    private Trap trap;
    private ArrayList<Enemy> enemies;
    private game.FightController fightController;
    private final EnemyFactory enemyFactory = new EnemyFactory();
    private final ItemFactory itemFactory = new ItemFactory();
    private CollisionVisitor collisionVisitor;
    private boolean isFirstDungeon;
    private ArrayList<GameItem> items;
    private InventoryWindowManager inventoryWindowManager;
    private ChestWindowManager chestWindowManager;
    private Label heroLvlLabel;


    public ArrayList<IEntity> getToRemove() {
        return toRemove;
    }

    @Override
    protected void setup() {

        LOGGER.config("Initializing Game");
        LOGGER.fine("Loading level");

        //Erstellung unseres Helden
        this.hero = new Hero();

        this.fightController = new game.FightController(this);
        this.collisionVisitor = new game.CollisionVisitor(hero, fightController, this);

        this.items = new ArrayList<>();
        this.projectiles = new ArrayList<>();

        //Ab jetzt kümmert sich der EntityController um das aufrufen von Held.update
        entityController.addEntity(hero);

        this.enemies = enemyFactory.getRandomEnemyArrayList(hero.getExpLevel() * ENEMIESPERHEROLEVEL);
        for ( Enemy enemy : enemies ) {
            entityController.addEntity(enemy);
        }

        //unsere Kamera soll sich immer auf den Helden zentrieren.
        camera.follow(hero);

        isFirstDungeon = true;

        hero.registerEntityController(entityController);
        heroLvlLabel = textHUD.drawText("Hero EXP LV.", "assets/textures/ui/SuperLegendBoy-4w8Y.ttf", Color.WHITE, 18, 50, 50, 30, 30);
    }

    @Override
    protected void beginFrame() {

        heroLvlLabel.setText("Hero EXP LVL." + hero.getExpLevel() + "\n " + hero.getCurrentExpPoints() + "/" + hero.getExpToNextLevel());
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

        if ( hero.getCurrentProjectile() != null ) {
            entityController.addEntity(hero.getCurrentProjectile());
            projectiles.add(hero.getCurrentProjectile());
            hero.setCurrentProjectile(null);
        }


        isHeroCollisionCheck();
        isChestCollisionCheck();

        chestWindowManager.update(chest);
        /** Überprüfen wir, ob der Nutzer das Inventar ansehen will und zeigen es danach an */
        if ( hero.getinventoryOpen() )
            inventoryWindowManager.open();
        else if ( !hero.getinventoryOpen() )
            inventoryWindowManager.close();

        /** Überprüfen wir, ob die Truhe geöffnet wurde*/
        if ( chest.isChestOpen() )
            chestWindowManager.open();
        else if ( !chest.isChestOpen() ) {
            chestWindowManager.close();
        }

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

        entityController.removeAll();
        entityController.addEntity(hero);
        /** UI im GUI*/
        hud.addHudElement(new HeartIcon(hero));
        inventoryWindowManager = new InventoryWindowManager(hero);
        inventoryWindowManager.setup(hud);

        this.chest = new Chest();
        this.chest.setLevel(levelController.getDungeon());

        chestWindowManager = new ChestWindowManager(chest);
        chestWindowManager.setup(hud);

        this.trap = new Trap();
        this.trap.setLevel(levelController.getDungeon());

        entityController.addEntity(this.chest);
        entityController.addEntity(this.trap);

        enemies = enemyFactory.getRandomEnemyArrayList(hero.getExpLevel() * ENEMIESPERHEROLEVEL);
        for ( Enemy enemy : enemies ) {
            enemy.setLevel(levelController.getDungeon());
            entityController.addEntity(enemy);
        }
        resetItems();

        LOGGER.info("Level loaded");
    }

    private void resetItems() {
        items.clear();
    }

    /**
     * Prüft, ob der Held und die Gegner sich auf der gleichen Position befinden, um einen Kampf zu starten.
     */
    private void isHeroCollisionCheck() {
        for ( IEntity entiy : entityController.getList() ) {
            collisionVisitor.visit(entiy);
        }
        //Prüfen, ob sich die Gegner und der Held auf den gleichen Koordinaten befinden.


    }

    private void isChestCollisionCheck() {
        //Prüfen, ob sich die Chest und der Held auf dem gleichen Koordinaten befinden.
        if ( Math.round(this.chest.getPosition().x) == Math.round(this.hero.getPosition().x) &&
                Math.round(this.chest.getPosition().y) == Math.round(this.hero.getPosition().y) ) {
            //Wenn Hero sich zu der Chest bewegt
            this.chest.open(hero);
        } else {
            //Wenn Hero weggeht
            this.chest.close();
        }

    }

    private void setupHeroItems() {
        //this.hero.addToInventory(this.itemFactory.getWeapon(Weapons.SWORD));
        //this.hero.addToInventory(this.itemFactory.getWeapon(Weapons.AXE));
        //this.hero.addToInventory(this.itemFactory.getPotion());

    }

    public Trap getTrap() {
        return trap;
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

    public EntityController getEntityController() {
        return entityController;
    }
}
