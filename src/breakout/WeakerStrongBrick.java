package breakout;

/**
 * @author Caleb Getahun
 * @author Ryan Feinberg
 *
 * The WeakerStrongBrick class is a representation of a brick in the game where the brick can only be
 * broken after 2 hits. This is a variation to the StrongerBrick, which takes 3 hits to disappear
 *
 * WeakerStrongBrick will only be called if the number passed into the grid is a 3, meaning there may be some
 * implicit assumption to be made. We also must be able to extend the abstract class Brick for this
 * implementation to be valid.
 *
 * Dependencies: Constants in Main are used as parameters, we rely on the Brick class and Overriding
 * methods from it to be able to customize the actions that can be called on UnbreakableBrick.
 *
 * In the Grid class if we call the case 3, a WeakerStrongBrick is generated.
 * Ex: Brick twoHitBrick = new WeakerStrongBrick(xpos, ypos, width, height)
 *
 */

import breakout.Brick;
import breakout.Main;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class WeakerStrongBrick extends Brick {

  private final int myType = 3;
  private int myHealth = Main.STRONG_BRICK_STARTING_HEALTH - 1;

  public WeakerStrongBrick(double xPos, double yPos, double width, double height) {
    super(xPos, yPos, width, height);
    this.setFill(Main.WEAKER_STRONG_BRICK_COLOR);
  }

  @Override
  public int getMyType() {
    return this.myType;
  }

  //Darkens its color the lower its health
  public void damage() {
    this.myHealth--;
    if (this.myHealth == 1) {
      this.setFill(Color.LIGHTGREEN.darker());
    }
  }

  public int getMyHealth() {
    return this.myHealth;
  }

  public long getMyScore() {
    return Main.WEAKER_STRONG_BRICK_POINTS;
  }

  @Override
  public void heal() {

  }

}
