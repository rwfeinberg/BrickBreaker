package breakout;

/**
 * @author Ryan Feinberg
 * @author Caleb Getahun
 *
 * Extends Level.java, this class represents the second level
 */

import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class LevelTwo extends Level {

  private final String LEVEL_NAME = "Two";

  //Gets bricks
  private final Grid myGrid = new Grid();
  private final List<Brick> listOfBricks = myGrid.getBricks("data/levelTwo.txt");
  private int unbreakableCount = myGrid.getUnbreakables();

  //Initialize game objects
  private final Paddle myPaddle = new Paddle(Main.WIDTH / 2 - Main.PADDLE_WIDTH / 2,
      Main.HEIGHT - 50,
      Main.PADDLE_WIDTH,
      Main.PADDLE_HEIGHT,
      Main.PADDLE_COLOR);
  private final Ball myBall = new Ball(Main.WIDTH / 2, myPaddle.getY() - Main.BALL_SIZE,
      Main.BALL_SIZE,
      Main.BALL_COLOR);
  //Initialize lists for handling removal from root
  private final List<Object> bricksToRemove = new ArrayList<>();
  private final List<PowerUp> listOfPowerUps = new ArrayList<>();
  //Used as ID for powerUps, increases by 1
  private final int[] powerUpChooser = {1};
  private long myScore;
  private long levelScore;
  private int myLives = Main.DEFAULT_STARTING_LIVES - 2;
  private final TextBox myScoreDisplay;
  private final TextBox myNameDisplay = new TextBox(LEVEL_NAME);
  private final TextBox myLivesDisplay = new TextBox(myLives);
  private boolean inGame = false;
  private final List<PowerUp> powerUpsToRemove = new ArrayList<>();


  /**
   * This constructor adds all of this level's game objects to the game's root
   *
   * @param root - game scene's root
   */
  public LevelTwo(Group root, long score) {
    super();
    this.myScore = score;
    this.levelScore = score;
    root.getChildren().clear();

    myScoreDisplay = new TextBox(myScore);
    this.myPaddle.setId("paddle");
    this.myBall.setId("ball");
    this.myScoreDisplay.setId("score");
    this.myNameDisplay.setId("name");
    this.myLivesDisplay.setId("lives");

    this.myBall.setxDirection(0);
    this.myBall.setyDirection(0);
    this.myScoreDisplay.setFill(Color.WHITE);
    this.myNameDisplay.setFill(Color.WHITE);
    this.myLivesDisplay.setFill(Color.RED);

    //Add bricks to list and scene, set ids appropriately
    int brickID = 1;
    for (Brick block : this.listOfBricks) {
      block.setId("brick" + brickID);
      root.getChildren().add(block);
      brickID++;
    }

    //add to root
    root.getChildren().add(this.myBall);
    root.getChildren().add(this.myPaddle);
    root.getChildren().add(this.myScoreDisplay);
    root.getChildren().add(this.myNameDisplay);
    root.getChildren().add(this.myLivesDisplay);
  }

  /**
   * This method is the main section of Main.java's step method, updates the game objects
   *
   * @param elapsedTime - time passed per frame (1/ FPS)
   * @param root - The root node of the game's scene
   * @param levelWon - a boolean designating whether the level has been beaten or not yet
   */
  public void doStep(double elapsedTime, Group root, boolean[] levelWon) {
    //update ball
    myBall.updateBall(elapsedTime);

    //check for brick collisions
    for (Brick inSceneBrick : this.listOfBricks) {
      inSceneBrick
          .checkBrickCollision(myBall, root, bricksToRemove, this, powerUpChooser, listOfPowerUps,
              inSceneBrick);

    }
    this.listOfBricks.removeAll(bricksToRemove);
    root.getChildren().removeAll(bricksToRemove);
    bricksToRemove.clear();

    //check for other collisions
    myPaddle.checkBallPaddleCollision(myBall);
    myBall.checkBallWallCollision();

    //Update powerUp if powerUp has spawned
    for (PowerUp scenePowerUp : listOfPowerUps) {
      scenePowerUp.updatePowerUp(elapsedTime);
      scenePowerUp.checkPaddleCollision(root, scenePowerUp, myPaddle, myBall, powerUpsToRemove);
    }
    this.listOfPowerUps.removeAll(powerUpsToRemove);
    root.getChildren().removeAll(powerUpsToRemove);
    powerUpsToRemove.clear();
    decreasePaddleSizeAndSlowBall();

    //Check for scenarios
    checkIfBallOffScreen();
    checkIfWon(levelWon);
    checkIfLost(levelWon);
  }

  /**
   * This method is called whenever the score increases, and shrinks the paddle and slows down the
   * ball slightly when it occurs.
   */
  public void decreasePaddleSizeAndSlowBall() {
    long scoreDifference = myScore - levelScore;
    myPaddle.setWidth(Main.PADDLE_WIDTH * (1 - (double) scoreDifference / 25000));
    myBall.setMyBallSpeed(Main.BALL_SPEED * (1 - (double) scoreDifference / 75000));
  }

  //Places paddle and ball at default position
  public void resetGameToStart() {
    myPaddle.setX(Main.WIDTH / 2 - myPaddle.getWidth() / 2);
    myPaddle.setY(Main.HEIGHT - 50);
    myBall.setCenterX(Main.WIDTH / 2);
    myBall.setCenterY(myPaddle.getY() - myBall.getRadius());
    myBall.setyDirection(0);
    myBall.setxDirection(0);
    this.inGame = false;
  }

  /**
   * This method checks if the ball fell off of the bottom of the screen and decreases the lives
   */
  private void checkIfBallOffScreen() {
    if (myBall.getCenterY() >= Main.HEIGHT) {
      resetGameToStart();
      this.decreaseLives();
    }
  }

  /**
   * This method sets the levelWon boolean to true if the bricks are cleared from the level
   *
   * @param levelWon - boolean to signify if level is won
   */
  private void checkIfWon(boolean[] levelWon) {
    if (this.listOfBricks.size() - unbreakableCount <= 0) {
      levelWon[0] = true;
    }
  }

  /**
   * Checks if the lives have reached zero, and if so, ends the game while recording the high
   * score if achieved.
   */
  private void checkIfLost(boolean[] levelWon) {
    if (this.myLives <= 0) {
      levelWon[0] = true;
    }
  }

  //Used to release ball from paddle to start game
  public void releaseBall(double xDir, double yDir) {
    if (!this.inGame) {
      myBall.setxDirection(xDir);
      myBall.setyDirection(yDir);
      this.inGame = true;
    }
  }


  @Override
  public void movePaddleLeft() {
    if (myPaddle.getX() >= 20) {
      if (!this.inGame) {
        myBall.setCenterX(myBall.getCenterX() - Main.PADDLE_SPEED);
      }
      myPaddle.setX(myPaddle.getX() - Main.PADDLE_SPEED);
    }
  }

  @Override
  public void movePaddleRight() {
    if (myPaddle.getX() + myPaddle.getWidth() <= Main.WIDTH - 20) {
      if (!this.inGame) {
        myBall.setCenterX(myBall.getCenterX() + Main.PADDLE_SPEED);
      }
      myPaddle.setX(myPaddle.getX() + Main.PADDLE_SPEED);
    }
  }

  @Override
  public void flipX() {
    if (this.inGame) {
      myBall.flipXDirection();
    }
  }

  @Override
  public void flipY() {
    if (this.inGame) {
      myBall.flipYDirection();
    }
  }

  //Spawns random powerUp from brick
  @Override
  public PowerUp makeRandomPowerUp(Rectangle rect) {
    powerUpChooser[0]++;
    PowerUp p = new SpeedPowerUp(rect.getX() + rect.getWidth() / 2,
        rect.getY() + (rect.getHeight() / 2));
    p.setId("powerup" + powerUpChooser[0]);
    listOfPowerUps.add(p);
    return p;
  }

  //Gives player random powerUp
  @Override
  public void giveRandomPowerUp(Group root) {
    PowerUp p = makeRandomPowerUp(myPaddle);
    root.getChildren().add(p);
  }

  //Used in GameTest.java only
  @Override
  public long getScore() {
    return myScore;
  }

  //Updates score when brick is destroyed
  @Override
  public void setScore(long score) {
    this.myScore += score;
    myScoreDisplay.setFill(Color.YELLOW);
    Timeline timeline = new Timeline(
        new KeyFrame(Duration.millis(Main.SCORE_FLASH_DURATION * 1000),
            ae -> myScoreDisplay.setFill(Color.WHITE)));
    timeline.play();
    myScoreDisplay.setText("Score: " + String.format("%08d", myScore));
  }

  @Override
  public void increaseLives() {
    myLives++;
    myLivesDisplay.setText("Lives: " + myLives);
  }

  @Override
  public void decreaseLives() {
    myLives--;
    myLivesDisplay.setText("Lives: " + myLives);
    if (myLives <= 1) {
      //
    }
  }

  //Used only in GameTest
  @Override
  public Ball getMyBall() {
    return null;
  }

  //Used only in GameTest
  @Override
  public Paddle getMyPaddle() {
    return null;
  }

  //Used only in GameTestLevelTwo
  @Override
  public int getLives() {
    return this.myLives;
  }

  //Used only in GameTestLevelTwo
  @Override
  public List<Brick> getBricks() {
    return this.listOfBricks;
  }

  //Used for GameTestLevelTwo
  public void increaseScore(int score) {
    this.myScore += score;
  }

  //Used for GameTestLevelTwo
  public double getBallSpeed() {
    return this.myBall.getMyBallSpeed();
  }

  //Destroys first brick in game (most right and down brick)
  @Override
  public void destroyFirstBrick(Group root) {
    bricksToRemove.add(this.listOfBricks.get(this.listOfBricks.size() - 1));
    String brickID = this.listOfBricks.get(this.listOfBricks.size() - 1).getId();
    root.getChildren().remove(root.lookup("#" + brickID));
  }
}