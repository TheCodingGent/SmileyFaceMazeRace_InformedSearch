import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * This class is the main class of the game where all the data of a specific 
 * maze is initialized (size, obstacle positions) and where the smiley face 
 * position is set. 
 * The main functionality of this class is to take a starting position and 
 * to perform an A* search to the goal position from that position.
 * 
 * @author Yasser Ghamlouch
 *
 */
public class Game {
	// params of the maze, can be modified for different mazes
	private static final int WIDTH_FIRST_MAZE = 7;
	private static final int HEIGTH_FIRST_MAZE = 7;
	
	private static final int WIDTH_SECOND_MAZE = 9;
	private static final int HEIGTH_SECOND_MAZE = 9;
	
	private static final int NUM_OF_SMILEYS = 4;
	
	private Maze mMaze;
	private int mazeId;
	
	// the positions of all the obstacles are defined here.
	private Point[] obstaclesFirstMaze = {
			new Point(0,3),
			new Point(1,1),
			new Point(1,4),
			new Point(2,1),
			new Point(2,4),
			new Point(3,1),
			new Point(3,2),
			new Point(3,4),
			new Point(4,2),
			new Point(5,2),
			new Point(5,3),
			new Point(5,4)
	};
	
	private Point[] obstaclesSecondMaze = {
			new Point(0,3),
			new Point(1,1),
			new Point(1,4),
			new Point(2,1),
			new Point(2,4),
			new Point(3,1),
			new Point(3,2),
			new Point(3,4),
			new Point(4,2),
			new Point(5,2),
			new Point(5,3),
			new Point(5,4),
			new Point(6,5),
			new Point(2,7),
			new Point(2,8),
			new Point(3,7),
			new Point(3,8),
			new Point(4,7),
			new Point(4,8),
			new Point(5,7),
			new Point(5,8),
			new Point(6,7),
			new Point(6,8)
	};
	
	// the goal point is defined here
	private Point goal = new Point(3,3);
	
	private SmileyFace bob;
	
	// constructor of the game where data is initialized
	public Game(Point startingPoint, int smileyId, int mazeNumber){
		if(mazeNumber == 1){
			this.mMaze = new Maze(WIDTH_FIRST_MAZE, HEIGTH_FIRST_MAZE, obstaclesFirstMaze, goal);
			this.mazeId = 1;
		}else if(mazeNumber == 2){
			this.mMaze = new Maze(WIDTH_SECOND_MAZE, HEIGTH_SECOND_MAZE, obstaclesSecondMaze, goal);
			this.mazeId = 2;
		}else{
			System.out.println("No maze numbered: "+mazeNumber+" exists!");
		}
		
		bob = new SmileyFace(smileyId, startingPoint);
	}
	
	// called at the beginning to show the world
	public void init(){
		mMaze.printMaze();
	}
	
