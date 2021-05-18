package ui;

import com.badlogic.gdx.graphics.Texture;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IHUDElement;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import entities.Hero;

public class HeartIcon implements IHUDElement {

  private final Hero hero;
  int state;
  float temp_max, temp_current;

  public HeartIcon(Hero hero) {
    this.hero = hero;
  }

  @Override
  public Point getPosition() {
    // festlegen der Position
    return new Point(0.5f, 4.5f);
  }

  @Override
  public Texture getTexture() {
    // laden der Textur
    this.calculateState();

    if (state == 3) return new Texture("./assets/textures/ui/ui_heart_full.png");
    else if (state == 2) return new Texture("./assets/textures/ui/ui_heart_half.png");
    else return new Texture("./assets/textures/ui/ui_heart_empty.png");
  }

  public void calculateState() {
    temp_max = hero.getMaxHealth();
    temp_current = hero.getBaseHealth();
    if (temp_current == temp_max) state = 3;
    else if (temp_current < temp_max && temp_current > 0) state = 2;
    else state = 1;
  }
}
