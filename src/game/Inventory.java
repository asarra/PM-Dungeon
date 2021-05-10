package game;

import entities.GameItem;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Inventory {


    int capacity;

    ArrayList<GameItem> items;

    public Inventory(int capacity) {
        items = new ArrayList<>();
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isFull() {
        return this.getSize() == capacity;
    }

    public int getSize() {
        return this.items.size();
    }

    public void addItem(GameItem item) {
        if ( getSize() + 1 > this.capacity ) {
            System.out.println("inventory full");
        } else {
            this.items.add(item);
        }
    }

    public GameItem getItem(int index) {
        if ( index < items.size() ) {
            return items.get(index);
        }
        return null;
    }

    public void removeItem(int index) {
        if ( this.isEmpty() ) {
            System.out.println("Inventory empty");
        } else {
            if ( index < items.size() ) {
                this.items.remove(index);
            }
        }

    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    public void logInventory(Logger logger) {
        InventoryLogger inventoryLogger = new InventoryLogger(logger);
        for ( GameItem item : items ) {
            item.accept(inventoryLogger);
        }
    }

    public String returnItemNames() {
        String temp_string = "";
        for ( GameItem item : this.items ) {
            temp_string += " " + item.getName();
        }
        return temp_string;
    }

    public ArrayList<GameItem> getItems() {
        return items;
    }

}
