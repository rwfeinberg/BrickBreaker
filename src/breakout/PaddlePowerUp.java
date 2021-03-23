package breakout;

/**
 * @author Ryan Feinberg
 * @author Caleb Getahun
 *
 * Extends PowerUp.java, this class represents a powerUp that enlarges the players paddle
 */

import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.util.Duration;

public class PaddlePowerUp extends PowerUp {

  /**
   *
   * @param centerX - initial x position of the powerup
   * @param centerY - initial y position of the powerup
   */
  public PaddlePowerUp(double centerX, double centerY) {
    super(centerX, centerY);
    this.setFill(Main.PADDLE_POWER_UP_COLOR);
  }

  //Increases paddle size for POWER_UP_DURATION seconds
  private void enlargePaddle(Paddle myPaddle) {
    myPaddle.setWidth(Main.ENLARGED_PADDLE_WIDTH);
    Timeline timeline = new Timeline(
        new KeyFrame(Duration.millis(Main.POWER_UP_DURATION * 1000),
            ae -> myPaddle.setWidth(Main.PADDLE_WIDTH)));
    timeline.play();
  }

  @Override
  public void activatePowerUp(Group root, Paddle myPaddle, Ball myBall,
      List<PowerUp> powerUpsToRemove, PowerUp thisPowerUp) {
    this.enlargePaddle(myPaddle);
    powerUpsToRemove.add(thisPowerUp);
  }
}
