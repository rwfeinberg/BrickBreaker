package breakout;

/**
 * @author Ryan Feinberg
 * @author Caleb Getahun
 *
 * Purpose - The PowerUp.java class is the superclass that defines the PowerUp game object in Breakout.
 * Its use it be extened when creating a new subclass power up type.
 *
 * Assumptions - N/A
 *
 * Dependencies - This class extends the Ball.java class for construction's sake, as well as Main.java for
 * certain constants.
 *
 * Example - New class: public class NewPowerUp extends PowerUp {}
 *           Instance: PowerUp newPowerUp = new NewPowerUp(x, y);
 *
 */


import java.util.List;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public abstract class PowerUp extends Ball {

  //PowerUp falls straight down
  public static final int X_DIRECTION = 0;
  public static final int Y_DIRECTION = -1;

  /**
   *
   * @param centerX - initial x position of the powerup
   * @param centerY - initial y position of the powerup
   */
  public PowerUp(double centerX, double centerY) {
    super(centerX, centerY, Main.POWER_UP_RADIUS, Color.WHITE);
  }

  /**
   *
   * @param elapsedTime - 1/FPS (how often should be updated)
   *
   * This method updates the position of the powerUp every frame.
   */
  public void updatePowerUp(double elapsedTime) {
    this.setCenterX(this.getCenterX() + X_DIRECTION * Main.POWER_UP_FALL_SPEED * elapsedTime);
    this.setCenterY(this.getCenterY() - Y_DIRECTION * Main.POWER_UP_FALL_SPEED * elapsedTime);
  }

  /**
   *
   * This method checks the collision between the powerup and the player's paddle.
   *
   * @param root - Game scene's root
   * @param thisPowerUp - a reference to the created powerup
   * @param myPaddle - the player's padde
   * @param myBall - the game's ball
   * @param powerUpsToRemove - a list of powerups to be removed from the scene at the end of every frame
   */
  public void checkPaddleCollision(Group root, PowerUp thisPowerUp, Paddle myPaddle, Ball myBall,
      List<PowerUp> powerUpsToRemove) {

    //Check if hit top of paddle
    if (this.getCenterY() + this.getRadius() >= myPaddle.getY()) {
      if (this.getCenterX() >= myPaddle.getX() &&
          this.getCenterX() <= myPaddle.getX() + myPaddle.getWidth()) {
        activatePowerUp(root, myPaddle, myBall, powerUpsToRemove, thisPowerUp);
        return;
      }
    }

    //Check if hit side of paddle
    if (this.getCenterY() >= myPaddle.getY() &&
        this.getCenterY() <= myPaddle.getY() + myPaddle.getHeight()) {
      if (this.getCenterX() + this.getRadius() >= myPaddle.getX() &&
          this.getCenterX() + this.getRadius() <= myPaddle.getX() + myPaddle.getWidth()) {
        activatePowerUp(root, myPaddle, myBall, powerUpsToRemove, thisPowerUp);
        return;
      } else if (this.getCenterX() - this.getRadius() <= myPaddle.getX() + myPaddle.getWidth()
          &&
          this.getCenterX() - this.getRadius() >= myPaddle.getX()) {
        activatePowerUp(root, myPaddle, myBall, powerUpsToRemove, thisPowerUp);
        return;
      }
    }

    //Check if powerUp fell off bottom of screen
    if (this.getCenterY() >= Main.HEIGHT) {
      powerUpsToRemove.add(thisPowerUp);
    }
  }

  /**
   * This method calls the subclass powerup's unique effect
   *
   * @param root - Game scene's root
   * @param thisPowerUp - a reference to the created powerup
   * @param myPaddle - the player's padde
   * @param myBall - the game's ball
   * @param pList - a list of powerups to be removed from the scene at the end of every frame
   */
  public abstract void activatePowerUp(Group root, Paddle myPaddle, Ball myBall,
      List<PowerUp> pList, PowerUp thisPowerUp);
}
