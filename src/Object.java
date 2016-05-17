import java.util.ArrayList;

public class Object {
	private ArrayList<Location> blob;
	private double time;
	
	public Object() {
		blob = new ArrayList<Location>();
	}
	
	public ArrayList<Location> getBlob() {
		return blob;
	}
	
	public void add(Location loc) {
		blob.add(loc);
	}
	
	public Location getCenter() {
		if(blob.size() == 0) return null;
		int centerRow = 0, centerCol = 0;
		for(Location loc : blob) {
			centerRow += loc.getRow();
			centerCol += loc.getCol();
		}
		return new Location(centerRow/blob.size(), centerCol/blob.size());
	}
}
