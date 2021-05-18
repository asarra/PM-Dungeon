package entities;

import com.badlogic.gdx.graphics.Texture;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;
import entities.items.ItemFactory;
import entities.items.Weapons;
import game.CollisionVisitor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import quests.ExpLevelQuest;
import quests.GameQuest;
import quests.KillQuest;

public class DungeonQuest extends GameActor {
    Animation idleAnimation;
    boolean active;
    boolean hidden;
    GameQuest quest;

    public DungeonQuest(Hero hero) {
        List<Texture> idle = new ArrayList<>();
        idle.add(new Texture("assets/textures/questIcon/tile000.png"));
        idle.add(new Texture("assets/textures/questIcon/tile001.png"));
        idle.add(new Texture("assets/textures/questIcon/tile002.png"));
        idleAnimation = new Animation(idle, 13);
        this.activeAnimation = idleAnimation;


        Random random = new Random();
        int questOption = random.nextInt(2);
        switch (questOption) {
            case 0 -> {
                ArrayList<GameItem> rewardItems = new ArrayList<>();
                int rewardExp = hero.getExpToNextLevel() / 3;
                quest = new KillQuest(hero, "Monsterkiller", rewardItems, rewardExp, hero.getExpLevel() * 5);
            }
            case 1 -> {
                ArrayList<GameItem> rewardItems = new ArrayList<>();
                ItemFactory itemFactory = new ItemFactory();
                int weapontype = random.nextInt(6);
                if (weapontype == 0) {
                    rewardItems.add(itemFactory.getWeapon(Weapons.SWORD));
                } else if (weapontype == 1) {
                    rewardItems.add(itemFactory.getWeapon(Weapons.GOLDEN_SWORD));
                } else if (weapontype == 2) {
                    rewardItems.add(itemFactory.getWeapon(Weapons.HAMMER));
                } else if (weapontype == 3) {
                    rewardItems.add(itemFactory.getWeapon(Weapons.CLEAVER));
                } else if (weapontype == 4) {
                    rewardItems.add(itemFactory.getWeapon(Weapons.DUEL_SWORD));
                } else {
                    rewardItems.add(itemFactory.getWeapon(Weapons.KATANA));
                }
                int rewardExp = 0;
                int goalExp = hero.getExpLevel() + 1;

                quest = new ExpLevelQuest(hero, "ExpCollector", rewardItems, rewardExp, goalExp);
            }
        }
    }

    @Override
    public void update() {
        this.draw();
    }

    public void accept(CollisionVisitor visitor) {
        visitor.visit(this);
    }

    public GameQuest getQuest() {
        return quest;
    }

    public void setRemovable(boolean removable) {

    }

}
