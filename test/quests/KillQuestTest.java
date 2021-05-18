package quests;

import entities.GameItem;
import entities.Hero;
import entities.items.ItemFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class KillQuestTest {

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
    void complete() {
    }

    @Test
    void updateShouldUpdateKillCount() {
    }
}