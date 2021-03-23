package breakout;

/**
 * @author Ryan Feinberg
 * @author Caleb Getahun
 *
 * Extends PowerUp.java, this class represents a powerUp that slows down the ball
 */

import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.util.Duration;

public class SpeedPowerUp extends PowerUp {

  /**
   *
   * @param centerX - initial x position of the powerup
   * @param centerY - initial y position of the powerup
   */
  public SpeedPowerUp(double centerX, double centerY) {
    super(centerX, centerY);
    this.setFill(Main.SPEED_POWER_UP_COLOR);
  }

  //Slows down ball for POWER_UP_DURATION seconds
  private void speedUpBall(Ball myBall) {
    myBall.setMyBallSpeed(Main.SLOW_BALL_SPEED);
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(Main.POWER_UP_DURATION * 1000),
        ae -> myBall.setMyBallSpeed(Main.BALL_SPEED)));
    timeline.play();
  }

  @Override
  public void activatePowerUp(Group root, Paddle myPaddle, Ball myBall,
      List<PowerUp> powerUpsToRemove, PowerUp thisPowerUp) {
    this.speedUpBall(myBall);
    powerUpsToRemove.add(thisPowerUp);
  }
}