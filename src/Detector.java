import java.awt.Dimension;

public class Detector{
	private int[] pixelDiff;
	private byte[] previousPixels, pixels;
	private int numRows, numCols, backgroundColor;
	
	public Detector(Dimension size) {
		numRows = (int) size.getHeight();
		numCols = (int) size.getWidth();
		pixels = null;
		previousPixels = null;
	}
	
	private void setPixelDiff() {
		pixelDiff = new int[pixels.length];
		for(int i = 0; i < pixels.length; i++) {
			pixelDiff[i] = pixels[i] - previousPixels[i];
		}
	}

	public void setImage(byte[] pixels) {
		previousPixels = this.pixels;
		this.pixels = pixels;
		setPixelDiff();
	}
	
	public byte getPreviousPixel(int r, int c) {
		return previousPixels[c + r * numCols];
	}
	
	public byte getPixel(int r, int c) {
		return pixels[c + r * numCols];
	}

	public int getColor(int pixel, int shift) {
		int pix = pixel >> shift;
		return pix & 255;
	}

}
