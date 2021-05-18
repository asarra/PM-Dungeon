package quests;

import de.fhbielefeld.pmdungeon.desktop.DesktopLauncher;
import entities.GameItem;
import entities.Hero;
import entities.Monster;
import entities.items.ItemFactory;
import game.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ExpLevelQuestTest {

    Hero hero;
    int rewardExp;
    int goalExp;
    @BeforeEach
    void setUp() {
        hero= new Hero("Test");
        ItemFactory itemFactory =new ItemFactory();
        rewardExp =0;
        goalExp = hero.getExpLevel()+1;
    }

    @Test
    void isCompletedShoulNoteCompleted() {

        int rewardExp =0;
        int goalExp = hero.getExpLevel()+1;
        ArrayList<GameItem> rewardItems= new ArrayList<>();
        ExpLevelQuest quest = new ExpLevelQuest(hero, "ExpCollector", rewardItems, rewardExp,goalExp);
        hero.addQuest(quest);
        assertFalse(quest.isCompleted());
    }


}