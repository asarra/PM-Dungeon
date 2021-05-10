package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import game.CollisionVisitor;
import game.Inventory;
import game.InventoryLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

/**
 * Klasse zur Darstellung des Spielers
 * Diese Klasse besitzt Eigenschaften wie Lebenspunkte, Geschwindigkeit, Blickrichtung, und EXPLevel.
 */
public class Hero extends entities.GameActor {
    private static final Logger LOGGER = Logger.getLogger(Hero.class.getName());

    static {
        LOGGER.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.INFO);
        LOGGER.addHandler(consoleHandler);
        LOGGER.setLevel(Level.FINE);

        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler("logs/" + Hero.class.getName() + ".log");
            fileHandler.setFormatter(new SimpleFormatter());
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        LOGGER.addHandler(fileHandler);
    }

    @SuppressWarnings("FieldCanBeLocal")
    private final int INVENTORYCAPACITY = 3;
    private final Inventory inventory;
    private final int EQUIPMENTCAPACITY = 2;
    private final InventoryLogger equipmentLogger;
    private String direction;
    private int expLevel = 1;
    private float hitChance = 90.0f;
    private ArrayList<entities.GameItem> equipment;
    private boolean inventoryOpen;
    private boolean equipmentOpen;
    private boolean droppingItem;
    private entities.GameItem itemToDrop;
    private Point targetPoint;
    private boolean usingWeapon;
    private float attackRange;

    /**
     * Konstruktor zur Initilisierung des Helden mit den Startwerten und Animationen
     */
    public Hero() {
        maxHealth = 700.0f;
        health = 700.0f;
        strength = 100.0f;
        movementSpeed = 0.1f;
        inventory = new Inventory(INVENTORYCAPACITY);
        inventoryOpen = false;
        equipment = new ArrayList<>();
        droppingItem = false;
        targetPoint = null;
        attackRange = 1.0f;
        equipmentLogger = new InventoryLogger(LOGGER);


        //Blickrichtung nach rechts
        this.direction = "right";
        //ArrayLists für die Animationen erstellen
        List<Texture> idle = new ArrayList<>();
        List<Texture> idleLeft = new ArrayList<>();
        List<Texture> right = new ArrayList<>();
        List<Texture> left = new ArrayList<>();
        //Laden der Texturen für die jeweilige Animation
        idle.add(new Texture("assets/textures/characters/playercharacters/knight/knight_m_idle_anim_f0.png"));
        idle.add(new Texture("assets/textures/characters/playercharacters/knight/knight_m_idle_anim_f1.png"));
        idle.add(new Texture("assets/textures/characters/playercharacters/knight/knight_m_idle_anim_f2.png"));
        idle.add(new Texture("assets/textures/characters/playercharacters/knight/knight_m_idle_anim_f3.png"));

        idleLeft.add(new Texture("assets/textures/characters/playercharacters/knight/knight_m_idle_anim_left_f0.png"));
        idleLeft.add(new Texture("assets/textures/characters/playercharacters/knight/knight_m_idle_anim_left_f1.png"));
        idleLeft.add(new Texture("assets/textures/characters/playercharacters/knight/knight_m_idle_anim_left_f2.png"));
        idleLeft.add(new Texture("assets/textures/characters/playercharacters/knight/knight_m_idle_anim_left_f3.png"));

        right.add(new Texture("assets/textures/characters/playercharacters/knight/knight_m_run_anim_f0.png"));
        right.add(new Texture("assets/textures/characters/playercharacters/knight/knight_m_run_anim_f1.png"));
        right.add(new Texture("assets/textures/characters/playercharacters/knight/knight_m_run_anim_f2.png"));
        right.add(new Texture("assets/textures/characters/playercharacters/knight/knight_m_run_anim_f3.png"));

        left.add(new Texture("assets/textures/characters/playercharacters/knight/knight_m_run_anim_left_f0.png"));
        left.add(new Texture("assets/textures/characters/playercharacters/knight/knight_m_run_anim_left_f1.png"));
        left.add(new Texture("assets/textures/characters/playercharacters/knight/knight_m_run_anim_left_f2.png"));
        left.add(new Texture("assets/textures/characters/playercharacters/knight/knight_m_run_anim_left_f3.png"));

        this.idleAnimation = new Animation(idle, 4);
        this.idleLeftAnimation = new Animation(idleLeft, 4);
        this.rightAnimation = new Animation(right, 4);
        this.leftAnimation = new Animation(left, 4);
        //Idle Animation als aktive Animation setzten
        this.activeAnimation = idleAnimation;
        LOGGER.log(Level.INFO, "Hero Character created");
    }

    public void accept(CollisionVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Bewegt den Helden um die x Achse
     *
     * @param x um wie viele Einheiten sich der Held nach links/rechts bewegen soll.
     */
    public void movePositionX(float x) {

        Point newPosition = new Point(this.position.x + x, this.position.y);
        if ( this.targetPoint != null ) {
            newPosition = new Point(this.position.x + x, this.targetPoint.y);
        }
        if ( level.isTileAccessible(newPosition) ) {
            this.targetPoint = newPosition;

        }

    }


    /**
     * Bewegt den Helden um die y Achse
     *
     * @param y Um wie viele Einheiten sich der Held nach oben/unten bewegen soll.
     */
    public void movePositionY(float y) {
        Point newPosition = new Point(this.position.x, this.position.y + y);
        if ( this.targetPoint != null ) {
            newPosition = new Point(this.targetPoint.x, this.position.y + y);
        }
        if ( level.isTileAccessible(newPosition) ) {
            this.targetPoint = newPosition;
        }

    }

    /**
     * Setzt die Stats des Helden zurück
     */
    public void resetStats() {
        this.maxHealth = 700.0f;
        this.health = maxHealth;
        this.expLevel = 1;

    }

    public int getExpLevel() {
        return expLevel;
    }

    public void setExpLevel(int expLevel) {
        this.expLevel = expLevel;
    }

    public float getHitChance() {
        return hitChance;
    }

    public void setHitChance(float hitChance) {
        this.hitChance = hitChance;
    }

    public ArrayList<entities.GameItem> getEquipment() {
        return equipment;
    }

    public void setEquipment(ArrayList<entities.GameItem> equipment) {
        this.equipment = equipment;
    }

    public void addToInventory(entities.GameItem item) {
        this.inventory.addItem(item);
    }

    public void removeFromInventoryByIndex(int index) {
        this.inventory.removeItem(index);
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void addEquipment(entities.GameItem item) {
        this.equipment.add(item);
        item.onEquip(this);
    }

    public void removeEquipment(entities.GameItem item) {
        item.onUnequip(this);
        equipment.remove(item);
    }

    public boolean isDroppingItem() {
        return droppingItem;
    }

    public entities.GameItem getItemToDrop() {
        return itemToDrop;
    }

    public void setItemToDrop(entities.GameItem itemToDrop) {
        this.itemToDrop = itemToDrop;
    }

    public void equipFromInventory(int index) {
        if ( !inventory.isEmpty() ) {
            if ( inventory.getItem(index) != null ) {
                if ( equipment.size() == EQUIPMENTCAPACITY ) {
                    inventory.addItem(equipment.get(0));
                    equipment.get(0).onUnequip(this);
                    equipment.remove(0);

                }
                addEquipment(inventory.getItem(index));
                inventory.removeItem(index);
            }


        }
    }

    @Override
    public void update() {
        //zeichnet den Helden

        this.draw();
        if ( equipment.size() > 0 ) {

            equipment.get(0).setPosition(position.x - 0.2f, position.y + 0.25f);
            equipment.get(0).draw();
            if ( equipment.size() == 2 ) {
                equipment.get(1).setPosition(position.x + 0.5f, position.y + 0.25f);
                equipment.get(1).draw();
            }
        }

        if ( this.targetPoint != null ) {

            Point newPosition = new Point(this.position);
            newPosition.x += 0.4f * (this.targetPoint.x - this.position.x);
            newPosition.y += 0.4f * (this.targetPoint.y - this.position.y);
            if ( !level.isTileAccessible(newPosition) ) {
                this.targetPoint = this.position;
            } else {
                this.position = newPosition;
            }

            if ( (Math.round(this.position.x) == Math.round(this.targetPoint.x) & Math.round(this.position.y) == Math.round(this.targetPoint.y)) ) {
                this.targetPoint = null;
            }
        }
        if ( inventoryOpen ) {
            if ( Gdx.input.isKeyJustPressed(Input.Keys.I) ) {
                this.inventoryOpen = false;
            }
        } else if ( equipmentOpen ) {
            if ( (Gdx.input.isKeyJustPressed(Input.Keys.E)) ) {
                equipmentOpen = false;
            }
            if ( Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) ) {
                equipFromInventory(0);
            }
            if ( Gdx.input.isKeyJustPressed(Input.Keys.NUM_2) ) {
                equipFromInventory(1);
            }
            if ( Gdx.input.isKeyJustPressed(Input.Keys.NUM_3) ) {
                equipFromInventory(2);
            }

        } else if ( droppingItem ) {
            if ( (Gdx.input.isKeyJustPressed(Input.Keys.Q)) ) {
                droppingItem = false;
            }
            if ( Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) ) {
                dropItemFromInventoy(0);
            }
            if ( Gdx.input.isKeyJustPressed(Input.Keys.NUM_2) ) {
                dropItemFromInventoy(1);
            }
            if ( Gdx.input.isKeyJustPressed(Input.Keys.NUM_3) ) {
                dropItemFromInventoy(2);
            }
        } else {
            checkForMoveinput();

            if ( Gdx.input.isKeyJustPressed(Input.Keys.I) ) {
                LOGGER.info("Current items in Hero Inventory");
                inventory.logInventory(LOGGER);
                inventoryOpen = true;
            }
            if ( Gdx.input.isKeyJustPressed(Input.Keys.E) ) {
                LOGGER.info("Current items equiped");
                for ( GameItem item : equipment ) {
                    item.accept(equipmentLogger);
                }
                equipmentOpen = true;
            }
            if ( Gdx.input.isKeyJustPressed(Input.Keys.Q) ) {
                droppingItem = true;
            }
            if ( Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) ) {

                if ( equipment.size() >= 1 ) {
                    useItem(equipment.get(0));
                }
            }
            if ( Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT) ) {

                if ( equipment.size() == 2 ) {
                    useItem(equipment.get(1));
                }
            }
        }

    }

    public void dropItemFromInventoy(int index) {

        Point dropPosition = new Point(this.position.x + 1.0f, this.position.y);
        if ( inventory.getItem(index) != null ) {

            if ( level.isTileAccessible(dropPosition) ) {
                itemToDrop = inventory.getItem(index);
                itemToDrop.setPosition(dropPosition);
                itemToDrop.setIdlePoint(dropPosition);
                itemToDrop.setLevel(this.level);
                itemToDrop.setDropped(true);
                inventory.removeItem(index);
            } else {
                LOGGER.warning("Cant drop Item here");
            }

        }


    }

    public void checkForMoveinput() {
        //Temporären Point um den Held nur zu bewegen, wenn es keine Kollision gab

        Point newPosition = new Point(this.position);
        //Wenn die Taste W gedrückt ist, bewege dich nach oben
        if ( Gdx.input.isKeyPressed(Input.Keys.W) ) {
            newPosition.y += movementSpeed;
            LOGGER.fine("Hero pressed W, moving Up.");
        }
        //Wenn die Taste S gedrückt ist, bewege dich nach unten
        else if ( Gdx.input.isKeyPressed(Input.Keys.S) ) {
            newPosition.y -= movementSpeed;
            LOGGER.fine("Hero pressed S, moving Down.");
        }
        //Wenn die Taste D gedrückt ist, bewege dich nach rechts
        else if ( Gdx.input.isKeyPressed(Input.Keys.D) ) {
            this.direction = "right";
            this.activeAnimation = this.rightAnimation;
            newPosition.x += movementSpeed;
            LOGGER.fine("Hero pressed D, moving Right.");
        }
        //Wenn die Taste A gedrückt ist, bewege dich nach links
        else if ( Gdx.input.isKeyPressed(Input.Keys.A) ) {
            this.direction = "left";
            this.activeAnimation = this.leftAnimation;
            newPosition.x -= movementSpeed;
            LOGGER.fine("Hero pressed A, moving Left.");
        }
        //Wenn keine andere Taste gedrückt ist, bleib stehen und schau in die aktuelle Richtung
        else {
            if ( this.direction.equals("right") ) {
                this.activeAnimation = this.idleAnimation;
            } else {
                this.activeAnimation = this.idleLeftAnimation;
            }

        }
        //Wenn der übergebene Punkt betretbar ist, ist das nun die aktuelle Position
        if ( level.isTileAccessible(newPosition) )
            this.position = newPosition;


    }

    public void useItem(entities.GameItem item) {
        item.onUse(this);
    }

    public boolean isUsingWeapon() {
        return usingWeapon;
    }

    public void setUsingWeapon(boolean usingWeapon) {
        this.usingWeapon = usingWeapon;
    }

    public float getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(float attackRange) {
        this.attackRange = attackRange;
    }
}
