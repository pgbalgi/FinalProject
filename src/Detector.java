import java.awt.Point;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Detector {
	public static final int THRESHOLD = 50;
	public final int HEIGHT, WIDTH;
	private Blob background;
	boolean[][] isUsed;
	private ArrayList<Blob> blobs;
	private ArrayList<Point> points;
	private boolean[][] hasChanged;
	private int[][] previousPixels, pixels;
	
	public Detector(byte[] newPixels, int height, int width) {
		HEIGHT = height;
		WIDTH = width;
		pixels = convertTo2DInt(newPixels);
		previousPixels = new int[HEIGHT][WIDTH];
		background = new Blob();
		blobs = new ArrayList<Blob>();
		isUsed = new boolean[HEIGHT][WIDTH];
		initializePoints();
	}
	
	public Detector(int height, int width) {
		this(new byte[3*height*width], height, width);
	}
	
	public void updateImage(byte[] newPixels) {
		previousPixels = pixels;
		pixels = convertTo2DInt(newPixels);
		setPixelChange();
		addBlobs();
		System.out.println(blobs.size());
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
	
	private void addBlobs() {
		while(points.size() > 0) {
			Blob blob = new Blob();
			for(Point p : points) {
				isUsed = new boolean[HEIGHT][WIDTH];
				createBlob(blob, (int) p.getY(), (int) p.getX());
			}
			if(partOfBackground(blob)) background.add(blob);
			else blobs.add(blob);
			points.removeAll(blob.getBlob());
		}
	}
	
	private void createBlob(Blob b, int row, int col) {
		if(!isInImage(row, col)) return;
		b.add(new Point(col, row));
		isUsed[row][col] = true; 
		if(!isEdge(row, col)) {	
			if(row > 0 && !isUsed[row - 1][col]) createBlob(b, row - 1, col);
			if(col < WIDTH - 1 && !isUsed[row][col + 1]) createBlob(b, row, col + 1);
			if(row < HEIGHT - 1 && !isUsed[row + 1][col]) createBlob(b, row + 1, col);
			if(col > 0 && !isUsed[row][col - 1]) createBlob(b, row, col - 1);
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

	private boolean isInImage(int row, int col) {
		if(row < 0 || row >= HEIGHT) return false;
		if(col < 0 || col >= WIDTH) return false;
		return true;
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
	
	private int[][] convertTo2DInt(byte[] newPixels) {
		int[][] pixels = new int[HEIGHT][WIDTH];
		for(int i = 0; i < newPixels.length; i += 3) {
			int r = convertToIntColor(newPixels[i]); 
			int g = convertToIntColor(newPixels[i + 1]);
			int b = convertToIntColor(newPixels[i + 2]);
			pixels[(i/3) / WIDTH][(i/3) % WIDTH] = getColor(r, g, b);
		}
		return pixels;
	}

	private int convertToIntColor(byte color) {
		if(color < 0) return 256 + color;
		return color;
	}
	
	private void initializePoints() {
		points = new ArrayList<Point>();
		for(int r = 0; r < HEIGHT; r++) {
			for(int c = 0; c < WIDTH; c++) {
				points.add(new Point(c, r));
			}
		}
	}
	
	private boolean partOfBackground(Blob b) {
		if(b.getMinRow() == 0) return true;
		if(b.getMaxRow() == HEIGHT - 1) return true;
		if(b.getMinCol() == 0) return true;
		if(b.getMaxCol() == WIDTH - 1) return true;
		return false;
	}

}
