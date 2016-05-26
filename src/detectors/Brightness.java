package detectors;

public class Brightness extends BWDetector {

	public Brightness(byte[] newPixels, int height, int width) {
		super(newPixels, height, width);
		THRESHOLD = 40;
	}

	protected boolean canBeEdge(int row, int col) {
		if(isBorder(row, col)) return false;
		int dx = Math.abs(pixelsBW[row][col - 1] - pixelsBW[row][col + 1]);
		int dy = Math.abs(pixelsBW[row - 1][col] - pixelsBW[row + 1][col]);
		double diff = Math.sqrt(dx*dx + dy*dy);
		return diff > THRESHOLD;
	}
	
}
