package breakout;

/**
 * @author Caleb Getahun
 * @author Ryan Feinberg
 *
 * The Brick class is an abstract class to represent the basic implementation and attributes of a brick
 * in our game of breakout. The user needs this class to have any type of game to play
 *
 * We are operating under the assumption that the class will be extended by its subclasses and the
 * Rectangle class can also be extended from
 *
 * Dependencies: Ball (for knowing when collisions occur), Level (to keep track of the list of bricks
 * that need to be removed), PowerUp (to know we have a PowerUpBrick, we need to know the type of the
 * brick (numeric value as given in Grid)).
 *
 * For example the Brick class is extended by the StandardBrick class to include much of the basic functionality
 * and necessary function calls. This also comes with a default constructor.
 *
 *
 */

import java.util.List;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

public abstract class Brick extends Rectangle {

  public Brick(double xPos, double yPos, double width, double height) {
    super(xPos, yPos, width, height);
  }

  /**
   *
   * Checks collision of myBall with any brick
   *
   * @param myBall
   * @param root
   * @param toRemove
   * @param currentLevel
   * @param i
   * @param list
   * @param thisBrick
   */
  public void checkBrickCollision(Ball myBall, Group root, List toRemove, Level currentLevel,
      int[] i, List<PowerUp> list, Brick thisBrick) {

    //Checks for each side of brick
    checkTopCollision(myBall, root, toRemove, currentLevel, thisBrick, i, list);
    checkBottomCollision(myBall, root, toRemove, currentLevel, thisBrick, i, list);
    checkLeftCollision(myBall, root, toRemove, currentLevel, thisBrick, i);
    checkRightCollision(myBall, root, toRemove, currentLevel, thisBrick, i);

  }

  /**
   * Performs a collision check on the top of the brick in question
   * @param myBall
   * @param root
   * @param toRemove
   * @param currentLevel
   * @param thisBrick
   * @param i
   * @param listOfPowerUps
   */
  public void checkTopCollision(Ball myBall, Group root, List toRemove, Level currentLevel,
      Brick thisBrick, int[] i, List<PowerUp> listOfPowerUps) {
    if (myBall.getCenterY() + myBall.getRadius() >= this.getY() &&
        myBall.getCenterY() + myBall.getRadius() <= this.getY() + this.getHeight()) {
      doCollision(myBall, root, toRemove, currentLevel, thisBrick, i, listOfPowerUps);
    }
  }

  /**
   * Performs a collision check on the bottom of the brick in question
   * @param myBall
   * @param root
   * @param toRemove
   * @param currentLevel
   * @param thisBrick
   * @param i
   * @param listOfPowerUps
   */

  public void checkBottomCollision(Ball myBall, Group root, List toRemove, Level currentLevel,
      Brick thisBrick, int[] i, List<PowerUp> listOfPowerUps) {
    if (myBall.getCenterY() - myBall.getRadius() <= this.getY() + this.getHeight() &&
        myBall.getCenterY() - myBall.getRadius() >= this.getY()) {
      doCollision(myBall, root, toRemove, currentLevel, thisBrick, i, listOfPowerUps);
    }
  }

  /**
   * Performs collision check on the right side of the brick in question
   * @param myBall
   * @param root
   * @param toRemove
   * @param currentLevel
   * @param thisBrick
   * @param i
   */
  public void checkRightCollision(Ball myBall, Group root, List toRemove, Level currentLevel,
      Brick thisBrick, int[] i) {
    if (myBall.getCenterY() >= this.getY() &&
        myBall.getCenterY() <= this.getY() + this.getHeight()) {
      if (myBall.getCenterX() + myBall.getRadius() >= this.getX() &&
          myBall.getCenterX() + myBall.getRadius() <= this.getX() + this.getWidth()) {

        myBall.flipXDirection();
        this.damage();

        if (this.getMyHealth() <= 0) {
          destroyBrick(root, toRemove, thisBrick, currentLevel);
          return;
        }

      }
    }
  }

  /**
   * Performs collision check on the left side of the brick in question
   * @param myBall
   * @param root
   * @param toRemove
   * @param currentLevel
   * @param thisBrick
   * @param i
   */
  public void checkLeftCollision(Ball myBall, Group root, List toRemove, Level currentLevel,
      Brick thisBrick, int[] i) {
    if (myBall.getCenterY() >= this.getY() &&
        myBall.getCenterY() <= this.getY() + this.getHeight()) {
      if (myBall.getCenterX() - myBall.getRadius() <= this.getX() + this.getWidth() &&
          myBall.getCenterX() - myBall.getRadius() >= this.getX()) {

        myBall.flipXDirection();
        this.damage();

        if (this.getMyHealth() <= 0) {
          destroyBrick(root, toRemove, thisBrick, currentLevel);
          return;
        }
      }
    }
  }

  /**
   * Handles the updating and destruction of the brick's attributes on a collision
   * @param myBall
   * @param root
   * @param toRemove
   * @param currentLevel
   * @param thisBrick
   * @param i
   * @param list
   */
  public void doCollision(Ball myBall, Group root, List toRemove, Level currentLevel,
      Brick thisBrick, int[] i, List<PowerUp> list) {
    if (myBall.getCenterX() >= this.getX() &&
        myBall.getCenterX() <= this.getX() + this.getWidth()) {

      myBall.flipYDirection();
      this.damage();

      if (this.getMyHealth() <= 0) {
        destroyBrick(root, toRemove, thisBrick, currentLevel);
        return;
      }
    }
  }

  /**
   * Removes brick from listOfBricks and root to fully destroy the brick
   * @param root
   * @param toRemove
   * @param thisBrick
   * @param currentLevel
   */
  public void destroyBrick(Group root, List toRemove, Brick thisBrick, Level currentLevel) {
    currentLevel.setScore(thisBrick.getMyScore());
    toRemove.add(thisBrick);
  }

  //Used in GameTest, and in Grid.java's getBricks to determine omission of AirBricks

  /**
   * Return the type of Brick (based on the number passed into grid)
   * @return int
   */
  public abstract int getMyType();

  /**
   * Takes a damage point off the health of the brick
   */

  public abstract void damage();

  /**
   * Getter for the health of the brick which is the number of hits until the brick will be destroyed
   * @return int
   */
  public abstract int getMyHealth();

  /**
   * Gets the score the brick is worth in game
   * @return
   */
  public abstract long getMyScore();

  /**
   * Adds a health point to the brick
   */
  public abstract void heal();
}
