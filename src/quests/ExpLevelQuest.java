package quests;

import entities.GameItem;
import entities.Hero;
import java.util.ArrayList;

public class ExpLevelQuest extends GameQuest {
  private final int levelGoal;

  public ExpLevelQuest(
      Hero hero, String questTitle, ArrayList<GameItem> rewardItems, int rewardExp, int levelGoal) {
    super(hero, questTitle, rewardItems, rewardExp);
    this.levelGoal = levelGoal;
    questDescription = "Reach level " + levelGoal;
    questProgress = levelGoal - hero.getExpLevel() + " levels left";
  }

  @Override
  public void update() {
    if (hero.getExpLevel() >= levelGoal) {
      complete();
    }
    questProgress = levelGoal - hero.getExpLevel() + " levels left";
  }
}
