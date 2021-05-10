package commands;

import com.badlogic.gdx.graphics.Texture;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;
import entities.Hero;
import entities.Projectile;
import interfaces.GameCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasse für die Feuerball Fähigkeit
 **/
public class FireBallCommand implements GameCommand {
    private final Hero hero;

    public FireBallCommand(Hero hero) {
        this.hero = hero;
    }

    @Override
    public void execute() {
        List<Texture> textures = new ArrayList<>();
        textures.add(new Texture("assets/textures/projectiles/fireball/FB500-1.png"));
        textures.add(new Texture("assets/textures/projectiles/fireball/FB500-2.png"));
        textures.add(new Texture("assets/textures/projectiles/fireball/FB500-3.png"));
        textures.add(new Texture("assets/textures/projectiles/fireball/FB500-4.png"));
        textures.add(new Texture("assets/textures/projectiles/fireball/FB500-5.png"));

        Animation fireBallAnimation = new Animation(textures, 5);

        Projectile fireBall = new Projectile(fireBallAnimation, hero.getPosition(), 30, 20.0f, 0.02f);
        hero.setCurrentProjectile(fireBall);
        fireBall.fire(hero.getDirection());

    }

}
