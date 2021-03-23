package breakout;

import java.io.File;
import java.io.FileWriter;
import java.util.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Text;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class GameTestLevelOneTest extends DukeApplicationTest {

  //Initialize objects
  private LevelOne testLevel = new LevelOne();
  private List<Brick> listOfBricks;
  private Scene myScene;
  private Ball myBall;
  private Paddle myPaddle;
  private TextBox myScoreDisplay;
  private TextBox myLivesDisplay;
  private TextBox myNameDisplay;
  private Scanner readScore;
  private File highScoreFile = new File("data/" + Main.HIGH_SCORE_FILE_NAME);
  private FileWriter scoreWriter;

  //initialize root
  private Group root = testLevel.setupScene();
  private boolean[] levelWon = {false};

  //Set up scene, get game objects
  @Override
  public void start(Stage stage) {
    myScene = new Scene(root, Main.WIDTH, Main.HEIGHT, Main.BACKGROUND);
    myScene.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
    stage.setScene(myScene);
    stage.show();

    createHighScoreFile();

    myBall = testLevel.getMyBall();
    myPaddle = testLevel.getMyPaddle();
    listOfBricks = testLevel.getBricks();
    myScoreDisplay = testLevel.getScoreDisplay();
    myLivesDisplay = testLevel.getLivesDisplay();
    myNameDisplay = testLevel.getNameDisplay();

  }

  private void createHighScoreFile() {
    try {
      if (highScoreFile.createNewFile()) {
        new FileWriter("data/" + Main.HIGH_SCORE_FILE_NAME).close();
        scoreWriter = new FileWriter("data/" + Main.HIGH_SCORE_FILE_NAME);
        scoreWriter.write(0 + "");
        scoreWriter.close();
      } else {
        readScore = new Scanner(highScoreFile);
        if (!readScore.hasNextLong()) {
          new FileWriter("data/" + Main.HIGH_SCORE_FILE_NAME).close();
          scoreWriter = new FileWriter("data/" + Main.HIGH_SCORE_FILE_NAME);
          scoreWriter.write(0 + "");
          scoreWriter.close();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void handleKeyPress(KeyCode code) {
    switch (code) {
      case TAB -> {
        testLevel.setInGame(true);
        myBall.setxDirection(1);
        myBall.setyDirection(1);
      }
      case U -> {
        testLevel.releaseBall(0, 1);
      }
      case LEFT -> {
        if (myPaddle.getX() >= 20) {
          if (!testLevel.isInGame()) {
            myBall.setCenterX(myBall.getCenterX() - Main.PADDLE_SPEED);
          }
          myPaddle.setX(myPaddle.getX() - Main.PADDLE_SPEED);
        }
      }
      //Moves paddle right until edge of window
      case RIGHT -> {
        if (myPaddle.getX() + myPaddle.getWidth() <= Main.WIDTH - 20) {
          if (!testLevel.isInGame()) {
            myBall.setCenterX(myBall.getCenterX() + Main.PADDLE_SPEED);
          }
          myPaddle.setX(myPaddle.getX() + Main.PADDLE_SPEED);
        }
      }
      case Y -> {
        if (testLevel.isInGame()) {
          myBall.flipYDirection();
        }
      }
      case L -> {
        testLevel.increaseLives();
      }
      case P -> {
        PowerUp p = new BallPowerUp(myPaddle.getX() + myPaddle.getWidth() / 2,
            myPaddle.getY() - 10);
        p.setId("powerup1");
        root.getChildren().add(p);
      }
      case R -> resetGameToStart();
      case D -> {
        String brickID = listOfBricks.get(listOfBricks.size() - 1).getId();
        root.getChildren().remove(root.lookup("#" + brickID));
        listOfBricks.remove(listOfBricks.size() - 1);
      }
      case Q -> {
        testLevel.decreaseLives();
      }
    }
  }

  private void resetGameToStart() {
    myPaddle.setX(Main.WIDTH / 2 - myPaddle.getWidth() / 2);
    myPaddle.setY(Main.HEIGHT - 50);
    myBall.setCenterX(Main.WIDTH / 2);
    myBall.setCenterY(myPaddle.getY() - myBall.getRadius());
    myBall.setyDirection(0);
    myBall.setxDirection(0);
    testLevel.setInGame(false);
  }

  @Test
  public void testInitialPositionsAndVelocity() {
    //Test ball initial position and size
    assertEquals(300, myBall.getCenterX());
    assertEquals(742, myBall.getCenterY());
    assertEquals(8, myBall.getRadius());

    double initialXPosition = myBall.getCenterX();
    double initialYPosition = myBall.getCenterY();

    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));

    //Test ball initial velocity
    assertEquals(initialXPosition, myBall.getCenterX());
    assertEquals(initialYPosition, myBall.getCenterY());
  }

  @Test
  public void testInitialPaddle() {
    //Test paddle initial position and size
    assertEquals(250, myPaddle.getX());
    assertEquals(750, myPaddle.getY());
    assertEquals(100, myPaddle.getWidth());
    assertEquals(15, myPaddle.getHeight());

    //Test paddle moves right
    press(myScene, KeyCode.RIGHT);
    assertEquals(250 + Main.PADDLE_SPEED, myPaddle.getX());

    //Test paddle moves left
    press(myScene, KeyCode.LEFT);
    assertEquals(250, myPaddle.getX());
  }

  @Test
  public void testBrickRead() {
    List<Brick> brickList = testLevel.getBricks();
    Brick firstRowBrick = (Brick) brickList.toArray()[0];
    Brick secondRowBrick = (Brick) brickList.toArray()[8];
    Brick thirdRowBrick = (Brick) brickList.toArray()[16];
    Brick fourthRowBrick = (Brick) brickList.toArray()[24];

    //Test first brick on first row
    assertEquals(20, firstRowBrick.getX());
    assertEquals(50, firstRowBrick.getY());
    assertEquals(1, firstRowBrick.getMyType());

    //Test first brick on second row
    assertEquals(20, secondRowBrick.getX());
    assertEquals(85, secondRowBrick.getY());
    assertEquals(1, secondRowBrick.getMyType());

    //Test first brick on third row
    assertEquals(20, thirdRowBrick.getX());
    assertEquals(120, thirdRowBrick.getY());
    assertEquals(1, thirdRowBrick.getMyType());

    //Test first brick on fourth row
    assertEquals(20, fourthRowBrick.getX());
    assertEquals(155, fourthRowBrick.getY());
    assertEquals(1, fourthRowBrick.getMyType());
  }

  //Invalid test when bricks are placed in scene.

//  @Test
//  public void testBallBouncesOffCorner() {
//    //Start ball motion
//    press(myScene, KeyCode.TAB);
//
//    //Ball is exactly 5 pixels off of corner
//    myBall.setCenterX(587 - Grid.SCREEN_EDGE_BUFFER);
//    myBall.setCenterY(13);
//
//    //Move towards corner
//    myGame.step(Main.SECOND_DELAY);
//
//    //At corner
//    myGame.step(Main.SECOND_DELAY);
//
//    //Bounces back off of corner
//    myGame.step(Main.SECOND_DELAY);
//    myGame.step(Main.SECOND_DELAY);
//
//    assertEquals(577, myBall.getCenterX());
//    assertEquals(23, myBall.getCenterY());
//
//    myGame.step(Main.SECOND_DELAY);
//    assertEquals(572, myBall.getCenterX());
//    assertEquals(28, myBall.getCenterY());
//  }

  @Test
  public void testBallBouncesOffPaddle() {
    press(myScene, KeyCode.TAB);
    myBall.setCenterX(myPaddle.getX() + myPaddle.getWidth() / 2);
    double oldXBallPos = myBall.getCenterX();
    myBall.setCenterY(myPaddle.getY() - 20);
    press(myScene, KeyCode.Y);

    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));

    // if the myBall y pos is less than the paddle, it means it is above the paddle in our game
    assertTrue(myBall.getCenterY() < myPaddle.getY());
  }

  @Test
  public void testGameResetsWhenBallOut() {
    press(myScene, KeyCode.TAB);
    myBall.setCenterX(myPaddle.getX() + myPaddle.getWidth() + 20);
    myBall.setCenterY(Main.HEIGHT - 10);

    System.out.println(myBall.getCenterX());

    myBall.setyDirection(-1);

    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));

    //Check ball position resets
    //assertEquals(Main.WIDTH/2, myBall.getCenterX());
    assertEquals(myPaddle.getY() - Main.BALL_SIZE, myBall.getCenterY());

    //Check Paddle position resets
    assertEquals(Main.WIDTH / 2 - myPaddle.getWidth() / 2, myPaddle.getX());
    assertEquals(Main.HEIGHT - 50, myPaddle.getY());
  }

