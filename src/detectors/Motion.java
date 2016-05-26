package detectors;

public class Motion extends Detector {
	private int[][] previousPixels, pixels;

	public Motion(byte[] newPixels, int height, int width) {
		super(newPixels, height, width);
		THRESHOLD = 40;
	}

	protected boolean canBeEdge(int row, int col) {
		double pixelChange = getDistance(pixels[row][col], previousPixels[row][col]);
		return pixelChange > THRESHOLD;
	}

	protected void convertTo2DInt(byte[] newPixels) {
		pixels = new int[HEIGHT][WIDTH];
		for(int i = 0; i < newPixels.length; i += 3) {
			int r = convertToIntColor(newPixels[i]); 
			int g = convertToIntColor(newPixels[i + 1]);
			int b = convertToIntColor(newPixels[i + 2]);
			pixels[(i/3) / WIDTH][(i/3) % WIDTH] = getColor(r, g, b);
		}
		previousPixels = pixels;
	}
	
	private static int getColor(int r, int g, int b) {
		return b + (g << 8) + (r << 16);
	}

	private double getDistance(int color1, int color2) {
		int rDiff = getRed(color1) - getRed(color2);
		int gDiff = getGreen(color1) - getGreen(color2);
		int bDiff = getBlue(color1) - getBlue(color2);
		return Math.sqrt(rDiff*rDiff + gDiff*gDiff + bDiff*bDiff);
	}
	
	private static int getRed(int pixel) {
		int pix = pixel >> 16;
		return pix & 255;
	}
	
	private static int getGreen(int pixel) {
		int pix = pixel >> 8;
		return pix & 255;
	}

	private static int getBlue(int pixel) {
		return pixel & 255;
	}

}
