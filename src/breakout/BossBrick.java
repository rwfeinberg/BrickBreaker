package breakout;

/**
 * @author Caleb Getahun
 * @author Ryan Feinberg
 *
 * The BossBrick class is a representation of the boss character present in Level Three needed to defeat
 * and complete the entire game
 *
 * BossBrick will only be called if the character passed into the grid is a 0, meaning there may be some
 * implicit assumption to be made, but this is in line with our design.
 *
 * Dependencies: We rely on the Brick class and Overriding methods from it to be able to customize the
 * actions that can be called on BossBrick. We also depend on the JavaFx scene and animation to customize
 *
 * This would be used in the Grid Class to represent the block to be used in the boss level (level 3)
 * Ex: Brick boss = new BossBrick(Main.BOSS_STARTING_X, Main.BOSS_STARTING_Y, Main.BOSS_WIDTH,
 *                 Main.BOSS_HEIGHT);
 *
 */

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class BossBrick extends Brick {

  private int myHealth = Main.BOSS_STARTING_HEALTH;

  public BossBrick(double xPos, double yPos, double width, double height) {
    super(xPos, yPos, width, height);
    this.setFill(Main.BOSS_COLOR);
  }

  @Override
  public int getMyType() {
    return 10;
  }

  //Darkens its color the lower its health
  public void damage() {
    this.myHealth--;
    this.setFill(Color.RED);
    Timeline timeline = new Timeline(
        new KeyFrame(Duration.millis(Main.BOSS_FLASH_DURATION * 1000),
            ae -> this.setFill(Main.BOSS_COLOR)));
    timeline.play();
  }

  public int getMyHealth() {
    return this.myHealth;
  }

  public void heal() {
    this.myHealth++;
  }

  public long getMyScore() {
    return Main.STRONG_BRICK_POINTS;
  }
}
