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

public class GameTestLevelTwoTest extends DukeApplicationTest {

  //Initialize objects
  private Group root = new Group();
  private long myScore = 0;
  private LevelTwo testLevel = new LevelTwo(root, myScore);
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
  private boolean[] levelWon = {false};

  //Set up scene, get game objects
  @Override
  public void start(Stage stage) {
    myScene = new Scene(root, Main.WIDTH, Main.HEIGHT, Main.BACKGROUND);
    myScene.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
    stage.setScene(myScene);
    stage.show();

    myBall = lookup("#ball").query();
    myPaddle = lookup("#paddle").query();
    listOfBricks = testLevel.getBricks();
    myScoreDisplay = lookup("#score").query();
    myLivesDisplay = lookup("#lives").query();
    myNameDisplay = lookup("#name").query();
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
      case DIGIT1 -> {
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
  public void checkBrickList() {
    assertEquals(14, listOfBricks.size());
  }

  @Test
  public void checkLiveCount() {
    assertEquals(1, testLevel.getLives());
  }

  @Test
  public void checkPaddleSizeDecrease() {
    double initialPaddleSize = myPaddle.getWidth();
    testLevel.increaseScore(5000);

    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));

    assertTrue(initialPaddleSize > myPaddle.getWidth());
  }

  @Test
  public void checkBallSpeedDecrease() {
    double initialBallSpeed = myBall.getMyBallSpeed();

    testLevel.increaseScore(5000);

    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));
    javafxRun(() -> testLevel.doStep(Main.SECOND_DELAY, root, levelWon));

    assertTrue(initialBallSpeed > myBall.getMyBallSpeed());
  }

}