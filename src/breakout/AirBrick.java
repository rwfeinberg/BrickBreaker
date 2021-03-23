package breakout;

/**
 * @author Caleb Getahun
 * @author Ryan Feinberg
 *
 * The AirBrick class is a representation of a space in the game where there is no brick (represented
 * as a 0 in our Grid class and in reading in the layout of the level
 *
 * AirBrick will only be called if the number passed into the grid is a 0, meaning there may be some
 * implicit assumption to be made, but this is in line with our design
 *
 * Dependencies: We rely on the Brick class and Overriding methods from it to be able to customize the
 * actions that can be called on AirBrick
 *
 * This would be used in the Grid Class to represent a space of a void brick (no brick) being present
 * in the scene.
 *
 */

public class AirBrick extends Brick {

  //Signifies brick type
  private final int myType = 0;

  public AirBrick(double xPos, double yPos, double width, double height) {
    super(xPos, yPos, width, height);
  }

  //Used to omit addition in Grid.java's getBricks method
  @Override
  public int getMyType() {
    return this.myType;
  }

  @Override
  public void damage() {
    return;
  }


  @Override
  public int getMyHealth() {
    return 0;
  }

  @Override
  public long getMyScore() {
    return 0;
  }

  @Override
  public void heal() {

  }
}
