/**
 * This class represents a smiley face that moves around the board
 * has an id and a starting position
 * 
 * @author Yasser Ghamlouch
 *
 */
public class SmileyFace {
	private int id;
	private Point startPos;
	private int traveledDistance;
	
	public SmileyFace(int id, Point start){
		this.setId(id);
		this.startPos = start;
	}
	
	public Point getStartingPos(){
		return startPos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setTraveledDistance(int dist){
		traveledDistance = dist;
	}
	
	public int getTraveledDistance(){
		return traveledDistance;
	}
}
