game
====
[![coverage report](https://coursework.cs.duke.edu/compsci307_2020fall/game_team01/badges/master/coverage.svg)](https://coursework.cs.duke.edu/compsci307_2020fall/game_team01/-/commits/master)
[![pipeline status](https://coursework.cs.duke.edu/compsci307_2020fall/game_team01/badges/master/pipeline.svg)](https://coursework.cs.duke.edu/compsci307_2020fall/game_team01/-/commits/master)

This project implements the game of Breakout.

Name: Caleb Getahun, Ryan Feinberg

### Timeline

Start Date: 09/13/2020

Finish Date: 09/28/2020

Hours Spent: 25

### Resources Used

StackOverFlow

### Running the Program

Main class: Run main method in Main.java to launch javaFX application. 

Data files needed: 
* 3 .txt files in a folder named "data" marked as Resources root. The three .txt files should consist of a 4 x 8 grid of numbers 0-5 (or B as the first integer), space separated. See the data folder's "levelOne.txt", "levelTwo.txt", or "levelThree.txt" for an example.
* A .txt file to be used for testing, formatted as described above, named "testLevel.txt" in the data folder.

Key/Mouse inputs:
* Left Arrow: Moves paddle left
* Right Arrow: Moves paddle right
* Enter: Starts game, ball shoots off of paddle in a random direction
* Tab: Starts game, ball shoots off of paddle to the upper right
* U: Starts game, ball shoots off of paddle straight upwards
* Space: Pause the game


Cheat keys:
* R: Reset ball and paddle to starting positions
* Y: Flip ball's y direction
* X: Flip ball's y direction
* L: Gain a life
* P: Give yourself a random power-up
* W: Cause auto game win (high score will not be recorded)
* D: Destroy last brick on screen (the brick furthest down and right)
* 1: Goto level 1 (score will be reset)
* 2: Goto level 2
* 3: Goto level 3

Known Bugs:
* If the ball collides with either corner of the paddle at a 45 degree angle, it can get stuck in the paddle.
* The ball will sometimes seem to attach to the bottom of the paddle and move under it if the ball is collided with the side of the paddle while moving
* If the ball-enlarging powerup is obtained while the ball collides with the paddle, or while the ball is sitting still on the paddle (before starting), the ball will get stuck in the paddle briefly.
* If the ball collides with two bricks on the same frame, it only bounces off of one of the bricks, and will seem to not obey physics (this happens because of the space between the bricks). This space can also cause the ball to be sent straight between two bricks if the U key is pressed without moving the paddle.


Extra credit:

N/A

### Notes/Assumptions
* Data files are .txt files.
* Directory containing data files will be named "data"
* Correct format of the level .txt files (see PLAN.md for example)
* Level .txt files will consist of the following characters: 0, 1, 2, 3, 4, 5, or B (see the PLAN.md file for descriptions)
* **The checkBossmethod() test in GameTestLevelThree.java will take a while (~30 seconds)**


### Impressions
* Overall, we thought the assignment was enjoyable, however being under a time crunch can be a bit stressful at times and not allow us to fully enjoy the process of making the game
* At times, it was hard to find a balance between trying to modularize and clean up our code vs functionality


