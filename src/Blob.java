import java.util.ArrayList;

public class Blob {
	private ArrayList<Location> blob;
	private double time;
	
	public Blob() {
		blob = new ArrayList<Location>();
	}
	
	public ArrayList<Location> getBlob() {
		return blob;
	}
	
	public void add(Location loc) {
		blob.add(loc);
	}
}
