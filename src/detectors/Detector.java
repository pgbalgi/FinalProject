package detectors;
public abstract class Detector {
	public static int THRESHOLD;
	public final int HEIGHT, WIDTH;
	
	protected Detector(byte[] newPixels, int height, int width) {
		HEIGHT = height;
		WIDTH = width;
		convertTo2DInt(newPixels);
	}
	
	protected abstract boolean canBeEdge(int row, int col);

	protected abstract void convertTo2DInt(byte[] newPixels);
	
	protected int convertToIntColor(byte color) {
		if(color < 0) return 256 + color;
		return color;
	}

}
