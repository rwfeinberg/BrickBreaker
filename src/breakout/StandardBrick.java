package breakout;

/**
 * @author Caleb Getahun
 * @author Ryan Feinberg
 *
 * The StandardBrick class is a representation of a space in the game where there is a regular one hit
 * brick (represented as a 1 in our Grid class for reading in the layout of the level)
 *
 * StandardBrick will only be called if the number passed into the grid is a 1, meaning there may be some
 * implicit assumption to be made. We also must be able to extend the abstract class Brick for this
 * implementation to be valid.
 *
 * Dependencies: We rely on the Brick class and Overriding methods from it to be able to customize the
 * actions that can be called on StandardBrick
 *
 * In the Grid class if we call the case 1, a StandardBrick is generated.
 * Ex: Brick standardBrick = new StrandardBrick(xpos, ypos, width, height)
 */

public class StandardBrick extends Brick {

  private final int myType = 1;
  private int myHealth = Main.STANDARD_BRICK_STARTING_HEALTH;

  public StandardBrick(double xPos, double yPos, double width, double height) {
    super(xPos, yPos, width, height);
    this.setFill(Main.STANDARD_BRICK_COLOR);
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
    return Main.STANDARD_BRICK_POINTS;
  }

  @Override
  public void heal() {

  }
}