	// This method is the A* search algorithm, starts from the starting point
	// and performs and A* search to the goal and returns the shortest path
	// back to the call in an ArrayList.
	private ArrayList<Cell> aStarSearch(){
		Comparator<Cell> comparator = new CellComparator();
		// a queue to hold the nodes that are to be visited in order
		PriorityQueue<Cell> openList = 
				new PriorityQueue<Cell>(mMaze.getGridSize(), comparator);
		ArrayList<Cell> closedList = new ArrayList<Cell>();
		
		ArrayList<Cell> path = new ArrayList<Cell>();
		
		openList.add(mMaze.getCellAt(bob.getStartingPos()));
		
		while(true){
			// if the open list becomes empty and no dest has been reached
			if(openList.isEmpty()){
				System.out.println("no path was found");
			}
			
			Cell current = openList.remove(); //take the cell with the lowest f cost
			
			//System.out.print("( "+current.getPos().getX()+", "+current.getPos().getY()+" )" +"\t F: "+current.getFValue());
			
			closedList.add(current); // switch it to the closed list
			
			// the goal has been reached stop and track back the traveled nodes
			if(current.getPos().equals(goal)){
				Cell temp = mMaze.getCellAt(goal);
				path.add(temp);
				while(temp != null){
					temp = temp.getParent();
					path.add(temp);
				}
				break; //goal reached
			}
			
			//check cell on the right
			Cell right = getCellToRight(current);
			if(right != null && right.isAccessible() && !closedList.contains(right)){
				if(!openList.contains(right)){ // not in the open list
					right.setParent(current); // set the current to be the parent
					// update the g value, the h value is already precomputed and 
					// the f value will be updated automatically
					right.updateGValue(right.getParent().getGValue()+1);
					openList.add(right); // add it to the open list
				}else{ // it is already on the open list
					int currGValue = right.getGValue(); // get the current G value
					//compute the new g value from this current node
					int newGValue = current.getGValue()+1;
					
					if(newGValue < currGValue){
						openList.remove(right);
						right.setParent(current);
						right.updateGValue(newGValue);
						openList.add(right);
					}
				}
			}
			
			//check cell on the left
			Cell left = getCellToLeft(current);
			if(left != null && left.isAccessible() && !closedList.contains(left)){
				if(!openList.contains(left)){ // not in the open list
					left.setParent(current); // set the current to be the parent
					// update the g value, the h value is already precomputed and 
					// the f value will be updated automatically
					left.updateGValue(left.getParent().getGValue()+1);
					openList.add(left); // add it to the open list
				}else{ // it is already on the open list
					int currGValue = left.getGValue(); // get the current G value
					//compute the new g value from this current node
					int newGValue = current.getGValue()+1;
					
					if(newGValue < currGValue){
						openList.remove(left);
						left.setParent(current);
						left.updateGValue(newGValue);
						openList.add(left);
					}
				}
			}
			
			//check cell at the top
			Cell top = getCellToTop(current);
			if(top != null && top.isAccessible() && !closedList.contains(top)){
				if(!openList.contains(top)){ // not in the open list
					top.setParent(current); // set the current to be the parent
					// update the g value, the h value is already precomputed and 
					// the f value will be updated automatically
					top.updateGValue(top.getParent().getGValue()+1);
					openList.add(top); // add it to the open list
				}else{ // it is already on the open list
					int currGValue = top.getGValue(); // get the current G value
					//compute the new g value from this current node
					int newGValue = current.getGValue()+1;
					
					if(newGValue < currGValue){
						openList.remove(top);
						top.setParent(current);
						top.updateGValue(newGValue);
						openList.add(top);
					}
				}
			}
			
			//check cell at the bottom
			Cell bottom = getCellToBottom(current);
			if(bottom != null && bottom.isAccessible() && !closedList.contains(bottom)){
				if(!openList.contains(bottom)){ // not in the open list
					bottom.setParent(current); // set the current to be the parent
					// update the g value, the h value is already precomputed and 
					// the f value will be updated automatically
					bottom.updateGValue(bottom.getParent().getGValue()+1);
					openList.add(bottom); // add it to the open list
				}else{ // it is already on the open list
					int currGValue = bottom.getGValue(); // get the current G value
					//compute the new g value from this current node
					int newGValue = current.getGValue()+1;
					
					if(newGValue < currGValue){
						openList.remove(bottom);
						bottom.setParent(current);
						bottom.updateGValue(newGValue);
						openList.add(bottom);
					}
				}
			}
		}
		return path;
	}
	
	// A list of methods to get the adjacent cells to a certain cell.
	
	private Cell getCellToRight(Cell current){
		if(mazeId == 1){
			if(current.getPos().getY()+1 < WIDTH_FIRST_MAZE){
				return mMaze.getCellAt(new Point(current.getPos().getX(), current.getPos().getY()+1));
			}
		}else if(mazeId == 2){
			if(current.getPos().getY()+1 < WIDTH_SECOND_MAZE){
				return mMaze.getCellAt(new Point(current.getPos().getX(), current.getPos().getY()+1));
			}
		}
		
		return null;
	}
	
	private Cell getCellToLeft(Cell current){
		if(current.getPos().getY()-1 >= 0){
			return mMaze.getCellAt(new Point(current.getPos().getX(), current.getPos().getY()-1));
		}
		return null;
	}
	
	private Cell getCellToTop(Cell current){
		if(current.getPos().getX()-1 >= 0){
			return mMaze.getCellAt(new Point(current.getPos().getX()-1, current.getPos().getY()));
		}
		return null;
	}
	
