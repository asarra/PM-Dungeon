package commands;

import entities.Hero;
import interfaces.GameCommand;

/**
 * Klasse für die Teleportationfähigkeit des heldens
 */
public class TeleportCommand implements GameCommand {
    private final Hero hero;
    private final int teleportDistance = 2;

    public TeleportCommand(Hero hero) {
        this.hero = hero;
    }

    @Override
    public void execute() {
        float heroX = hero.getPosition().x;
        float heroY = hero.getPosition().y;
        //Feuerball in die Blickrichtung des Heldens fliegen lassen
        if ( hero.getDirection().equals("left") ) {
            if ( hero.getLevel().isTileAccessible(Math.round(heroX - teleportDistance), Math.round(heroY)) ) {

                hero.setPosition(heroX - teleportDistance, heroY);
            }
        }
        if ( hero.getDirection().equals("right") ) {
            if ( hero.getLevel().isTileAccessible(Math.round(heroX + teleportDistance), Math.round(heroY)) ) {

                hero.setPosition(heroX + teleportDistance, heroY);
            }
        }
        if ( hero.getDirection().equals("up") ) {
            if ( hero.getLevel().isTileAccessible(Math.round(heroX), Math.round(heroY + teleportDistance)) ) {

                hero.setPosition(heroX, heroY + teleportDistance);
            }
        }
        if ( hero.getDirection().equals("down") ) {
            if ( hero.getLevel().isTileAccessible(Math.round(heroX), Math.round(heroY - teleportDistance)) ) {

                hero.setPosition(heroX, heroY - teleportDistance);
            }
        }

    }


}
