package entities;

import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IAnimatable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

/**
 * Repräsesentiert eine Game Enttity bzw. Charakter im Spiel
 *
 * <p>Entählt alle Charakteranimationen, die Position im Dungeon und die Kampfeigenschaften wie
 * Leben, stärke und Geschwindigkeit
 */
public abstract class GameActor implements IAnimatable, IEntity {
  // Positionen werden als float x und float y in der Klasse Point gespeichert.
  protected Point position;
  // Das Level ist eine Instanz der Klasse DungeonWorld
  protected DungeonWorld level;

  // Die Animationen für den entitites.GameActor
  protected Animation activeAnimation;
  protected Animation idleAnimation;
  protected Animation idleLeftAnimation;
  protected Animation rightAnimation;
  protected Animation leftAnimation;
  protected boolean isDeletable = false;

  // STATS ----------------------------------------------------

  /** Die maximale Lebenspunktenanzahl des GameActors */
  protected float maxHealth;
  /** Die derzeitige Lenbenspunkte des GameActors */
  protected float baseHealth;
  /** Die Kampfstärtke des GameActors */
  protected float baseStrength;
  /** Die Geschwindigkeit des GameActors */
  protected float movementSpeed;

  /** Stellt alle Lebenspunkte zum MaxHealth wieder her */
  public void restoreHealth() {
    this.baseHealth = maxHealth;
  }

  // BASIC GETTERS AND SETTERS ----------------------------------------------------
  public float getMaxHealth() {
    return maxHealth;
  }

  public void setMaxHealth(float health) {
    this.maxHealth = health;
  }

  public float getBaseHealth() {
    return baseHealth;
  }

  public void setBaseHealth(float baseHealth) {
    this.baseHealth = baseHealth;
  }

  public float getBaseStrength() {
    return baseStrength;
  }

  public void setBaseStrength(float baseStrength) {
    this.baseStrength = baseStrength;
  }

  public float getMovementSpeed() {
    return movementSpeed;
  }

  public void setMovementSpeed(float movementSpeed) {
    this.movementSpeed = movementSpeed;
  }
  // END OF BASIC GETTERS AND SETTERS------------------------------------------------------

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

  public void setPosition(Point position) {
    this.position = position;
  }

  @Override
  public abstract void update();

  @Override
  public boolean deleteable() {
    return isDeletable;
  }

  public void setdeletable(boolean isDeletable) {
    this.isDeletable = isDeletable;
  }

  public DungeonWorld getLevel() {
    return level;
  }

  /** Lässt einen Charakter in eine zufällige Position des Dungeons spawnen */
  public void setLevel(DungeonWorld level) {
    this.level = level;
    // siehe unten
    findRandomPostion();
  }

  /** Findet eine neue Position im Dungeon, die zur Charakterposition wird. */
  public void findRandomPostion() {
    // Die Methode gibt ein zufälliges Bodenfeld im Dungeon zurück
    this.position = new Point(level.getRandomPointInDungeon());
  }

  /**
   * Gibt dem GameActor eine neue Position
   *
   * @param x x- Koordinate
   * @param y y- Koordinate
   */
  public void setPosition(float x, float y) {
    this.position = new Point(x, y);
  }
}
