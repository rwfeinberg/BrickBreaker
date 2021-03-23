package breakout;

/**
 * @author Ryan Feinberg
 * @author Caleb Getahun
 *
 * Extends PowerUp.java, this class represents a powerUp that enlarges the ball
 */

import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.util.Duration;

public class BallPowerUp extends PowerUp {

  /**
   *
   * @param centerX - initial x position of the powerup
   * @param centerY - initial y position of the powerup
   */
  public BallPowerUp(double centerX, double centerY) {
    super(centerX, centerY);
    this.setFill(Main.BALL_POWER_UP_COLOR);
  }

  //Increases ball size for POWER_UP_DURATION seconds
  private void enlargeBall(Ball myBall) {
    myBall.setRadius(Main.ENLARGED_BALL_RADIUS);
    Timeline timeline = new Timeline(
        new KeyFrame(Duration.millis(Main.POWER_UP_DURATION * 1000),
            ae -> myBall.setRadius(Main.BALL_SIZE)));
    timeline.play();
  }

  @Override
  public void activatePowerUp(Group root, Paddle myPaddle, Ball myBall,
      List<PowerUp> powerUpsToRemove, PowerUp thisPowerUp) {
    this.enlargeBall(myBall);
    powerUpsToRemove.add(thisPowerUp);
  }
}