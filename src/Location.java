public class Location {
	public static final double RIGHT = 0;
	public static final double UPPER_RIGHT = Math.PI/4;
	public static final double UP = Math.PI/2;
	public static final double UPPER_LEFT = 3*Math.PI/4;
	public static final double LEFT = Math.PI;
	public static final double LOWER_LEFT = 5*Math.PI/4;
	public static final double DOWN = 3*Math.PI/2;
	public static final double LOWER_RIGHT = 7*Math.PI/4;

	private int row;
	private int col;
	
	public Location(int r, int c) {
		row = r;
		col = c;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public Location getAdjacentLocation(double direction) {
		int newRow = row + round(Math.sin(direction));
		int newCol = col + round(Math.cos(direction));
		return new Location(newRow, newCol);
	}
	
	public int round(double num) {
		double decimal = num - (int) num;
		if(decimal < 0.5) return (int) num;
		else return 1 + (int) num;
	}
	
	public String toString() {
		return "[" + row + ", " + col + "]";
	}
	
}