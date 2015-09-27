/**
 * This is a simple class that represents a point that has two coordinates on
 * the grid: X which represents what row its in and Y which represents what
 * column it falls in.
 * 
 * @author Yasser Ghamlouch
 *
 */
public class Point {
	private int xPos;
	private int yPos;
	
	public Point(int x, int y){
		this.xPos = x;
		this.yPos = y;
	}
	
	public int getX(){
		return this.xPos;
	}
	
	public int getY(){
		return this.yPos;
	}
	
	// method used to compare two points for equality
	public boolean equals(Point p){
		if((this.xPos == p.getX()) && (this.yPos == p.getY())){
			return true;
		}else{
			return false;
		}
	}
	
	// prints the postion
	public void print(){
		System.out.println("( "+xPos+", "+yPos+" )");
	}
}
