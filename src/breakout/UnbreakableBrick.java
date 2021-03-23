package breakout;

/**
 * @author Caleb Getahun
 * @author Ryan Feinberg
 *
 * The UnbreakableBrick class is a representation of a brick in the game where the brick cannot be broken.
 * This can be treated as a wall the user has to get around in order to hit other bricks
 *
 * UnbreakableBrick will only be called if the number passed into the grid is a 5, meaning there may be some
 * implicit assumption to be made. We also must be able to extend the abstract class Brick for this
 * implementation to be valid.
 *
 * Dependencies: Constants in Main are used as parameters, we rely on the Brick class and Overriding
 * methods from it to be able to customize the actions that can be called on UnbreakableBrick.
 *
 * In the Grid class if we call the case 5, an UnbreakableBrick is generated.
 * Ex: Brick unbreakableBrick = new UnbreakableBrick(xpos, ypos, width, height)
 *
 *
 */

public class UnbreakableBrick extends Brick {

  private final int myType = 5;
  private int myHealth = Integer.MAX_VALUE;

  public UnbreakableBrick(double xPos, double yPos, double width, double height) {
    super(xPos, yPos, width, height);
    this.setFill(Main.WALL_BRICK_COLOR);
  }

  @Override
  public int getMyType() {
    return this.myType;
  }

  public void damage() {
    this.myHealth--;
  }

  public int getMyHealth() {
    return this.myHealth;
  }

  public long getMyScore() {
    return Main.WALL_BRICK_POINTS;
  }

  @Override
  public void heal() {

  }
}
