package ui;

import com.badlogic.gdx.graphics.Texture;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IHUDElement;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import entities.GameItem;

public class Item implements IHUDElement {

    private final GameItem item;
    private final float abstand = 0.75f;
    private final int index;
    private final float scaleFactor = 0.33f;
    private final float x = 3.4f;
    private final float y = 2.5f;
    private float abstandy = 0f;

    Item(GameItem item, int i, float j) {
        this.item = item;
        this.index = i;
        this.abstandy = j;
        //this.amountOfItems = amountOfItems; //in Manager beutzen, um Fensterbreite anzupassen und von hier entfernen
    }

    @Override
    public Point getPosition() {
        if ( this.index % 3 == 0 ) {
            return new Point(this.x - this.abstand, this.y - this.abstandy);
        } else if ( this.index % 3 == 1 ) {
            return new Point(this.x, this.y - this.abstandy);
        } else if ( this.index % 3 == 2 ) {
            return new Point(this.x + this.abstand, this.y - this.abstandy);
        }
        return new Point(-100, -100);//nicht sehbar
    }

    @Override
    public Texture getTexture() {
        return new Texture("" + item.getTexture().toString());
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
