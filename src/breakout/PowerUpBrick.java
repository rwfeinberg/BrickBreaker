package breakout;

/**
 * @author Caleb Getahun
 * @author Ryan Feinberg
 *
 * The PowerUpBrick class is a representation of a brick in the game where upon hitting it, a power up
 * is spawned.
 *
 * PowerUpBrick will only be called if the number passed into the grid is a 4, meaning there may be some
 * implicit assumption to be made. We also must be able to extend the abstract class Brick for this
 * implementation to be valid.
 *
 * Dependencies: We rely on the Brick class and Overriding methods from it to be able to customize the
 * actions that can be called on StandardBrick. We also use the PowerUp class to get the actual powerup
 * spawns
 *
 * In the Grid class if we call the case 1, a StandardBrick is generated.
 * Ex: Brick powerUpBrick = new StrandardBrick(xpos, ypos, width, height)
 *
 */

import java.util.List;
import javafx.scene.Group;

public class PowerUpBrick extends Brick {

  private final int myType = 4;
  private int myHealth = Main.POWER_UP_BRICK_STARTING_HEALTH;

  public PowerUpBrick(double xPos, double yPos, double width, double height) {
    super(xPos, yPos, width, height);
    this.setFill(Main.POWER_UP_BRICK_COLOR);
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
    return Main.POWER_UP_BRICK_POINTS;
  }

  @Override
  public void heal() {
    return;
  }

  /**
   * Checks whether there is a collision between the ball and the PowerUpBrick. If the ball falls within
   * a set of x and y position constraints, the corresponding collisions will be checked.
   * will
   * @param myBall
   * @param root
   * @param toRemove
   * @param currentLevel
   * @param powerUpChooser
   * @param listOfPowerUps
   * @param thisBrick
   */
  @Override
  public void checkBrickCollision(Ball myBall, Group root, List toRemove, Level currentLevel,
      int[] powerUpChooser, List<PowerUp> listOfPowerUps, Brick thisBrick) {

    if (myBall.getCenterX() >= this.getX() &&
        myBall.getCenterX() <= this.getX() + this.getWidth()) {
      checkTopCollision(myBall, root, toRemove, currentLevel, thisBrick, powerUpChooser,
          listOfPowerUps);
      checkBottomCollision(myBall, root, toRemove, currentLevel, thisBrick, powerUpChooser,
          listOfPowerUps);
    }
    checkLeftCollision(myBall, root, toRemove, currentLevel, thisBrick, powerUpChooser,
        listOfPowerUps);
    checkRightCollision(myBall, root, toRemove, currentLevel, thisBrick, powerUpChooser,
        listOfPowerUps);
  }

  /**
   * Check if there is a collision on the left side of the brick with myBall
   * @param myBall
   * @param root
   * @param toRemove
   * @param currentLevel
   * @param thisBrick
   * @param powerUpChooser
   * @param listOfPowerUps
   */
  public void checkLeftCollision(Ball myBall, Group root, List toRemove, Level currentLevel,
      Brick thisBrick, int[] powerUpChooser, List<PowerUp> listOfPowerUps) {
    if (myBall.getCenterY() >= this.getY() &&
        myBall.getCenterY() <= this.getY() + this.getHeight()) {
      if (myBall.getCenterX() + myBall.getRadius() >= this.getX() &&
          myBall.getCenterX() + myBall.getRadius() <= this.getX() + this.getWidth()) {
        doCollision(myBall, root, toRemove, currentLevel, thisBrick, powerUpChooser,
            listOfPowerUps);
      }
    }
  }

  /**
   * Check if there is a collision on the right side of the brick with myBall
   * @param myBall
   * @param root
   * @param toRemove
   * @param currentLevel
   * @param thisBrick
   * @param powerUpChooser
   * @param listOfPowerUps
   */
  public void checkRightCollision(Ball myBall, Group root, List toRemove, Level currentLevel,
      Brick thisBrick, int[] powerUpChooser, List<PowerUp> listOfPowerUps) {
    if (myBall.getCenterY() >= this.getY() &&
        myBall.getCenterY() <= this.getY() + this.getHeight()) {
      if (myBall.getCenterX() - myBall.getRadius() <= this.getX() + this.getWidth() &&
          myBall.getCenterX() - myBall.getRadius() >= this.getX()) {
        doCollision(myBall, root, toRemove, currentLevel, thisBrick, powerUpChooser,
            listOfPowerUps);
      }
    }
  }

  /**
   * Destroys the brick and removes from scene, spawns powerUp randomly
   * @param myBall
   * @param root
   * @param toRemove
   * @param currentLevel
   * @param thisBrick
   * @param powerUpChooser
   * @param listOfPowerUps
   */
  @Override
  public void doCollision(Ball myBall, Group root, List toRemove, Level currentLevel,
      Brick thisBrick, int[] powerUpChooser, List<PowerUp> listOfPowerUps) {
    myBall.flipYDirection();
    this.damage();

    if (this.getMyHealth() <= 0) {
      destroyBrick(root, toRemove, thisBrick, currentLevel);
      powerUpChooser[0]++;
      PowerUp p = currentLevel.makeRandomPowerUp(thisBrick);
      root.getChildren().add(p);
      return;
    }
  }
}
