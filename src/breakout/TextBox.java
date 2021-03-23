package breakout;

/**
 * @author Ryan Feinberg
 * @author Caleb Getahun
 *
 * Purpose - This class is used as a way to initiate and define the displays during the game, such as
 * the current remaining lives, the score, and the name of the level. All of these are displayed on the top
 * of the screen of the game's window. Each of the three constructors represent the three displays defined above.
 *
 * Assumptions - Extends Text from javaFX.
 *
 * Dependencies - The only extraneous class that this class depends on is Main.java for certain constants.
 *
 * Example - Instance: private TextBox myLivesDisplay = new TextBox(myLives);
 */

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TextBox extends Text {

  public static final int TOP_OFFSET = 30;
  public static final int LIVES_WIDTH = 80;
  public static final int NAME_OFFSET = (Main.LEVEL_NAME_PREFIX.length() * 9);

  /**
   *
   * @param message - The name of the level (will be placed after "Level: " on the screen)
   */
  public TextBox(String message) {
    super(Main.WIDTH / 2 - NAME_OFFSET, TOP_OFFSET, Main.LEVEL_NAME_PREFIX + message);
    this.setFont(Font.font("Verdana", 20));
  }

  /**
   *
   * @param score - The current level's score, should be initiated in the level subclass.
   */
  public TextBox(long score) {
    super(Main.SCREEN_SIDE_BUFFER_WIDTH, TOP_OFFSET, "Score: " + String.format("%08d", score));
    this.setFont(Font.font("Verdana", 20));
  }

  /**
   *
   * @param lives - The current level's lives, should be initiated in the level subclass.
   */
  public TextBox(int lives) {
    super(Main.WIDTH - Main.SCREEN_SIDE_BUFFER_WIDTH - LIVES_WIDTH, TOP_OFFSET, "Lives: " + lives);
    this.setFont(Font.font("Verdana", 20));
  }
}
