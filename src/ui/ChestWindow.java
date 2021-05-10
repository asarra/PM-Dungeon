package ui;

import com.badlogic.gdx.graphics.Texture;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IHUDElement;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public class ChestWindow implements IHUDElement {

    private int scaleFactor = 6;


    ChestWindow(int scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    public void setScaleFactor(int scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    @Override
    public Point getPosition() {
        //festlegen der Position
        return new Point(2f, 1f);
    }

    @Override
    public Texture getTexture() {
        //laden der Textur
        return new Texture("./assets/textures/ui/chest.png");
    }

    @Override
    public float getWidth() {
        return scaleFactor * 0.5F;
    }

    @Override
    public float getHeight() {
        return scaleFactor * ((float) (this.getTexture().getHeight() / 2));
    }

}