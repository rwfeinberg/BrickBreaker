package breakout;

/**
 * @author Ryan Feinberg
 * @author Caleb Getahun
 *
 * Purpose - The paddle class is used to initiate and create the player's paddle for the user to control.
 *
 * Assumptions - The class extends Rectangle from javafx's shapes library.
 *
 * Dependencies - The class requires a parameter of a Ball.java instance in its collision method.
 * The class requires some of Main.java's constants.
 *
 * Example - Instance: private final Paddle myPaddle = new Paddle(x, y, WIDTH, HEIGHT, Color.BLUE);
 */

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {

  /**
   *
   * @param centerX - initial x position of the paddle (defined at top left corner)
   * @param centerY - initial y position of the paddle (defined at top left corner)
   * @param width - initial width of paddle in pixels
   * @param height - initial height of paddle in pixels
   * @param color - color of paddle to be filled
   */
  public Paddle(int centerX, int centerY, int width, int height, Paint color) {
    super(centerX, centerY, width, height);
    this.setFill(color);
  }

  /**
   * This method checks the collision between this paddle and the in-game ball, and produces
   * the physically correct response depending on the location of the collision.
   *
   * @param myBall - instance of the ball that the paddle will collide with
   */
  public void checkBallPaddleCollision(Ball myBall) {
    //Checks collision with top of paddle
    if (myBall.getCenterY() + myBall.getRadius() > this.getY()) {
      if (myBall.getCenterX() >= this.getX() &&
          myBall.getCenterX() <= this.getX() + this.getWidth()) {
        myBall.flipYDirection();

        if (Main.ENABLE_VARIED_PADDLE_BOUNCES) {
          //Ball bounces differently depending on where on paddle it hits
          double contactPoint = myBall.getCenterX() - (this.getX() + this.getWidth() / 2);
          if (contactPoint <= -35) {
            myBall.setxDirection(-0.8);
          } else if (contactPoint > -35 && contactPoint <= -20) {
            myBall.setxDirection(-0.6);
          } else if (contactPoint > -20 && contactPoint <= -10) {
            myBall.setxDirection(-0.4);
          } else if (contactPoint > 10 && contactPoint <= 20) {
            myBall.setxDirection(0.4);
          } else if (contactPoint > 20 && contactPoint <= 35) {
            myBall.setxDirection(0.6);
          } else if (contactPoint > 35) {
            myBall.setxDirection(0.8);
          }
        }
      }
    }

    //Checks collision with side of paddle
    if (myBall.getCenterY() >= this.getY() &&
        myBall.getCenterY() <= this.getY() + this.getHeight()) {
      if (myBall.getCenterX() + myBall.getRadius() >= this.getX() &&
          myBall.getCenterX() + myBall.getRadius() <= this.getX() + this.getWidth()) {
        myBall.flipXDirection();
      } else if (myBall.getCenterX() - myBall.getRadius() <= this.getX() + this.getWidth()
          &&
          myBall.getCenterX() - myBall.getRadius() >= this.getX()) {
        myBall.flipXDirection();
      }
    }
  }
}
