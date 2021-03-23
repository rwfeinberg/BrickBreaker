## Lab Discussion
### Team 01
### Caleb Getahun (czg3), Ryan Feinberg (rwf8)

### Issues in Current Code

#### Method or Class
 * Design issues

LevelOne.java and LevelTwo.java have mostly completely duplicated code between them. The only actual difference between the two classes is the listOfBricks instance variable. 

 * Design issue

Our collision methods (in Brick, Paddle, PowerUp, and PowerUpBrick.java), much of each of the methods share logic and could be extracted to a single method that gets passed to each class. (GameObject class that encompasses ball, paddle, bricks, powerups)


#### Method or Class
 * Design issues

In Main.java, the handleKeyInput method (starting on line 114) is too long. Can extract some methods for certain cases. Also case statements are occasionally too long.

 * Design issue


### Refactoring Plan

 * What are the code's biggest issues?

Long methods (complex methods, specifically the collision methods present in Brick, Paddle, PowerUp, and PowerUpBrick.java), duplication, magic numbers

 * Which issues are easy to fix and which are hard?

Magic numbers should be easy to fix, duplication shouldn't be too bad, we can just add more functionality to the superclass. 

Fixing the long collision methods problem will be more difficult, as we may need to implement a new abstract superclass whose subclasses need to contain Ball, Paddle, Brick, and PowerUp. 

 * What are good ways to implement the changes "in place"?

When extracting methods, don't try and complete too many steps in one refactor. Also keep affirming correct functionality every time we refactor something.

### Refactoring Work

 * Issue chosen: Fix and Alternatives

Methods should not be empty: We fixed this issue by adding a "return;" in the damage() method of AirBrick.java

 * Issue chosen: Fix and Alternatives

 Private fields should be local variables: We moved the instance variable myScene (was at line 55) to be a local variable in the start() method of Main.java (starts on line 65).
 
 
 Make a list of powerups to iterate over in step
 or use this to eliminate lookups
 
 Level switching
 
 different level ideas 
 
 maybe toggle block