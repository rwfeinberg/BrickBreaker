package breakout;

/**
 * @author Ryan Feinberg
 * @author Caleb Getahun
 *
 * Purpose - The Main.java class is the class that sets up the game itself and the game loop.
 * It takes the created levels and initializes them appropriately in the start/step method
 * and is also responsible for handling key inputs of the game, as well as pausing it/launching it.
 *
 * Assumptions - The created levels extend Level.java and have a constructor that takes in "root" as
 * a parameter and adds its own game objects to it.
 *
 * Dependencies - The only extraneous class that the Main.java class depends on are each of the
 * level subclasses.
 *
 */


import java.io.File;
import java.io.FileWriter;
import java.util.Random;
import java.util.Scanner;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

  /**
   * Global variables
   */
  //Project Directory Items
  public static final String HIGH_SCORE_FILE_NAME = "highScore.txt";
  //Window dimensions
  public static final String TITLE = "Breakout!";
  public static final int WIDTH = 600;
  public static final int HEIGHT = 800;

  //Paddle dimensions
  public static final int PADDLE_WIDTH = 100;
  public static final int PADDLE_HEIGHT = 15;
  public static final Paint PADDLE_COLOR = Color.WHITE;
  public static final double PADDLE_SPEED = 40;

  //Ball dimensions
  public static final int BALL_SIZE = 8;
  public static final Paint BALL_COLOR = Color.WHITE;
  public static final double BALL_SPEED = 400;


  //Game dimensions
  public static final int DEFAULT_STARTING_LIVES = 3;
  public static final int DEFAULT_STARTING_SCORE = 0;
  public static final int SCREEN_SIDE_BUFFER_WIDTH = 20;
  public static final int SCREEN_TOP_BUFFER_WIDTH = 50;
  public static final int BUFFER_BETWEEN_BRICKS_WIDTH = 5;
  public static final String LEVEL_NAME_PREFIX = "Level ";
  //in seconds
  public static final double POWER_UP_DURATION = 10;
  public static final double SCORE_FLASH_DURATION = 0.5;
  //turn this to false to have the ball bounce equally everywhere on the paddle
  public static final boolean ENABLE_VARIED_PADDLE_BOUNCES = true;

  //Brick dimensions
  public static final double BRICK_WIDTH = 65.625;
  public static final double BRICK_HEIGHT = 30;
  //Update this to be consistent with level file
  public static final int BRICKS_PER_ROW = 8;
  public static final Paint POWER_UP_BRICK_COLOR = Color.INDIANRED;
  public static final int POWER_UP_BRICK_POINTS = 2500;
  public static final int POWER_UP_BRICK_STARTING_HEALTH = 1;
  public static final Paint STANDARD_BRICK_COLOR = Color.WHITE;
  public static final int STANDARD_BRICK_POINTS = 1000;
  public static final int STANDARD_BRICK_STARTING_HEALTH = 1;
  public static final Paint STRONG_BRICK_COLOR = Color.DEEPSKYBLUE;
  public static final int STRONG_BRICK_POINTS = 2000;
  public static final int STRONG_BRICK_STARTING_HEALTH = 3;
  public static final Paint WEAKER_STRONG_BRICK_COLOR = Color.LIGHTGREEN;
  public static final int WEAKER_STRONG_BRICK_POINTS = 1500;
  public static final Paint WALL_BRICK_COLOR = Color.GRAY;
  public static final int WALL_BRICK_POINTS = 0;

  //Boss dimensions
  public static final int BOSS_STARTING_X = 100;
  public static final int BOSS_STARTING_Y = 60;
  public static final double BOSS_WIDTH = Main.WIDTH - (BOSS_STARTING_X * 2);
  public static final double BOSS_HEIGHT = 175;
  public static final int BOSS_STARTING_HEALTH = 12;
  public static final double BOSS_FLASH_DURATION = 0.05;
  public static final double BOSS_MOVE_DURATION = 7;
  public static final Paint BOSS_COLOR = Color.LIGHTGRAY;


  //PowerUp dimensions
  public static final int POWER_UP_RADIUS = 10;
  public static final int POWER_UP_FALL_SPEED = 200;
  public static final int ENLARGED_BALL_RADIUS = 16;
  public static final Color BALL_POWER_UP_COLOR = Color.GREEN;
  public static final int ENLARGED_PADDLE_WIDTH = 150;
  public static final Color PADDLE_POWER_UP_COLOR = Color.ORANGE;
  public static final int SLOW_BALL_SPEED = 250;
  public static final Color SPEED_POWER_UP_COLOR = Color.PURPLE;

  //Animation and Scene dimensions
  public static final Paint BACKGROUND = Color.BLACK;
  public static final double FPS = 60;
  public static final double SECOND_DELAY = 1.0 / FPS;


  private final Group root = new Group();
  private final boolean[] levelWon = {false};
  private boolean isPaused = false;
  private Timeline animation;
  private LevelOne firstLevel;
  private LevelTwo secondLevel;
  private LevelThree thirdLevel;
  private Level curLevel;

  /**
   *
   * @param stage - game's stage
   *
   * This method sets up the game window and loop with the first level, and also created the high
   *              score file if it does not yet exist.
   */
  @Override
  public void start(Stage stage) {
    firstLevel = new LevelOne(root);
    createHighScoreFile();

    curLevel = firstLevel;
    Scene myScene = new Scene(root, WIDTH, HEIGHT, BACKGROUND);
    myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode(), curLevel));
    stage.setScene(myScene);
    stage.setTitle(TITLE);
    stage.setResizable(false);
    stage.show();

    KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY),
        e -> step(SECOND_DELAY, root, curLevel, levelWon));
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  private void createHighScoreFile() {
    try {
      File highScoreFile = new File("data/" + HIGH_SCORE_FILE_NAME);
      if (highScoreFile.createNewFile()) {
        new FileWriter("data/" + HIGH_SCORE_FILE_NAME).close();
        FileWriter scoreWriter = new FileWriter("data/" + HIGH_SCORE_FILE_NAME);
        scoreWriter.write(0 + "");
        scoreWriter.close();
      } else {
        Scanner readScore = new Scanner(highScoreFile);
        if (!readScore.hasNextLong()) {
          new FileWriter("data/" + HIGH_SCORE_FILE_NAME).close();
          FileWriter scoreWriter = new FileWriter("data/" + HIGH_SCORE_FILE_NAME);
          scoreWriter.write(0 + "");
          scoreWriter.close();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   *
   * @param elapsedTime - time passed per frame (1/ FPS)
   * @param root - The root node of the game's scene
   * @param l - the current level of the game
   * @param levelWon - a boolean designating whether the level has been beaten or not yet
   *
   * This method is called FPS times per second and is responsible for updating all of the game
   *                 objects and checking if the level should be switched.
   */
  void step(double elapsedTime, Group root, Level l, boolean[] levelWon) {
    l.doStep(elapsedTime, root, levelWon);
    if (levelWon[0]) {
      if (curLevel == firstLevel) {
        secondLevel = new LevelTwo(root, firstLevel.getScore());
        curLevel = secondLevel;
      } else if (curLevel == secondLevel) {
        thirdLevel = new LevelThree(root, secondLevel.getScore());
        curLevel = thirdLevel;
      } else if (curLevel == thirdLevel) {
        System.out.println("You won! Congrats!");
        System.exit(0);
      }
      levelWon[0] = false;
    }

  }

  //Handle different Key inputs
  private void handleKeyInput(KeyCode code, Level l) {
    switch (code) {
      //Moves paddle left until edge of window
      case LEFT -> {
        l.movePaddleLeft();
      }
      //Moves paddle right until edge of window
      case RIGHT -> {
        l.movePaddleRight();
      }
      //Sends ball off of paddle to start game in a random direction
      case ENTER -> {
        Random r = new Random();
        l.releaseBall(-1 + (2) * r.nextDouble(), 1);
      }
      //Same as ENTER, but always sends ball to up-right direction
      case TAB -> {
        l.releaseBall(1, 1);
      }
      //Same as TAB, but ball points straight up
      case U -> {
        l.releaseBall(0, 1);
      }
      //Resets ball and paddle
      case R -> l.resetGameToStart();
      //Pauses game, hit again to unpause
      case SPACE -> pauseGame();
      //flips y direction
      case Y -> l.flipY();
      //flips x direction
      case X -> l.flipX();
      //Adds 1 life
      case L -> l.increaseLives();
      //Gives player random powerUp
      case P -> l.giveRandomPowerUp(root);
      //Executes win screen (quits game with message)
      case W -> {
        System.out.println("You won! Congrats!");
        System.exit(0);
      }
      //Destroys "last" brick on screen (furthest down and right)
      case D -> l.destroyFirstBrick(root);
      //Goes to level 1
      case DIGIT1 -> {
        firstLevel = new LevelOne(root);
        curLevel = firstLevel;
      }
      //Goes to level 2
      case DIGIT2 -> {
        secondLevel = new LevelTwo(root, curLevel.getScore());
        curLevel = secondLevel;
      }
      //Goes to level 3
      case DIGIT3 -> {
        thirdLevel = new LevelThree(root, curLevel.getScore());
        curLevel = thirdLevel;
      }
    }
  }

  //Pauses game animation
  private void pauseGame() {
    if (isPaused) {
      animation.play();
      isPaused = false;
    } else {
      animation.pause();
      isPaused = true;
    }
  }

  /**
   * @param args
   *
   * This method is run to launch the javafx application
   */
  public static void main(String[] args) {
    launch(args);
  }
}
