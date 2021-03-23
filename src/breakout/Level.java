package breakout;

/**
 * @author Ryan Feinberg
 * @author Caleb Getahun
 *
 * Purpose - The Level.java class is the superclass to be extended when creating a new game level.
 * The class defines many abstract methods for the levels to use, which are described below.
 *
 * Assumptions - This class should be extended when creating new level subclasses to be added to the game,
 * and all of its abstract methods should be overwritten. The instance variables listed are only to be used
 * for testing purposes, and should not be used in a game.
 *
 * Dependencies - This class depends on Main.java for certain constants, as well as Ball, Paddle, TextBox, Grid,
 *  PowerUp, and Brick.java to initialize its instance variables and to be passed into its methods.
 *
 * Example - New class: public class LevelFour extends Level {}
 *           Instance: Level fourthLevel = new LevelFour(root, score);
 *
 */

import java.util.List;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Level {

  //These instance variables are defined purely for use in GameTest.java
  private final Paddle myPaddle = new Paddle(Main.WIDTH / 2 - Main.PADDLE_WIDTH / 2,
      Main.HEIGHT - 50,
      Main.PADDLE_WIDTH,
      Main.PADDLE_HEIGHT,
      Main.PADDLE_COLOR);
  private final Ball myBall = new Ball(Main.WIDTH / 2, myPaddle.getY() - Main.BALL_SIZE,
      Main.BALL_SIZE,
      Main.BALL_COLOR);
  private final Grid myGrid = new Grid();
  private final List<Brick> listOfBricks = myGrid.getBricks("data/testLevel.txt");
  private final long myScore = 0;
  private final int myLives = 3;
  private boolean inGame = false;
  private final TextBox myScoreDisplay = new TextBox(myScore);
  private final TextBox myNameDisplay = new TextBox("");
  private final TextBox myLivesDisplay = new TextBox(myLives);


  /**
   * This method is only used for testing purposes, and accomplishes the same as the level subclasses'
   * constructors (setting up the root with game objects).
   *
   * @return - the game's root, set up with game objects
   */
  public Group setupScene() {
    Group root = new Group();

    this.myPaddle.setId("paddle");
    this.myBall.setId("ball");
    this.myScoreDisplay.setId("score");
    this.myNameDisplay.setId("name");
    this.myLivesDisplay.setId("lives");

    this.myBall.setxDirection(0);
    this.myBall.setyDirection(0);
    this.myScoreDisplay.setFill(Color.WHITE);
    this.myNameDisplay.setFill(Color.WHITE);
    this.myLivesDisplay.setFill(Color.WHITE);

    int brickID = 1;
    for (Brick block : this.listOfBricks) {
      block.setId("brick" + brickID);
      root.getChildren().add(block);
      brickID++;
    }

    root.getChildren().add(this.myBall);
    root.getChildren().add(this.myPaddle);
    root.getChildren().add(this.myScoreDisplay);
    root.getChildren().add(this.myNameDisplay);
    root.getChildren().add(this.myLivesDisplay);
    return root;
  }

  /**
   *
   * @param time - elapsedTime (amount of time to be called per second)
   * @param r - game's scene's root
   * @param l - instance of current level
   */
  public abstract void doStep(double time, Group r, boolean[] l);

  /**
   * Resets level's paddle and ball to initial starting positions
   */
  public abstract void resetGameToStart();

  /**
   * This method is used to send the ball off of the player's paddle to start the game.
   * @param xDir - x direction of ball to be released (-1 to 1)
   * @param yDir - y direction of ball to be released (should be 1 to send ball upwards from paddle)
   */
  public abstract void releaseBall(double xDir, double yDir);

  /**
   * This method moves the paddle to the left and is called given a certain key input
   */
  public abstract void movePaddleLeft();

  /**
   * This method moves the paddle to the right and is called given a certain key input
   */
  public abstract void movePaddleRight();

  /**
   * This method flips the x direction of the level's ball
   */
  public abstract void flipX();

  /**
   * This method flips the y direction of the level's ball
   */
  public abstract void flipY();

  /**
   * This method returns the current score of the level
   * @return - score of the level
   */
  public abstract long getScore();

  /**
   * This method sets (updates) the current score of the level and updates the score display accordingly
   * @param s - points gained
   */
  public abstract void setScore(long s);

  /**
   * This method destroys the first brick in the level (brick furthest down and to the right)
   * @param r - game scene's root
   */
  public abstract void destroyFirstBrick(Group r);

  /**
   * This method spawns a random powerup during the collision of the ball and powerUp brick
   *
   * @param r - Instance of powerUpBrick where the powerUp should spawn from
   * @return - PowerUp
   */
  public abstract PowerUp makeRandomPowerUp(Rectangle r);

  /**
   * This method instantly gives the player a random powerup.
   *
   * @param r - game scene's root
   */
  public abstract void giveRandomPowerUp(Group r);

  /**
   * Returns an instance of the current level's ball
   *
   * @return - instance of the level's ball
   */
  public abstract Ball getMyBall();

  /**
   * Returns an instance of the current level's paddle
   *
   * @return - instance of the level's paddle
   */
  public abstract Paddle getMyPaddle();

  /**
   * Decreases the lives in the current level, called when ball falls off of screen
   */
  public abstract void decreaseLives();

  /**
   * Increases the lives in the current level, called for a cheat key
   */
  public abstract void increaseLives();

  /**
   * All of the following methods are only used for testing purposes.
   */

  /**
   * This method returns the listOfBricks of the level
   * @return - list of bricks of the level
   */
  public List<Brick> getBricks() {
    return listOfBricks;
  }

  /**
   * This method is used to check if a level is in game
   *
   * @return - boolean signifying whether the level is paused or not
   */
  public boolean isInGame() {
    return this.inGame;
  }

  /**
   * This method changes whether or not the level is in game
   *
   * @param inGame - true or false, to make the game in or our of game, respectively
   */
  public void setInGame(boolean inGame) {
    this.inGame = inGame;
  }

  /**
   * This method returns the current lives of the level
   *
   * @return - current level's lives
   */
  public int getLives() {
    return myLives;
  }

  /**
   * Returns an instance of the score display of the level
   *
   * @return - instance of score display
   */
  public TextBox getScoreDisplay() {
    return this.myScoreDisplay;
  }

  /**
   * Returns an instance of the lives display of the level
   *
   * @return - instance of lives display
   */
  public TextBox getLivesDisplay() {
    return this.myLivesDisplay;
  }

  /**
   * Returns an instance of the name display of the level
   *
   * @return - instance of name display
   */
  public TextBox getNameDisplay() {
    return this.myNameDisplay;
  }
}
