package entities;

import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IAnimatable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import game.CollisionVisitor;

/**
 * Klasse zur Darstellung eines Projektils im Spiel
 */
public class Projectile implements IEntity, IAnimatable {
    private final int damage;
    private final float range;
    private final float speed;
    private final Animation activeAnimation;
    private Point position;
    private Point targetPosition = null;

    /**
     * Konstruktor des Projektils
     *
     * @param animation Animation des Projektils
     * @param position  StartPosition des Projektils
     * @param damage    Schaden des Projektils
     * @param range     Reichweite des Projektils
     * @param speed     Geschwindigkeit des Projektils
     */
    public Projectile(Animation animation, Point position, int damage, float range, float speed) {
        this.activeAnimation = animation;
        this.position = position;
        this.damage = damage;
        this.range = range;
        this.speed = speed;
    }

    @Override
    public Animation getActiveAnimation() {
        return activeAnimation;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void update() {

        this.draw();
        if ( this.targetPosition != null ) {
            Point newPosition = new Point(this.position);
            newPosition.x += speed * (this.targetPosition.x - newPosition.x);
            newPosition.y += speed * (this.targetPosition.y - newPosition.y);
            this.position = newPosition;
        }

    }

    @Override
    public boolean deleteable() {
        return false;
    }

    /**
     * Feuert das Projektil abhängig
     *
     * @param direction Richung in der das Projektil fliegen soll
     */
    public void fire(String direction) {

        if ( direction.equals("up") ) {
            this.targetPosition = new Point(this.position.x, this.position.y + range);
        }
        if ( direction.equals("down") ) {
            this.targetPosition = new Point(this.position.x, this.position.y - range);
        }
        if ( direction.equals("left") ) {
            this.targetPosition = new Point(this.position.x - range, this.position.y);
        }
        if ( direction.equals("right") ) {
            this.targetPosition = new Point(this.position.x + range, this.position.y);
        }


    }


    public int getDamage() {
        return damage;
    }

    public void accept(CollisionVisitor visitor) {
        visitor.visit(this);
    }
}
