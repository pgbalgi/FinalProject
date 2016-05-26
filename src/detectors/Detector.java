package detectors;
public abstract class Detector {
	public static int THRESHOLD;
	public final int HEIGHT, WIDTH;
	
	protected Detector(byte[] newPixels, int height, int width) {
		HEIGHT = height;
		WIDTH = width;
		convertTo2DInt(newPixels);
	}
	
	//returns whether a pixel is an edge according to the detector
	protected abstract boolean canBeEdge(int row, int col);

	//converts the 1D byte array to a 2D int array
	protected abstract void convertTo2DInt(byte[] newPixels);
	
	//converts a byte color (can be negative) to an int color
	protected int convertToIntColor(byte color) {
		if(color < 0) return 256 + color;
		return color;
	}

}
