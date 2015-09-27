/**
 * This class is used to represent a vertical or horizontal path taken
 * by the smiley while keeping track of the number of obstacles in that 
 * path.
 * 
 * @author Yasser Ghamlouch
 *
 */
final class Path {
	private final int length;
	private final int numOfObstacles;
	
	// constructor
	public Path(int length, int numOfObstacles) {
		this.length = length;
		this.numOfObstacles = numOfObstacles;
	}

    public int getLength() {
        return length;
    }

    public int getNumOfObstacles() {
        return numOfObstacles;
    }
	
}
