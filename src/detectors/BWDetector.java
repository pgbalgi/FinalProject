package detectors;

abstract class BWDetector extends Detector {
	protected int[][] pixelsBW;

	protected BWDetector(byte[] newPixels, int height, int width) {
		super(newPixels, height, width);
	}

	protected abstract boolean canBeEdge(int row, int col);

	protected void convertTo2DInt(byte[] newPixels) {
		pixelsBW = new int[HEIGHT][WIDTH];
		for(int i = 0; i < newPixels.length; i += 3) {
			int r = convertToIntColor(newPixels[i]); 
			int g = convertToIntColor(newPixels[i + 1]);
			int b = convertToIntColor(newPixels[i + 2]);
			pixelsBW[(i/3) / WIDTH][(i/3) % WIDTH] = (r + g + b) / 3;
		}
	}
	
	protected boolean isBorder(int row, int col) {
		if(row <= 0 || row >= HEIGHT - 1) return true;
		if(col <= 0 || col >= WIDTH - 1) return true;
		return false;
	}
	
}
