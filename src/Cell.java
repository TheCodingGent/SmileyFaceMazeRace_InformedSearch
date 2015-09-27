/**
 * This class Cell is basically represents a Node on the map (can equally be
 * called Node but I prefer Cell) every cell keeps track of its heuristic value
 * the cost of reaching it from the starting pos aka the g value and the overall
 * cost f. Also a Cell knows its position on the grid and is either accessible
 * or not accessible. A Cell object might could or could not have a parent that 
 * it links to.
 * 
 * @author Yasser Ghamlouch
 *
 */
public class Cell {
	private int h = 0; //heuristic value
	private int g = 0; //movement cost
	private int f = 0; // f value = h+g;
	private Cell parent; // parent cell
	private Point position; // position of this cell on the grid
	
	private boolean isAccessible; //if this cell is walkable
	
	// Constructor for creating an accessible cell
	public Cell(int h, int g, Point pos){
		this.isAccessible = true;
		this.position = pos;
		this.h = h;
		this.g = g;
		this.f = g+h;
	}
	
	// constructor for creating an obstacle
	public Cell(Point pos){
		this.position = pos;
		this.isAccessible = false;
	}
	
	// set the parent of this cell
	public void setParent(Cell parent){
		this.parent = parent;
	}
	
	// get parent of this cell
	public Cell getParent(){
		return this.parent;
	}
	
	// update the cost g of this cell
	public void updateGValue(int value){
		g = value;
		f = g + h;
	}
	
	public int getFValue(){
		return f;
	}
	
	public Point getPos(){
		return this.position;
	}
	
	public int getGValue(){
		return g;
	}
	
	public boolean isAccessible(){
		return isAccessible;
	}
	
	// print this cell information
	public void print(){
		if(isAccessible){
			System.out.println("(h: "+ h +", g: "+g+", f: "+f+", Pos: ("+position.getX()+", "+position.getY()+" )");
		}else{
			System.out.println("Pos: ("+position.getX()+", "+position.getY()+" ) "+"Obstacle");
		}
	}
}
