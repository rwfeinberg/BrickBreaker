package breakout;

/**
 * @author Caleb Getahun
 * @author Ryan Feinberg
 *
 * The Grid Class sets the scene with bricks in the correct position from the data file that is being
 * passed in.
 *
 * There is an assumption being made that the string will always be a valid pathname with the integers
 * in the correct format.
 *
 * Dependencies: We make several calls to the Brick class to instantiate and add these bricks to the
 * scene
 *
 * Ex: Grid myGrid = new Grid("data/levelOne.txt");
 *
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Grid {

  private final double BOSS_HELPER_Y = Main.BOSS_STARTING_Y + Main.BOSS_HEIGHT + 45;
  private int unbreakableCount = 0;


  /**
   * Returns the list of Bricks to be added to level (omitting the AirBricks)
   * @param bricksFile
   * @return
   */
  public List<Brick> getBricks(String bricksFile) {
    List<Brick> bricks = new ArrayList<>();
    File file = new File(bricksFile);

    try {
      Scanner scanner = new Scanner(file);

      double x = Main.SCREEN_SIDE_BUFFER_WIDTH;
      double y = Main.SCREEN_TOP_BUFFER_WIDTH;

      //Reading from file, switch case for read int
      while (scanner.hasNext()) {
        Brick brick;
        String currBrickType = scanner.next();
        switch (currBrickType) {
          case 0 + "" -> {
            brick = new AirBrick(x, y, Main.BRICK_WIDTH, Main.BRICK_HEIGHT);
          }
          case 1 + "" -> {
            brick = new StandardBrick(x, y, Main.BRICK_WIDTH, Main.BRICK_HEIGHT);
          }
          case 2 + "" -> {
            brick = new StrongBrick(x, y, Main.BRICK_WIDTH, Main.BRICK_HEIGHT);
          }
          case 3 + "" -> {
            brick = new WeakerStrongBrick(x, y, Main.BRICK_WIDTH, Main.BRICK_HEIGHT);
          }
          case 4 + "" -> {
            brick = new PowerUpBrick(x, y, Main.BRICK_WIDTH, Main.BRICK_HEIGHT);
          }
          case 5 + "" -> {
            brick = new UnbreakableBrick(x, y, Main.BRICK_WIDTH, Main.BRICK_HEIGHT);
            unbreakableCount++;
          }
          case "B" -> {
            Brick boss = new BossBrick(Main.BOSS_STARTING_X, Main.BOSS_STARTING_Y, Main.BOSS_WIDTH,
                Main.BOSS_HEIGHT);
            bricks.add(boss);
            Brick unbreakable1 = new UnbreakableBrick(Main.SCREEN_SIDE_BUFFER_WIDTH + 10,
                BOSS_HELPER_Y, Main.BRICK_WIDTH, Main.BRICK_HEIGHT);
            Brick unbreakable2 = new UnbreakableBrick(Main.BOSS_STARTING_X + 60, BOSS_HELPER_Y,
                Main.BRICK_WIDTH, Main.BRICK_HEIGHT);
            Brick unbreakable3 = new UnbreakableBrick(
                Main.BOSS_STARTING_X + Main.BOSS_WIDTH - Main.BRICK_WIDTH - 60, BOSS_HELPER_Y,
                Main.BRICK_WIDTH, Main.BRICK_HEIGHT);
            Brick unbreakable4 = new UnbreakableBrick(Main.WIDTH - Main.BRICK_WIDTH - 30,
                BOSS_HELPER_Y, Main.BRICK_WIDTH, Main.BRICK_HEIGHT);
            bricks.add(unbreakable1);
            bricks.add(unbreakable2);
            bricks.add(unbreakable3);
            bricks.add(unbreakable4);
            unbreakableCount += 4;
            return bricks;
          }
          default -> brick = new StandardBrick(x, y, Main.BRICK_WIDTH, Main.BRICK_HEIGHT);
        }

        x += brick.getWidth() + Main.BUFFER_BETWEEN_BRICKS_WIDTH;

        if (endOfRow(x, brick)) {
          y += Main.BUFFER_BETWEEN_BRICKS_WIDTH + brick.getHeight();
          x = Main.SCREEN_SIDE_BUFFER_WIDTH;
        }

        //omit AirBrick
        if (brick.getMyType() != 0) {
          bricks.add(brick);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return bricks;
  }

  /**
   * Returns the number of unbreakable bricks present in list of bricks
   * @return
   */
  public int getUnbreakables() {
    return this.unbreakableCount;
  }

  /**
   * Returns whether we are at the end of the row or not.
   * This is used to determine where to place the bricks on the coordinate system
   * @param x
   * @param brick
   * @return true or false
   */
  private boolean endOfRow(double x, Brick brick) {
    return x >= Main.SCREEN_SIDE_BUFFER_WIDTH + (brick.getWidth() * Main.BRICKS_PER_ROW) + (
        (Main.BRICKS_PER_ROW - 1)
            * Main.BUFFER_BETWEEN_BRICKS_WIDTH);
  }
}
