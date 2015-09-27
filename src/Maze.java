/**
 * This class represents the maze. It mainly consists of a 2D array of Cells
 * that are either normal cells or obstacles. This is where all the attributes
 * of the world are set up.
 * 
 * @author Yasser Ghamlouch
 *
 */
public class Maze {
	
	private int gridSize = 0; // size of the map, width*height
	private Point goal; // the goal position
	private Cell[][] grid;
	
	public Maze(int width, int height, Point[] obstacles, Point goal){
		this.grid = new Cell[width][height];
		this.gridSize = width*height;
		
		// set the obstacle position, if they're not valid an exception will be
		// thrown
		try {
			setObstacles(obstacles);
		} catch (OutOfBoundsException e) {
			e.printStackTrace();
		}
		
		// set the goal if valid
		try {
			setGoal(goal);
		} catch (OutOfBoundsException e) {
			e.printStackTrace();
		}
		
		//fill up all remaining positions with normal cells
		setCells();
	}
	
	// This method sets all the obstacles and unaccessible areas
	private void setObstacles(Point[] obstacles) throws OutOfBoundsException{
		if(obstacles.length > this.gridSize){
			throw new OutOfBoundsException("Too many obstacles for this grid");
		}
		
		int i;
		for(i = 0; i < obstacles.length; i++){
			int x = obstacles[i].getX();
			int y = obstacles[i].getY();
			
			// error checking
			if(!isValidPosition(obstacles[i])){
				throw new OutOfBoundsException("Position of this obstacle is out of bounds");
			}
			this.grid[x][y] = new Cell(obstacles[i]); // creates an obstacle
		}
	}
	
	// set the postion of the goal destination
	private void setGoal(Point goalPos) throws OutOfBoundsException{
		if(!isValidPosition(goalPos)){
			throw new OutOfBoundsException("Position of this goal is out of bounds");
		}else{
			this.goal = goalPos;
		}
	}
	
	// fill the remaining grid positions with cells
	private void setCells(){
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid[0].length; j++){
				if(grid[i][j] == null){
					grid[i][j] = new Cell(calculateHeuristicValue(i, j),0, new Point(i,j));
				}
			}
		}
	}
	
	// checks if a position is valid on the grid or out of bounds
	private boolean isValidPosition(Point p){
		boolean isValid = true;
		int x = p.getX();
		int y = p.getY();
		if(x<0 || x>grid[0].length || y<0 || y>grid.length){
			isValid = false;
		}
		return isValid;
	}
	
	// This method is essential, it precalculates all the heuristic values
	// of all the cells.
	private int calculateHeuristicValue(int x, int y){
		Path vertPath = calculateVerticalSquares(x, y);
		//System.out.print("Cell: "+x+", "+y+" vertical length: "+vertPath.getLength() +" Num of Obstacles: "+vertPath.getNumOfObstacles());
		
		Path horizPath = null;
		if(x <= goal.getX()){
			horizPath = calculateHorizontalSquares(x+vertPath.getLength(), y);
		}else if(x > goal.getX()){
			horizPath = calculateHorizontalSquares(x-vertPath.getLength(), y);
		}
		//System.out.println("\thorizontal length: "+horizPath.getLength()+" Num of Obstacles: "+horizPath.getNumOfObstacles());
		
		// heuristic function which is the sum of vertical, horizontal 
		// and obstacles number of cells
		int h = vertPath.getLength()+horizPath.getLength()+
				vertPath.getNumOfObstacles()+horizPath.getNumOfObstacles();
		return h;
	}
	
	// used in the calculation of the heuristic value
	// calculates how many vertical squares are needed to travel to the goal
	// and how many obstacles are in the way and returns them in a path object
	private Path calculateVerticalSquares(int x, int y){
		int count = 0;
		int numOfObst = 0;
		int goalX = goal.getX();
		
		if(x <= goalX){
			while(x < goalX){
				x++;
				count++;
				if(grid[x][y] != null && !grid[x][y].isAccessible()){ // encountered an obstacle
					numOfObst++;
				}
			}
		}else if(x > goalX){
			while(x > goalX){
				x--;
				count++;
				if(grid[x][y] != null && !grid[x][y].isAccessible()){ // encountered an obstacle
					numOfObst++;
				}
			}
		}
		return new Path(count, numOfObst);
	}
	
	// used in the calculation of the heuristic value
	// calculates how many horizontal squares are needed to travel to the goal
	// and how many obstacles are in the way and returns them in a path object
	private Path calculateHorizontalSquares(int x, int y){
		int count = 0;
		int numOfObst = 0;
		int goalY = goal.getY();
		
		if(y <= goalY){
			while(y < goalY){
				y++;
				count++;
				if(grid[x][y] != null && !grid[x][y].isAccessible()){ // encountered an obstacle
					numOfObst++;
				}
			}
		}else if(y > goalY){
			while(y > goalY){
				y--;
				count++;
				if(grid[x][y] != null && !grid[x][y].isAccessible()){ // encountered an obstacle
					numOfObst++;
				}
			}
		}
		return new Path(count, numOfObst);
	}
	
	// this function prints the maze and represents the obstacles on it
	public void printMaze(){
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid[0].length; j++){
				if(grid[i][j] != null){
					if(grid[i][j].isAccessible()){
						if(goal.equals(new Point(i, j))){
							System.out.print("|G|");
						}else{
							System.out.print("| |");
						}
						
					}else{
						System.out.print("|X|");
					}
				}else{
					System.out.print("-");
				}
				System.out.print("\t");
			}
			System.out.print("\n");
		}
	}
	
	public int getGridSize(){
		return gridSize;
	}
	
	// returns the cell at the specified position
	public Cell getCellAt(Point p){
		return grid[p.getX()][p.getY()];
	}
}
