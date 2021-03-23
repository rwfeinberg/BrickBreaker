# Game Plan
## NAMEs
Caleb Getahun, Ryan Feinberg


### Breakout Variation Ideas

### Interesting Existing Game Variations

 * Game 1
Breakout Hero: Every few turns or on command, have a boss type of level where the user has to shoot 
balls at a figure a specified number of times. 
Could also incorporate smaller enemies in addition to the main boss.

 * Game 2
Super Breakout: certain blocks have a chance of dropping power up abilities such as laser, multiple 
balls, etc. as well as having these abilities appear every certain amount of points.


#### Block Ideas

 * Block 1
 Power up block: on contact, provide user with special abilities

 * Block 2
 Strong blocks: multiple hits upon the block to break it.
 
 * Block 3
 Toggle block: the block goes invisible on a random time interval. 


#### Power Up Ideas

 * Power Up 1
 Different Paddle Size: user changes size of paddle
 
 * Power Up 2
 Laser-ball: ball that goes through a specified number of blocks on contact

 * Power Up 3
 Bigger ball: makes the ball a specific size larger than the original


#### Cheat Key Ideas

 * Cheat Key 1
 'R': reset key to reset the current ball position

 * Cheat Key 2
 ' ': space to pause or unpause the game

 * Cheat Key 3
 'N': to skip to the next level

 * Cheat Key 4
 '+' and '-' arrow keys: adjust the length of the paddle for user difficulty


#### Level Descriptions
General block configuration: 10 rows X 8 columns

Legend:
0: Air
1: Standard
2: Strong
3: WeakerThanStrong
4: Power-up
5: Unbreakable wall block
B: Generate pre-made boss map level


 * Level 1
   * Block Configuration
   0 0 0 0 0 0 0 0
   0 3 0 0 0 0 3 0
   0 0 0 5 5 0 0 0
   0 0 0 0 0 0 0 0
   
   
   * Variation features
   Small number of blocks (~4) where the user has to hit all of them in a specified time limit. 
   Could also include special blocks (invisible/toggle blocks, moving blocks, etc.)
   Point total accumulated.

 * Level 2
   * Block Configuration
   1 1 1 4 4 1 1 1
   2 2 2 2 2 2 2 2
   1 4 1 1 1 1 4 1
   1 1 1 1 1 1 1 1
   

   * Variation features
   Special block challenge: normal blocks, power up blocks, and strong blocks make up the configuration.
   
 * Level 3
   * Block Configuration
   B 0 0 0 0 0 0 0
   0 0 0 0 0 0 0 0
   0 0 0 0 0 0 0 0
   0 0 0 0 0 0 0 0
   
   * Variation features
   Breakout Hero boss with unbreakable walls protecting him.
   Boss can control certain commands (paddle length toggle, ball speed, etc.)
   

### Possible Classes

 * Ball
   * Purpose
   Control properties of ball, position, velocity, size
   * Method
   changeSize of ball: set instance variables of ball object up or down
   
 * Paddle
   * Purpose
   Control paddle's movement and size 

   * Method
   changeLength: using + and - key, update instance variable of size for paddle object

 * Brick
   * Purpose
   Have the generic brick implementation including variables of constant size, color, times until it 
   breaks, and if the block moves or not. These would be used for the standard block implementation 
   and would be inherited by other block classes and have added functionality based on the block type.

   * Method
   setPosition: given x and y coordinate based on position in input grid, determine position.

 * Boss
   * Purpose
   Contain behaviors and actions of boss in boss level. Would include attributes such as size, position, 
   and available actions (toggle paddle size, change ball speed, etc.)

   * Method
   setAppearance: color, size, shape, abilities, and other attributes

 * Grid
   * Purpose
   Set window up with blocks, paddle, scoreboard, timer, etc. for the specified level.

   * Method
   setPointCounter: set up and initialize a score counter to be 0.