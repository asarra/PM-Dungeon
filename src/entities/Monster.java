package entities;

import com.badlogic.gdx.graphics.Texture;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class Monster extends Enemy {
    private static final Logger LOGGER = Logger.getLogger(Monster.class.getName());

    static {
        LOGGER.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.FINE);
        LOGGER.addHandler(consoleHandler);
        LOGGER.setLevel(Level.FINE);

        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler("logs/" + Monster.class.getName() + ".log");
            fileHandler.setFormatter(new SimpleFormatter());
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        LOGGER.addHandler(fileHandler);
    }

    public Monster() {
        health = 100.0f;
        strength = 100.0f;

        //ArrayLists für die Animationen erstellen
        List<Texture> idle = new ArrayList<>();
        List<Texture> idleLeft = new ArrayList<>();
        List<Texture> right = new ArrayList<>();
        List<Texture> left = new ArrayList<>();
        //Laden der Texturen für die jeweilige Animation
        idle.add(new Texture("assets/textures/characters/monsters/monster_idle_anim_f0.png"));
        idle.add(new Texture("assets/textures/characters/monsters/monster_idle_anim_f1.png"));
        idle.add(new Texture("assets/textures/characters/monsters/monster_idle_anim_f2.png"));
        idle.add(new Texture("assets/textures/characters/monsters/monster_idle_anim_f3.png"));

        idleLeft.add(new Texture("assets/textures/characters/monsters/monster_idle_anim_left_f0.png"));
        idleLeft.add(new Texture("assets/textures/characters/monsters/monster_idle_anim_left_f1.png"));
        idleLeft.add(new Texture("assets/textures/characters/monsters/monster_idle_anim_left_f2.png"));
        idleLeft.add(new Texture("assets/textures/characters/monsters/monster_idle_anim_left_f3.png"));

        right.add(new Texture("assets/textures/characters/monsters/monster_run_anim_f0.png"));
        right.add(new Texture("assets/textures/characters/monsters/monster_run_anim_f1.png"));
        right.add(new Texture("assets/textures/characters/monsters/monster_run_anim_f2.png"));
        right.add(new Texture("assets/textures/characters/monsters/monster_run_anim_f3.png"));

        left.add(new Texture("assets/textures/characters/monsters/monster_run_anim_left_f0.png"));
        left.add(new Texture("assets/textures/characters/monsters/monster_run_anim_left_f1.png"));
        left.add(new Texture("assets/textures/characters/monsters/monster_run_anim_left_f2.png"));
        left.add(new Texture("assets/textures/characters/monsters/monster_run_anim_left_f3.png"));
        this.idleAnimation = new Animation(idle, 4);
        this.idleLeftAnimation = new Animation(idleLeft, 4);
        this.rightAnimation = new Animation(right, 4);
        this.leftAnimation = new Animation(left, 4);
        //Idle Animation als aktive Animation setzten
        this.activeAnimation = idleAnimation;
        LOGGER.log(Level.INFO, "Monster Character created");
    }
}
