package entities;

import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import game.CollisionVisitor;

/**
 * <p> Abstrakte Klasse zur Darstellung eines Gegners im Spiel </p>
 */
public abstract class Enemy extends GameActor {
    /**
     * Zielposition des Gegners
     */
    protected Point targetPoint = null;

    /**
     * Gibt dem Gegner ein neuer zufälliger Punkt im Dungeon als Ziel
     */
    protected void resetTargetPoint() {
        this.targetPoint = new Point(level.getRandomPointInDungeon());
    }

    /**
     * Prüft, ob der Gegner am Leben ist
     *
     * @return Ob Lebenspunkte des Gegners über 0 sind
     */
    public boolean isAlive() {
        return this.health > 0;
    }

    @Override
    public void update() {

        //zeichnet das entitites.Monster
        this.draw();

        //Temporären Point um das entitites.Monster nur zu bewegen, wenn es keine Kollision gab
        Point newPosition = new Point(this.position);

        //Unser Gegner soll sich pro Schritt um einen Faktor von 0.01 bewegen.
        float movementSpeed = 0.01f;

        //Wenn es keinen Targetpoint gibt (Am Start des Programmes), wird es gesetzt
        if ( this.targetPoint == null )
            this.resetTargetPoint();

        newPosition.x += movementSpeed * (this.targetPoint.x - newPosition.x);
        newPosition.y += movementSpeed * (this.targetPoint.y - newPosition.y);

        //Wenn entitites.Monster an die ZielKoordinate kommt, entsprechende Idle animation setzten
        if ( this.position == this.targetPoint ) {
            if ( this.activeAnimation == rightAnimation ) {
                this.activeAnimation = idleAnimation;
            } else {
                this.activeAnimation = idleLeftAnimation;
            }
        }

        //Wenn der übergebene Punkt nicht betretbar ist oder die Zielkoordinate und die jetzige Koordinate gleich sind, wird die Zielkoordinate erneuert
        //Musste es umschreiben, da sonst der untere else if die Checks im jetzigen if blockiert. (Saß Stunden daran, um herauszufinden, dass es daran lag)
        if ( !level.isTileAccessible(newPosition) | (Math.round(this.position.x) == this.targetPoint.x & Math.round(this.position.y) == this.targetPoint.y) ) {

            this.resetTargetPoint();
        }
        //Wenn der übergebene Punkt betretbar ist, ist das nun die aktuelle Position
        else if ( level.isTileAccessible(newPosition) ) {
            //Animation für die Blickrichtungen setzen anhand der eigenen und die Zielkoordinate
            if ( newPosition.x > this.position.x ) {
                this.activeAnimation = rightAnimation;
            } else if ( newPosition.x < this.position.x ) {
                this.activeAnimation = leftAnimation;
            } else {
                this.activeAnimation = idleAnimation;
            }
            this.position = newPosition;
        }


    }

    //Die Position des Gegners zurückgeben (sehr wichtigh für update())
    @Override
    public Point getPosition() {
        if ( this.position != null ) {
            return this.position;
        } else {
            this.findRandomPostion();
            return this.position;
        }
    }

    public void accept(CollisionVisitor visitor) {
        visitor.visit(this);
    }
}
