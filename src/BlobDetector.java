import java.awt.Dimension;

public class BlobDetector {
	private int[][] image;
	private int numRows, numCols;
	
	public BlobDetector(Dimension size) {
		numRows = (int) size.getHeight();
		numCols = (int) size.getWidth();
	}

	private byte[][] getImage(byte[] pixels) {
		byte[][] image = new byte[numRows][numCols];
		for(int r = 0; r < image.length; r++) {
			for(int c = 0; c < image[0].length; c++) {
				image[r][c] = pixels[c + r * numCols];
			}
		}
		return image;
	}
	
}
