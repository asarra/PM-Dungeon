package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.game.GameSetup;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IAnimatable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import game.CollisionVisitor;
import interfaces.ItemVisitor;
import java.util.ArrayList;

/** Klasse zur Darstellung von Items allgemein im Spiel */
public abstract class GameItem implements IEntity, IAnimatable {
  protected String name;
  protected Texture sprite;
  protected Point position;
  protected Animation animation;
  protected DungeonWorld level;
  protected Point targetPoint;
  protected Point idlePoint;
  protected boolean dropped;

  /**
   * Konstruktor der GameItem Klasse
   *
   * @param name Name des Items
   * @param sprite Textur für das Item
   */
  public GameItem(String name, Texture sprite) {

    this.name = name;
    this.sprite = sprite;
    this.targetPoint = null;
    ArrayList<Texture> textures = new ArrayList<>();
    textures.add(sprite);
    textures.add(sprite);
    textures.add(sprite);
    textures.add(sprite);

    this.animation = new Animation(textures, 4);
  }

  protected GameItem() {}

  public Point getTargetPoint() {
    return targetPoint;
  }

  public void setTargetPoint(Point targetPoint) {
    this.targetPoint = targetPoint;
  }

  public void setPosition(float x, float y) {
    this.position = new Point(x, y);
  }

  public boolean isDropped() {
    return dropped;
  }

  public void setDropped(boolean dropped) {
    this.dropped = dropped;
  }

  public void setIdlePoint(Point position) {
    this.idlePoint = position;
  }

  @Override
  public Animation getActiveAnimation() {
    return animation;
  }

  @Override
  public Texture getTexture() {
    return sprite;
  }

  public void setLevel(DungeonWorld level) {
    this.level = level;
  }

  @Override
  public Point getPosition() {
    return this.position;
  }

  public void setPosition(Point position) {
    this.position = position;
  }

  public abstract void accept(ItemVisitor visitor);

  public abstract void onUse(Hero hero);

  public abstract void onEquip(Hero hero);

  public abstract void onUnequip(Hero hero);

  @Override
  public void update() {
    this.draw();
  }

  @Override
  public boolean deleteable() {
    return false;
  }

  public String getName() {
    return name;
  }

  public void accept(CollisionVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public void draw(float xOffset, float yOffset) {

    Texture texture = this.getTexture();
    Sprite sprite = new Sprite(texture);
    // this will resize the texture. this is setuped for the textures used in the thesis
    sprite.setSize(0.9f, 0.9f);
    sprite.setOrigin(0.9f / 2, 0.9f / 2);

    // where to draw the sprite
    Point position = this.getPosition();
    sprite.setPosition(position.x + xOffset, position.y + yOffset);

    // need to be called before drawing
    GameSetup.batch.begin();
    // draw sprite
    sprite.draw(GameSetup.batch);
    // need to be called after drawing
    GameSetup.batch.end();
  }
}
