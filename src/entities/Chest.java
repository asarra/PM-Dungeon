package entities;

import com.badlogic.gdx.graphics.Texture;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;
import entities.items.ItemFactory;
import entities.items.Weapons;
import game.CollisionVisitor;
import game.Inventory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

/**
 * Klasse zur Darstellung einer Schatztzuhe im Spiel
 * Scgatztuhen spawnen in zufälligen Positionen im Dungeon
 */
public class Chest extends GameActor {
    private static final Logger LOGGER = Logger.getLogger(Chest.class.getName());

    static {
        LOGGER.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.FINE);
        LOGGER.addHandler(consoleHandler);
        LOGGER.setLevel(Level.FINE);

        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler("logs/" + Chest.class.getName() + ".log");
            fileHandler.setFormatter(new SimpleFormatter());
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        LOGGER.addHandler(fileHandler);
    }

    private final Animation openAnimation;
    private final Animation closeAnimation;
    private final Animation idleopenAnimation;
    private final Animation idlecloseAnimation;
    private final int INVENTORYCAPACITY = 3;
    private final Inventory inventory;
    private final boolean inventoryOpen;
    private final ItemFactory itemFactory;
    private boolean isChestOpen = false;
    private boolean logged = false;

    public Chest() {

        //ArrayLists für die Animationen erstellen
        List<Texture> open = new ArrayList<>();
        List<Texture> close = new ArrayList<>();
        List<Texture> idleopen = new ArrayList<>();
        List<Texture> idleclose = new ArrayList<>();

        //Laden der Texturen für die jeweilige Animation
        int i = 0;
        for ( ; i < 2; i++ )
            open.add(new Texture("assets/textures/chest/chest_full_open_anim_f" + i + ".png"));

        idleopen.add(new Texture("assets/textures/chest/chest_full_open_anim_f" + i + ".png"));

        for ( ; i >= 0; i-- )
            close.add(new Texture("assets/textures/chest/chest_full_open_anim_f" + i + ".png"));

        idleclose.add(new Texture("assets/textures/chest/chest_full_open_anim_f" + (i + 1) + ".png"));

        this.openAnimation = new Animation(open, 4);
        this.closeAnimation = new Animation(close, 4);
        this.idleopenAnimation = new Animation(idleopen, 4);
        this.idlecloseAnimation = new Animation(idleclose, 4);

        this.activeAnimation = this.idlecloseAnimation;

        inventory = new Inventory(INVENTORYCAPACITY);
        inventoryOpen = false;

        //Geben wir der Truhe zufällige Items (max. 3 Items pro Truhe)
        this.itemFactory = new ItemFactory();
        int jMax, kMax, lMax, j = 0, k = 0, l = 0, summe = 0;
        int min = i = 0;
        for ( ; i <= INVENTORYCAPACITY; i++ ) {
            jMax = getRandomNumber(min, INVENTORYCAPACITY);
            for ( j = 0; j < jMax; j++ )
                this.addToInventory(this.itemFactory.getWeapon(Weapons.SWORD));
            summe += j + k + l;
            if ( summe == 3 ) {
                break;
            }

            kMax = getRandomNumber(i, INVENTORYCAPACITY);
            for ( k = 0; k < kMax; k++ )
                this.addToInventory(this.itemFactory.getWeapon(Weapons.AXE));
            summe += j + k + l;
            if ( summe == 3 ) {
                break;
            }

            lMax = getRandomNumber(i, INVENTORYCAPACITY);
            for ( l = 0; l < lMax; l++ )
                this.addToInventory(this.itemFactory.getPotion());
            summe += j + k + l;
            if ( summe == 3 ) {
                break;
            }
        }


        LOGGER.log(Level.INFO, "Chest created");
    }

    //Helfer Funktion für die zufällige Items
    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    /**
     * Fügt der Schatztruhe ein neues Item hinzu
     *
     * @param item Item zum Hinzufügen
     */
    public void addToInventory(GameItem item) {
        this.inventory.addItem(item);
    }

    /**
     * Öffnet die Schatztruhe und übergibt dem Helden die Items aus der Schatzkiste, wenn möglich.
     *
     * @param hero Held zum Übergeben der Items.
     */
    public void open(Hero hero) {
        this.activeAnimation = this.openAnimation;
        this.activeAnimation = this.idleopenAnimation;
        this.isChestOpen = true;
        if ( !logged ) {
            LOGGER.log(Level.INFO, "Folgende Items sind in der Schatzkiste: " + this.inventory.returnItemNames());
            if ( !inventory.isEmpty() ) {
                for ( int i = 0; i < inventory.getSize(); i++ ) {
                    if ( !hero.getInventory().isFull() ) {
                        hero.addToInventory(inventory.getItem(i));
                        inventory.removeItem(i); //Habe nachträglich noch das hinzugefügt
                    }

                }
            }
            logged = true;
        }

    }

    /**
     * Schließt die Schatztruhe
     */
    public void close() {
        this.activeAnimation = this.closeAnimation;
        this.activeAnimation = this.idlecloseAnimation;
        this.isChestOpen = false;
        logged = false;
    }

    /**
     * Entfernt ein Item aus der Schatztruhe
     *
     * @param index Position an der sich das zu löschende Item befindet
     */
    public void removeFromInventoryByIndex(int index) {
        this.inventory.removeItem(index);
    }


    @Override
    public void update() {
        this.draw();
    }

    public boolean isChestOpen() {
        return isChestOpen;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void accept(CollisionVisitor visitor) {
        visitor.visit(this);
    }
}
