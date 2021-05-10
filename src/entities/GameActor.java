package entities;

import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IAnimatable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

/**
 * Repräsesentiert eine Game Enttity bzw. Charakter im Spiel
 *
 * <p>
 * Entählt alle Charakteranimationen, die Position im Dungeon und die Kampfeigenschaften wie Leben, stärke und Geschwindigkeit
 * </p>
 */
public abstract class GameActor implements IAnimatable, IEntity {
    //Positionen werden als float x und float y in der Klasse Point gespeichert.
    protected Point position;
    //Das Level ist eine Instanz der Klasse DungeonWorld
    protected DungeonWorld level;

    //Die Animationen für den entitites.GameActor
    protected Animation activeAnimation;
    protected Animation idleAnimation;
    protected Animation idleLeftAnimation;
    protected Animation rightAnimation;
    protected Animation leftAnimation;

    // STATS ----------------------------------------------------

    /**
     * Die maximale Lebenspunktenanzahl des GameActors
     */
    protected float maxHealth;
    /**
     * Die derzeitige Lenbenspunkte des GameActors
     */
    protected float health;
    /**
     * Die Kampfstärtke des GameActors
     */
    protected float strength;
    /**
     * Die Geschwindigkeit des GameActors
     */
    protected float movementSpeed;

    /**
     * Stellt alle Lebenspunkte zum MaxHealth wieder her
     */
    public void restoreHealth() {
        this.health = maxHealth;
    }

    // BASIC GETTERS AND SETTERS ----------------------------------------------------
    public float getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(float health) {
        this.maxHealth = health;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getStrength() {
        return strength;
    }

    public void setStrength(float strength) {
        this.strength = strength;
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }
    //END OF BASIC GETTERS AND SETTERS------------------------------------------------------

    /**
     * Lässt einen Charakter in eine zufällige Position des Dungeons spawnen
     **/
    public void setLevel(DungeonWorld level) {
        this.level = level;
        //siehe unten
        findRandomPostion();
    }

    /**
     * Findet eine neue Position im Dungeon, die zur Charakterposition wird.
     */
    public void findRandomPostion() {
        //Die Methode gibt ein zufälliges Bodenfeld im Dungeon zurück
        this.position = new Point(level.getRandomPointInDungeon());
    }

    /**
     * Gibt die derzeitige Animation des Charakters zurück
     *
     * @return Die aktuelle Charackter Animation
     */
    @Override
    public Animation getActiveAnimation() {
        return this.activeAnimation;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }


    @Override
    public boolean deleteable() {
        return false;
    }

    @Override
    public abstract void update();
}
