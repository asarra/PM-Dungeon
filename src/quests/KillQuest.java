package quests;

import entities.GameItem;
import entities.Hero;
import java.util.ArrayList;

public class KillQuest extends GameQuest {
  private final int killCountGoal;
  int heroStartkillCount;
  private int killCount;

  public KillQuest(
      Hero hero,
      String questTitle,
      ArrayList<GameItem> rewardItems,
      int rewardExp,
      int killCountGoal) {
    super(hero, questTitle, rewardItems, rewardExp);
    this.killCountGoal = killCountGoal;
    questDescription = "Kill " + killCountGoal + " enemies";
    killCount = 0;
    heroStartkillCount = hero.getKillCount();
    questProgress = killCountGoal - killCount + " enemies left to kill";
  }

  @Override
  public void update() {
    if (hero.getKillCount() - 1 >= killCount) {
      killCount++;
      questProgress = killCountGoal - killCount + " enemies left to kill";
    }
    questProgress = killCountGoal - killCount + " enemies left to kill";
    if (hero.getKillCount() - heroStartkillCount == killCountGoal) {
      complete();
    }
  }
}
