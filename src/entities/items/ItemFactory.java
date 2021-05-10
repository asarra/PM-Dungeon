package entities.items;

import com.badlogic.gdx.graphics.Texture;
import entities.EnemyFactory;

import java.io.IOException;
import java.util.logging.*;

/**
 * Factory für die Erstellung von Items
 */
public class ItemFactory {
    private static final Logger LOGGER = Logger.getLogger(ItemFactory.class.getName());

    static {
        LOGGER.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.CONFIG);
        LOGGER.addHandler(consoleHandler);

        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler("logs/" + EnemyFactory.class.getName() + ".log");
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.FINE);
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        LOGGER.addHandler(fileHandler);
        LOGGER.setLevel(Level.CONFIG);
    }

    /**
     * Erstellt eine neue Waffe
     *
     * @param weaponType Waffe aus dem Weapon Enum
     * @return Die erstelle Waffe
     */
    public Weapon getWeapon(Weapons weaponType) {
        Weapon weapon;
        switch ( weaponType ) {
            case SWORD -> weapon = new Weapon("Sword", new Texture("assets/textures/items/weapon_regular_sword.png"), 40, 1.0f);
            case AXE -> weapon = new Weapon("Axe", new Texture("assets/textures/items/weapon_axe.png"), 70, 0.5f);
            case HAMMER -> weapon = new Weapon("Hammer", new Texture("assets/textures/items/weapon_hammer.png"), 70, 0.8f);
            case BATTON_WITH_SPIKES -> weapon = new Weapon("Batton with spikes", new Texture("assets/textures/items/weapon_baton_with_spikes.png"), 70, 0.5f);
            case KATANA -> weapon = new Weapon("Katana", new Texture("assets/textures/items/weapon_katana.png"), 50, 2.0f);
            case CLEAVER -> weapon = new Weapon("Cleaver", new Texture("assets/textures/items/weapon_cleaver.png"), 60, 0.5f);
            case BIG_HAMMER -> weapon = new Weapon("Big Hammer", new Texture("assets/textures/items/weapon_big_hammer.png"), 70, 1.5f);
            case DUEL_SWORD -> weapon = new Weapon("Duel Sword", new Texture("assets/textures/items/weapon_duel_sword.png"), 80, 01.5f);
            case GOLDEN_SWORD -> weapon = new Weapon("Golden sword", new Texture("assets/textures/items/weapon_golden_sword.png"), 100, 1.0f);
            default -> throw new IllegalStateException("invalid Weapon. WeaponType not found");

        }
        return weapon;

    }

    /**
     * Erstellt ein neuer Trank her
     *
     * @return Der neue Trank
     */
    public Potion getPotion() {
        return new Potion("Healing Potion", new Texture("assets/textures/items/flask_big_green.png"), 50);
    }

}
