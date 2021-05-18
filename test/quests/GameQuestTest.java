package quests;

import entities.GameItem;
import entities.Hero;
import entities.items.ItemFactory;
import entities.items.Weapon;
import entities.items.Weapons;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameQuestTest {
    Hero hero;
    int goalExp;
    int rewardExp;
    ItemFactory itemFactory;
    ArrayList<GameItem> rewardItems= new ArrayList<>();
    @BeforeEach
    void setUp() {
        hero= new Hero("Test");
        itemFactory =new ItemFactory();
        goalExp = hero.getExpLevel()+1;
        rewardExp=0;

    }

    @Test
    void completeShouldGiveItems() {
        rewardItems.add(new Weapon("Sword", 20,2.0f) {
        });
        ExpLevelQuest quest = new ExpLevelQuest(hero, "ExpCollector", rewardItems, rewardExp,goalExp);
        hero.addQuest(quest);
        quest.complete();
        hero.updateItemsToAdd();
        assertTrue(hero.getInventory().getItems().contains(rewardItems.get(0)));
    }
    @Test
    void completeShouldGiveXP(){
        int rewardExp=30;

        ExpLevelQuest quest = new ExpLevelQuest(hero, "ExpCollector", rewardItems, rewardExp,goalExp);

        quest.complete();

    }
    @Test
    void completeShouldAddItemsToStackWhenInventoryFull(){

    }

//ich glaube getters und setters sollten nicht getestet werden (Zeitverschwerndung

    @Test
    void getQuestTitle() {
        ExpLevelQuest quest = new ExpLevelQuest(hero, "ExpCollector", rewardItems, rewardExp,goalExp);
        assertEquals("ExpCollector", quest.getQuestTitle());
    }

    @Test
    void getRewardExp() {
        rewardExp=20;
        ExpLevelQuest quest = new ExpLevelQuest(hero, "ExpCollector", rewardItems, rewardExp,goalExp);

        assertEquals(20, quest.getRewardExp());
    }

    @Test
    void testToString() {
        ExpLevelQuest quest = new ExpLevelQuest(hero, "ExpCollector", rewardItems, rewardExp,goalExp);

        String s = "ExpCollector" +"\n"+
                " Reach level " + goalExp +"\n"+
                "1 levels left" +"\n";

        assertEquals(s,quest.toString());
    }
    @Test
    void isCompletedShoulbeRemovedFromHero() {
        ArrayList<GameItem> rewardItems= new ArrayList<>();
        ExpLevelQuest quest = new ExpLevelQuest(hero, "ExpCollector", rewardItems, rewardExp,goalExp);
        hero.addQuest(quest);
        quest.complete();
        hero.notifyQuests();
        assertFalse(hero.getQuests().contains(quest));
    }

    @Test
    void heroShouldHaveQuestInList(){
        ArrayList<GameItem> rewardItems= new ArrayList<>();
        ExpLevelQuest quest = new ExpLevelQuest(hero, "ExpCollector", rewardItems, rewardExp,goalExp);
        hero.addQuest(quest);;
        assertTrue(hero.getQuests().contains(quest));
    }

    @Test
    void isCompletedShoulbeCompleted() {

        int rewardExp =0;
        int goalExp = hero.getExpLevel()+1;
        ArrayList<GameItem> rewardItems= new ArrayList<>();
        ExpLevelQuest quest = new ExpLevelQuest(hero, "ExpCollector", rewardItems, rewardExp,goalExp);
        hero.addQuest(quest);
        hero.addExpPoints(100);
        hero.notifyQuests();
        assertTrue(quest.isCompleted());
    }
}