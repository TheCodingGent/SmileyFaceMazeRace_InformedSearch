# SmileyFaceMazeRace_InformedSearch
A Java program that simulates a race of the four smiley faces located in the four corners of a maze with obstacles towards a goal located at a certain position on the map. Assuming that they each use the A* algorithm to draw their path.

# Description
In this assignment we were given a maze with a set of obstacles with known positions, a goal, and a starting point
for a character (a smiley face). 

The following program (contained in this source folder) provides: 
- A representation of the Maze as a two dimensial matrix of Cells (a cell is a node). Please note that indexing starts at 0 and that
the position 0,0 is the top left of the maze.
 
- A representation of every location in the maze as a Cell object with a set of attributes all well explained in the code.

- Implements an informed search algorithm which is the A* search in the main Game class.
	- The algorithm knowing the starting point and the destination cell will provide back the shortest
	path from source to destination.

All the code is inside the folder src. There are 7 classes: Cell.java, Game.java (main class), Maze.java, OutOfBoundsException.java, 
Path.java, Point.java, SmileyFace.java.


# Usage
The first step is to compile the main file Game.java, either using the IDE of your choice or through the
command line by calling javac command.

The program has a main method in the Game class that contains some code to test the algorithm with the first maze
and with the smiley faces starting in each corner of the maze. 

In order to test the program:

.Run the program (in your IDE or cmd) by providing four parameters to the main function as follows: java Game x1;y1 x2;y2 x3;y3 x4;y4 mazeId(either 1 or 2)
A sample argument currently provided to the program is: "0;0 6;0 6;6 0;6 1" this will create four smileys, one in each corner of the first maze, and it will
select maze 1 for testing.


# Output
The output of the program has three parts:
1) The first output printed out is the maze itself.

2) The second output is the path traveled by each smiley face and its length.

3) The final output is the winner(s) of the race, i.e the ids of the smiley faces with the shortest path traveled.


# Thank you!
