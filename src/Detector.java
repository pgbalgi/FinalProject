public class Detector {
	public static final int THRESHOLD = 50;
	public final int HEIGHT, WIDTH;

	private boolean[][] hasChanged;
	private int[][] previousPixels, pixels;
	
	public Detector(byte[] newPixels, int height, int width) {
		HEIGHT = height;
		WIDTH = width;
		pixels = convertToInt(newPixels);
		previousPixels = new int[HEIGHT][WIDTH];
	}

	public Detector(int height, int width) {
		this(new byte[3*height*width], height, width);
	}
	
	public void updateImage(byte[] newPixels) {
		previousPixels = pixels;
		pixels = convertToInt(newPixels);
		setPixelChange();
	}
	
	private void setPixelChange() {
		hasChanged = new boolean[HEIGHT][WIDTH];
		for(int r = 0; r < HEIGHT; r++) {
			for(int c = 0; c < WIDTH; c++) {
				double pixelChange = getDistance(pixels[r][c], previousPixels[r][c]);
				if(pixelChange > THRESHOLD) hasChanged[r][c] = true;
				else hasChanged[r][c] = false;
			}
		}
	}
		
	public boolean isEdge(int row, int col) {
		if(!hasChanged[row][col]) return false;
		if(row > 0 && !hasChanged[row - 1][col]) return true;
		if(row < HEIGHT - 1 && !hasChanged[row + 1][col]) return true;
		if(col > 0 && !hasChanged[row][col - 1]) return true;
		if(col < WIDTH - 1 && !hasChanged[row][col + 1]) return true;
		return false;
	}
		
	public int getPixelValue(int value) {
		if(value < 0) return 256 + value;
		else return value;
	}
	
	public static int getColor(int r, int g, int b) {
		return b + (g << 8) + (r << 16);
	}

	public static int getRed(int pixel) {
		int pix = pixel >> 16;
		return pix & 255;
	}
	
	public static int getGreen(int pixel) {
		int pix = pixel >> 8;
		return pix & 255;
	}

	public static int getBlue(int pixel) {
		return pixel & 255;
	}
	
	private double getDistance(int color1, int color2) {
		int rDiff = getRed(color1) - getRed(color2);
		int gDiff = getGreen(color1) - getGreen(color2);
		int bDiff = getBlue(color1) - getBlue(color2);
		return Math.sqrt(rDiff*rDiff + gDiff*gDiff + bDiff*bDiff);
	}
	
	//extra
	private void printArray(boolean[] arr) {
		System.out.print("[");
		for(boolean num : arr) {
			System.out.print(num + " ");
		}
		System.out.println("]");
	}
	//extra
	private int[] copyArray(int[] arr) {
		int[] array = new int[arr.length];
		for(int i = 0; i < arr.length; i++) {
			array[i] = arr[i];
		}
		return array;
	}
	
	private int[][] convertToInt(byte[] newPixels) {
		int[][] pixels = new int[HEIGHT][WIDTH];
		for(int i = 0; i < newPixels.length; i += 3) {
			int r = newPixels[i], g = newPixels[i + 1], b = newPixels[i + 2];
			pixels[(i/3) / WIDTH][(i/3) % WIDTH] = getColor(r, g, b);
		}
		return pixels;
	}

}
