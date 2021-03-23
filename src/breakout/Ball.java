package breakout;

/**
 * @author Caleb Getahun
 * @author Ryan Feinberg
 *
 * The Ball class is used to represent any ball sort of object that would be used in the game of breakout.
 *
 * If given values (xpos, ypos, radius) that make the ball larger or out of the screen size being used,
 * this could cause problems and we are operating under the assumption that the ball will be defined in
 * scope
 *
 * Dependencies: We rely on the Circle class to have an actual round object, as well as the Paint class
 * to color/style the ball. Our main implementation of this is called within Main
 *
 * An example would look like the instantiation of the ball in the Level class. Simply call the ball
 * object and pass parameters detailed by the constructor to make the ball in the scene.
 */

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;


public class Ball extends Circle {

  //Directions of ball travel
  private double xDirection;
  private double yDirection;

  //Speed of ball
  private double myBallSpeed = Main.BALL_SPEED;

  public Ball(double centerX, double centerY, int radius, Paint color) {
    super(centerX, centerY, radius, color);
  }

  /**
   * Checks collision of ball with screen edges (x and y)
   *
   * no parameters
   */

  public void checkBallWallCollision() {
    if (this.getCenterX() + this.getRadius() >= Main.WIDTH - Main.SCREEN_SIDE_BUFFER_WIDTH
        || this.getCenterX() - this.getRadius() <= Main.SCREEN_SIDE_BUFFER_WIDTH) {
      this.flipXDirection();
    }
    if (this.getCenterY() - this.getRadius() <= Main.SCREEN_TOP_BUFFER_WIDTH) {
      this.flipYDirection();
    }
  }

  /**
   * Moves ball a function of the elapsedTime passed in
   * Used in updating the position of the ball after each step
   * @param elapsedTime
   */
  public void updateBall(double elapsedTime) {
    this.setCenterX(this.getCenterX() + this.getxDirection() * myBallSpeed * elapsedTime);
    this.setCenterY(this.getCenterY() - this.getyDirection() * myBallSpeed * elapsedTime);
  }

  /**
   * Returns the x direction that the ball is travelling
   * @return
   */
  private double getxDirection() {
    return xDirection;
  }

  /**
   * Allows the user to set the x direction
   * @param xDirection
   */

  public void setxDirection(double xDirection) {
    this.xDirection = xDirection;
  }

  /**
   * Returns the y direction that the ball is travelling
   * @return
   */

  private double getyDirection() {
    return yDirection;
  }

  /**
   * Allows the user to set the y direction
   * @param yDirection
   */

  public void setyDirection(double yDirection) {
    this.yDirection = yDirection;
  }


  /**
   * Change the x direction by a factor of -1
   */
  public void flipXDirection() {
    this.xDirection *= -1;
  }


  /**
   * Change the y direction by a factor of -1
   */
  public void flipYDirection() {
    this.yDirection *= -1;
  }

  /**
   * Used to set the speed of the ball to a desired value
   * Used in speedPowerUp
   * @param speed
   */
  public void setMyBallSpeed(double speed) {
    this.myBallSpeed = speed;
  }

  /**
   * Returns the current ball speed
   * Used in GameTestLevelTwo only
   * @return
   */
  public double getMyBallSpeed() {
    return this.myBallSpeed;
  }

}
