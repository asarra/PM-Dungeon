package entities.items;

import entities.GameItem;
import java.util.ArrayList;

/**
 * Bag Klasse für die Darstellung eines Tachenitems Eine Tasche kann nur Items eines Itemtypshaben
 *
 * @param <T> Itemtyp
 */
public class Bag<T extends GameItem> {

  private final ArrayList<T> items;
  private int capacity;

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
    if (getSize() + 1 > this.capacity) {
      System.out.println("inventory full");
    } else {
      this.items.add(item);
    }
  }

  /**
   * Item an bestimmer Stelle geben
   *
   * @param index Position des Items in der Tasche
   * @return Item
   */
  public T getItem(int index) {
    if (index < items.size()) {
      return items.get(index);
    }
    return null;
  }

  /**
   * Item aus der Tasche entfernen
   *
   * @param index Position des Items in der Tasche
   */
  public void removeItem(int index) {
    if (this.isEmpty()) {
      System.out.println("Inventory empty");
    } else {
      if (index < items.size()) {
        this.items.remove(index);
      }
    }
  }

  public boolean isEmpty() {
    return this.items.isEmpty();
  }

  /**
   * @return
   * @deprecated
   */
  public String returnItemNames() {
    String temp_string = "";
    for (T item : this.items) {
      temp_string += " " + item.getName();
    }
    return temp_string;
  }
}