//  @Test
//  public void testBallBouncesOffBrick() {
//    press(myScene, KeyCode.TAB);
//
//    int initialSize = listOfBricks.size();
//
//    myBall.setCenterX(300);
//    myBall.setCenterY(200);
//    double oldBallCenterY = myBall.getCenterY();
//    double oldBallCenterX = myBall.getCenterX();
//
//    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
//    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
//    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
//    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
//    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
//
//    assertTrue(myBall.getCenterY() > oldBallCenterY);
//    assertTrue(myBall.getCenterX() > oldBallCenterX);
//
//    //Checks to make sure brick was destroyed and removed
//    assertTrue(listOfBricks.size() < initialSize);
//
//    //Check if score increases
//    assertTrue(testLevel.getScore() != 0);
//  }

//  @Test
//  public void testLivesChange() {
//    press(myScene, KeyCode.U);
//    myBall.setCenterX(myPaddle.getX() + 2*myPaddle.getWidth());
//    myBall.setCenterY(Main.HEIGHT - 10);
//    myBall.flipYDirection();
//
//    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
//    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
//    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
//    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
//    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
//    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
//
//    assertEquals(2, testLevel.getLives());
//
//    press(myScene, KeyCode.L);
//
//    assertEquals(3, testLevel.getLives());
//  }

  //Tests cheat keys and power up effects, to change powerup, change to respective key, and also change
  //powerUpChooser in Main.java to the respective number (1, 2, or 3)
