package ui;

import de.fhbielefeld.pmdungeon.vorgaben.graphic.HUD;
import entities.GameItem;
import entities.Hero;

import java.util.ArrayList;

public class InventoryWindowManager {

    private final Hero hero;
    private boolean state = false;
    private final ArrayList<Item> itemlist = new ArrayList<>();
    private HUD hud;
    private int i, amountOfItems;
    private float j;
    private final InventoryWindow inventoryWindow = new InventoryWindow(0);
    private boolean isWindowOpen = false;

    public InventoryWindowManager(Hero hero) {
        this.hero = hero;
    }

    public void open() {
        state = true;
        this.show();
    }

    private void show() {
        if ( state & itemlist.size() == 0 ) {
            i = 0;
            j = 0f;
            amountOfItems = hero.getInventory().getSize();
            inventoryWindow.setScaleFactor(6);//Um es wieder sichtbar zu machen, falls es unsichtbar war
            //hud.addHudElement(inventoryWindow);
            this.isWindowOpen = true;
            for ( GameItem item : hero.getInventory().getItems() ) {
                if ( i >= 3 && i < 6 )
                    j = 0.5f;
                else if ( i >= 6 && i < 9 )
                    j = 1f;
                else if ( i >= 9 && i < 12 )
                    j = 1.5f;
                else if ( i >= 12 && i < 15 )
                    j = 2f;
                itemlist.add(new Item(item, i, j));
                hud.addHudElement(itemlist.get(i));
                i++;
            }
        }
    }

    public void close() {
        state = false;
        this.doNotShow();
    }

    private void doNotShow() {
        if ( this.isWindowOpen ) {
            if ( itemlist.size() > 0 ) {
                for ( i = 0; i < itemlist.size(); i++ )
                    hud.removeHudElement(itemlist.get(i));
                itemlist.clear();
            }
            inventoryWindow.setScaleFactor(0); // Workaround: Es wird jetzt unsichtbar.
            //hud.removeHudElement(inventoryWindow); //Das entfernt, warum auch immer nicht das Fenster im GUI
            this.isWindowOpen = false;
        }
    }

    public void setup(HUD hud) {
        this.hud = hud;
        hud.addHudElement(inventoryWindow);
    }

}