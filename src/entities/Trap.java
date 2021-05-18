package entities;

import com.badlogic.gdx.graphics.Texture;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;
import game.CollisionVisitor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Klasse zur Darstellung einer Falle im Spiel Fallen spawnen in zufälligen Positionen im Dungeon
 * über die Eigenschaft active lässt sich die Falle scharfstellen bzw entschärfen über die
 * Eigenschaft hidden lässt sich die Falle sichtbar oder unsichtbar spawnen
 */
public class Trap extends GameActor {
  Animation idleAnimation;
  boolean active;
  boolean hidden;

  /**
   * eine Arraylist wird mit den einzelnen Texturen belegt um damit die Animation der Falle zur
   * erzeugen
   */
  public Trap() {
    List<Texture> idle = new ArrayList<>();
    idle.add(new Texture("assets/textures/traps/fireTrap/tile000.png"));
    idle.add(new Texture("assets/textures/traps/fireTrap/tile001.png"));
    idle.add(new Texture("assets/textures/traps/fireTrap/tile002.png"));
    idle.add(new Texture("assets/textures/traps/fireTrap/tile003.png"));
    idle.add(new Texture("assets/textures/traps/fireTrap/tile004.png"));
    idle.add(new Texture("assets/textures/traps/fireTrap/tile005.png"));
    idle.add(new Texture("assets/textures/traps/fireTrap/tile006.png"));
    idle.add(new Texture("assets/textures/traps/fireTrap/tile007.png"));
    idle.add(new Texture("assets/textures/traps/fireTrap/tile008.png"));
    idle.add(new Texture("assets/textures/traps/fireTrap/tile009.png"));
    idle.add(new Texture("assets/textures/traps/fireTrap/tile010.png"));
    idle.add(new Texture("assets/textures/traps/fireTrap/tile011.png"));
    idle.add(new Texture("assets/textures/traps/fireTrap/tile012.png"));
    idle.add(new Texture("assets/textures/traps/fireTrap/tile013.png"));

    idleAnimation = new Animation(idle, 13);
    this.activeAnimation = idleAnimation;
    Random random = new Random();
    int visibility = random.nextInt(random.nextInt(2 - 1 + 1) + 1);
    hidden = visibility == 1;

    System.out.println(hidden);
  }

  /** draw Methode der Falle wird nur ausgeführt wenn die Eigenschaft hidden nicht erfüllt ist */
  @Override
  public void update() {
    if (!hidden) {
      this.draw();
    }
    // System.out.println(position.x+" "+ position.y);

  }

  @Override
  public boolean deleteable() {
    return false;
  }

  public void accept(CollisionVisitor visitor) {
    visitor.visit(this);
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public boolean isHidden() {
    return hidden;
  }

  public void setHidden(boolean hidden) {
    this.hidden = hidden;
  }
}
