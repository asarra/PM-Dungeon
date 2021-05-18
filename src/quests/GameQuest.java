package quests;

import entities.GameItem;
import entities.Hero;
import java.util.ArrayList;

public abstract class GameQuest {
  protected Hero hero;
  protected String questTitle;
  protected String questDescription;
  protected String questProgress;
  protected boolean completed;
  protected ArrayList<GameItem> rewardItems;
  protected int rewardExp;

  public GameQuest(Hero hero, String questTitle, ArrayList<GameItem> rewardItems, int rewardExp) {
    this.hero = hero;
    this.questTitle = questTitle;
    this.rewardItems = rewardItems;
    this.rewardExp = rewardExp;
  }

  public abstract void update();

  void complete() {
    hero.addExpPoints(rewardExp);
    System.out.println(rewardItems.toString());
    if (!rewardItems.isEmpty()) {
      hero.itemsToAdd.addAll(rewardItems);
    }
    completed = true;
  }

  public String getQuestTitle() {
    return questTitle;
  }

  public String getQuestDescription() {
    return questDescription;
  }

  public String getQuestProgress() {
    return questProgress;
  }

  public boolean isCompleted() {
    return completed;
  }

  public ArrayList<GameItem> getRewardItems() {
    return rewardItems;
  }

  public int getRewardExp() {
    return rewardExp;
  }

  @Override
  public String toString() {
    return "" + questTitle + "\n " + questDescription + "\n" + questProgress + "\n";
  }
}
