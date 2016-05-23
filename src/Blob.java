import java.awt.Point;
import java.util.ArrayList;

public class Blob {
	private ArrayList<Point> blob;
	private int minRow, minCol, maxRow, maxCol;
	
	public Blob() {
		blob = new ArrayList<Point>();
		minRow = Integer.MAX_VALUE;
		minCol = Integer.MAX_VALUE;
		maxRow = 0;
		maxCol = 0;
	}
		
	public ArrayList<Point> getBlob() {
		return blob;
	}
	
	public int getMinRow() {
		return minRow;
	}

	public int getMinCol() {
		return minCol;
	}

	public int getMaxRow() {
		return maxRow;
	}

	public int getMaxCol() {
		return maxCol;
	}

	public void add(Point p) {
		blob.add(p);
		if(p.getY() < minRow) minRow = (int) p.getY();
		if(p.getY() > maxRow) maxRow = (int) p.getY();
		if(p.getX() < minCol) minCol = (int) p.getX();
		if(p.getX() > maxCol) maxCol = (int) p.getX();
	}
	
	public void add(Blob b) {
		for(Point p : b.getBlob()) {
			add(p);
		}
	}
	
	public void remove(Point p) {
		blob.remove(p);
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
