package entities.items;

import entities.GameItem;

import java.util.ArrayList;

public class Bag<T extends GameItem> {

    int capacity;

    ArrayList<T> items;

    public Bag(int capacity) {
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

    public void addItem(T item) {
        if ( getSize() + 1 > this.capacity ) {
            System.out.println("inventory full");
        } else {
            this.items.add(item);
        }
    }

    public T getItem(int index) {
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

    public String returnItemNames() {
        String temp_string = "";
        for ( T item : this.items ) {
            temp_string += " " + item.getName();
        }
        return temp_string;
    }

}

