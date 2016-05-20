import java.awt.Point;
import java.util.ArrayList;

public class Object {
	private ArrayList<Point> blob;
	
	public Object() {
		blob = new ArrayList<Point>();
	}
	
	public ArrayList<Point> getBlob() {
		return blob;
	}
	
	public void add(Point loc) {
		blob.add(loc);
	}
	
	public Point getCenter() {
		if(blob.size() == 0) return null;
		int centerRow = 0, centerCol = 0;
		for(Point loc : blob) {
			centerRow += loc.getY();
			centerCol += loc.getX();
		}
		return new Point(centerRow/blob.size(), centerCol/blob.size());
	}
}
