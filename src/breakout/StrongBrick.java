package breakout;

/**
 * @author Caleb Getahun
 * @author Ryan Feinberg
 *
 * The StrongBrick class is a representation of a brick which takes multiple (3) hits to destroy from the
 * scene. This can be used to make a level more challenging and fun
 *
 * StrongBrick operates under the assumption that we are able to extend the Brick class.
 *
 * Dependencies: We rely on the Brick class and Overriding methods from it to be able to customize the
 * actions that can be called on this brick
 *
 * This would be used in the Grid Class to represent a StrongBrick being present in the scene.
 * Ex: Brick strongBrick = new StrandardBrick(xpos, ypos, width, height)
 */

import javafx.scene.paint.Color;

public class StrongBrick extends Brick {

  private final int myType = 2;
  private int myHealth = Main.STRONG_BRICK_STARTING_HEALTH;

  public StrongBrick(double xPos, double yPos, double width, double height) {
    super(xPos, yPos, width, height);
    this.setFill(Main.STRONG_BRICK_COLOR);
  }

  @Override
  public int getMyType() {
    return this.myType;
  }

  //Darkens its color the lower its health
  public void damage() {
    this.myHealth--;
    if (this.myHealth == 2) {
      this.setFill(Color.DEEPSKYBLUE.darker());
    } else if (this.myHealth == 1) {
      this.setFill(Color.DEEPSKYBLUE.darker().darker());
    }
  }

  public int getMyHealth() {
    return this.myHealth;
  }

  public long getMyScore() {
    return Main.STRONG_BRICK_POINTS;
  }

  @Override
  public void heal() {

  }

}
