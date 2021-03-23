# Game Design Final
### Ryan Feinberg (rwf8), Caleb Getahun (czg3)

## Team Roles and Responsibilities

 * Team Member #1 - Ryan Feinberg

The two of us mostly shared all of the responsibilities, as we implemented pair programming, so neither had one job over the other.

 * Team Member #2 - Caleb Getahun

The two of us mostly shared all of the responsibilities, as we implemented pair programming, so neither had one job over the other. 

## Design goals

#### What Features are Easy to Add

We designed our project so that new types of bricks, powerUps, and levels are relatively easy to add to the code. It was designed in a modular fashion so that levels and game objects could be easily changed, added, or removed from the game.

## High-level Design

The program is designed with a single class that defines the game model itself, it starts the game and creates the "game loop" that allows the game to update itself. Each level of the game has its own class, and extends a "Level" superclass. In each level, a configuration of bricks are read in through a method in the Grid class, and added to a list. This list is passed to each level, and then the "bricks" in the list are added to the game's root in the level constructor. The game objects in every level (the paddle, the ball, powerups, etc.) are also added to the game's root in this way. Each level class contains methods for the function of the level, such as checking if the level has been cleared, updating score and lives, and even moving the paddle and spawning powerups. The level class is responsible for the majority of each level's actual function in this way. Each game object, like the ones mentioned, has their own class as well, extended from a Shape from JavaFX. These classes contain methods to check their collision with other objects and update themselves in the game.

#### Core Classes

The core classes (whose function and interaction are desrcibed above) include Main.java, Grid.java, Brick.java, PowerUp.java, Paddle.java, Ball.java, and TextBox.java. (Brick and PowerUp are superclasses with associated subclasses).

## Assumptions that Affect the Design and Features Affected by Assumptions

No major assumptions were made, besides the "data" folder must be named as such and be the location of the level files and the level text files being .txt files along with following the required format as described in the README.

#### Features differing from original plan

Our original plan included a brick variation called Toggle block, which would blink in and out of the level at a certain time period. We decided to replace this idea with instead implementing a brick that takes 2 collisions to break (1 less than the Strong brick). Our plan also included a power up idea that consisted of the ball becoming a "laser ball", which would be able to collide through a certain amount of bricks in a row before bouncing back to the player, but we replaced this idea with a power up that varied the ball's speed. We also changed the original brick layout size from 10 x 8 to 4 x 8. 

## New Features HowTo

#### Easy to Add Features

New Level: In order to add a new level to the game, a new class must be created which extends Level.java (and implements the abstract methods it defined). This class should have a constructor that takes in the Main.java's root, and the previous level's score, and add all of it's game objects (defined as instance variables) to the root. All of its methods should be defined for proper function as well (can follow pattern of previous levels). In order to add it to be able to be accessed after Level Three (for example), an instance variable of the new level should be added to Main, and in the step() method, a new els eif conditional should be added to check if the curlevel is equal to thirdLevel, and if so, set the new instance variable to a new constructor of the calss, and set curLevel to that new variable. 

New type of power up: To add a new power up, a new class that extends the PowerUp.java class must be created, and the class should override the activatePowerUp abstract method in order to implement its new effect. (For consistency's sake, the effect should be run in a new timeline to have it be temporary). Then, to add it to a level, a new case in the makeRandomPowerUp method in the level subclass must be added to accomodate the new power up.

New brick type: To add a new brick variation, a new case must first be made in the getBricks method of Grid.java, and then the new brick class should be created, extending Brick.java and implementing its required abstract methods. Its health and type should be initialized in its instance variables, and its constructor should call super and then call setFill to its associated color. The overriden methods can be copied from any other Brick subclass implementation, and the unique function of the brick can be added in the class.

#### Other Features not yet Done

To add a splash screen or transition to the game, a new class should be created and its constructor should be passed the root and add its contents to it, in the same way that the level subclasses do. One can then put in the cutscreen class instance in the lines before 
```java
secondLevel = new LevelTwo(root, firstLevel.getScore());
```
In the step method of Main.java. (A different cutscene can exist before every line above for the different levels). These cutscenes can be created inside a timeline in a similar manner to how the power ups were, so that the cutscenes do not appear infinitely.