//
//  @Test
//  public void testPowerUp() {
//    press(myScene, KeyCode.TAB);
//
//    double initialBallsize = myBall.getRadius();
//    System.out.println(initialBallsize);
//
//    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
//    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
//    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
//    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
//
//    press(myScene, KeyCode.P);
//
//    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
//    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
//    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
//    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
//
//    System.out.println(myBall.getRadius());
//
//    assertTrue(myBall.getRadius() > initialBallsize);
//  }

  @Test
  public void testPowerUpFromBrick() {
    press(myScene, KeyCode.LEFT);
    press(myScene, KeyCode.U);

    Brick b = lookup("#brick26").query();
    myBall.setCenterY(b.getY() + b.getHeight() + 20);

    int initialRootSize = root.getChildren().size();

    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));

    assertTrue(root.getChildren().size() == initialRootSize);
  }

  @Test
  public void testDisplayPresent() {
    assertTrue(root.getChildren().contains(myLivesDisplay));
    assertTrue(root.getChildren().contains(myScoreDisplay));
    assertTrue(root.getChildren().contains(myNameDisplay));
  }

  @Test
  public void testResetCheatKey() {
    press(myScene, KeyCode.TAB);

    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));

    double movedBall = myBall.getCenterY();

    press(myScene, KeyCode.R);

    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));

    assertTrue(myBall.getCenterY() > movedBall);
  }

  @Test
  public void testRemoveBrickKey() {
    press(myScene, KeyCode.TAB);
    int sizeOfOldList = listOfBricks.size();

    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));

    press(myScene, KeyCode.D);

    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));

    assertTrue(listOfBricks.size() < sizeOfOldList);

  }


  @Test
  public void testHighScoreFileCreation() {
    highScoreFile.delete();
    createHighScoreFile();
    try {
      if (highScoreFile.createNewFile()) {
        assertEquals(0, Long.parseLong(readScore.next()));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //The below test must be ran last as it closes the JavaFx window to update the high score

//  @Test
//  public void testHighScoreUpdate() {
//    testLevel.setScore(100);
//
//    testLevel.decreaseLives();
//    testLevel.decreaseLives();
//    testLevel.decreaseLives();
//
//    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
//    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
//
//    assertEquals(100, Long.parseLong(readScore.next()));
//  }
}