	private Cell getCellToBottom(Cell current){
		if(mazeId == 1){
			if(current.getPos().getX()+1 < HEIGTH_FIRST_MAZE){
				return mMaze.getCellAt(new Point(current.getPos().getX()+1, current.getPos().getY()));
			}
		}else if(mazeId == 2){
			if(current.getPos().getX()+1 < HEIGTH_SECOND_MAZE){
				return mMaze.getCellAt(new Point(current.getPos().getX()+1, current.getPos().getY()));
			}
		}
		
		return null;
	}
	
	public int getSmileyId(){
		return bob.getId();
	}
	
	
	/*--------------------------------------------------------------------------
	 * Helper Classes Algorithms
	 -------------------------------------------------------------------------*/	
	/**
	 * This class is a comparator that defines how to compare two cells based
	 * on their f values.
	 * @author Yasser
	 *
	 */
	private class CellComparator implements Comparator<Cell>
	{
		@Override
		public int compare(Cell x, Cell y)
		{
			// Assume neither string is null. Real code should
			// probably be more robust
			// You could also just return x.length() - y.length(),
			// which would be more efficient.
			if (x.getFValue() < y.getFValue())
			{
				return -1;
			}
			if (x.getFValue() > y.getFValue())
			{
				return 1;
			}
			return 0;
		}
	}
	
	// performs the A* search and prints the data
	public SmileyFace run(){
		ArrayList<Cell> path = aStarSearch();
		int pathLength = path.size()-1;
		System.out.println("\n\nThe path lenght traveled by smiley "+bob.getId()+" is: "+pathLength);
		for (int i = (path.size()-2); i > -1; i--){
			path.get(i).getPos().print();
		}
		bob.setTraveledDistance(pathLength);
		return bob;
	}
	
	public static Point extractPoint(String s){
		return new Point(Character.getNumericValue(s.charAt(0)),Character.getNumericValue(s.charAt(2)));
	}
	
	public static void findWinner(SmileyFace [] bunchOfSmileys){
		int min = 100;
		ArrayList<Integer> winners = new ArrayList<Integer>();
		
		for(int i = 0; i < bunchOfSmileys.length; i++){
			int dist = bunchOfSmileys[i].getTraveledDistance(); 
			if(dist <= min){
				if(dist > min){
					winners.clear();
				}
				min = dist;
				winners.add(bunchOfSmileys[i].getId());
			}
		}
		
		System.out.println("\nThe winner(s) smiley(s): ");
		for(int i = 0; i < winners.size(); i++){
			System.out.print(winners.get(i)+"  ");
		}
	}

	/*--------------------------------------------------------------------------
	 * Testing the game with main
	 -------------------------------------------------------------------------*/
	// The list of arguments passed to main are the x,y positions of each smiley
	// face, the coordinates of one smiley are separated by a ";" 
	// and the different positions of the four smileys are separated by spaces
	// for example: 0;0 6;0 6;6 0;6 will give the example given in the 
	// assignment. After the positions there's a space and one more argument 
	// representing the maze which you want to run either "1" for maze 1 or
	// "2" for maze 2.
	
	public static void main(String args[]){
		System.out.println("Let the games begin!!!!");
		
		SmileyFace smileyFaces[] = new SmileyFace[NUM_OF_SMILEYS];
		
		// First Smiley
		Game g1 = new Game(Game.extractPoint(args[0]), 1, Integer.parseInt(args[4]));
		g1.init();
		smileyFaces[0] = g1.run();
		
		// Second Smiley
		Game g2 = new Game(Game.extractPoint(args[1]), 2, Integer.parseInt(args[4]));
		smileyFaces[1] = g2.run();
		
		// Third Smiley
		Game g3 = new Game(Game.extractPoint(args[2]), 3, Integer.parseInt(args[4]));
		smileyFaces[2] = g3.run();
		
		// Fourth Smiley
		Game g4 = new Game(Game.extractPoint(args[3]), 4, Integer.parseInt(args[4]));
		smileyFaces[3] = g4.run();
		
		Game.findWinner(smileyFaces);
	}
}
