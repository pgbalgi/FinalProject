package detectors;

public class Vertical_Horizontal extends BWDetector {

	public Vertical_Horizontal(byte[] newPixels, int height, int width) {
		super(newPixels, height, width);
		THRESHOLD = 40;
	}

	protected boolean canBeEdge(int row, int col) {
		if(isBorder(row, col)) return false;
		return isVerticalEdge(row, col) || isHorizontalEdge(row, col);
	}
	
	private boolean isVerticalEdge(int row, int col) {
		int diff = Math.abs(pixelsBW[row][col + 1] - pixelsBW[row][col]);
		return diff > THRESHOLD;
	}
	
	private boolean isHorizontalEdge(int row, int col) {
		int diff = Math.abs(pixelsBW[row + 1][col] - pixelsBW[row - 1][col]);
		return diff > THRESHOLD;
	}

}
