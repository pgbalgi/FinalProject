import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

import boofcv.gui.image.ShowImages;

public class Main {

	public static void main(String args[]) {
		/* OPEN WEBCAM */
		Dimension captureSize = new Dimension(640, 480);
		Dimension displaySize = new Dimension(640, 480);

		// Webcam webcam = Webcam.getWebcams().get(1);
		Webcam webcam = Webcam.getDefault();

		webcam.setViewSize(captureSize);

		/* This is necessary to initialize webcam */
		WebcamPanel panel = new WebcamPanel(webcam, displaySize, false);
		panel.start();

		/* This is the actual display panel */
		ClickableWindow gui = new ClickableWindow(webcam);
		gui.setPreferredSize(webcam.getViewSize());

		ShowImages.showWindow(gui, "Original Image");

		BufferedImage image;

		byte[] pixels = null, currentpixels = null, prevPixels = null;
		
		Detector detector = new Detector(captureSize);
		
		while (true) {
			// load and convert the image into a usable format
			image = webcam.getImage();

			pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

//			Point loc = gui.getLastClickedLocation();
//			if (loc != null) {
//				System.out.println("Clicked at: " + loc);
//				System.out.println("Clicked on color:  r: " + gui.getR() + " g: "
//						+ gui.getG() + " b: " + gui.getB());
//			}
//			loc = null;
			
			detector.setImage(pixels);
			
//			maskColor(pixels, gui.getR(), gui.getG(), gui.getB());

			gui.setBufferedImageSafe(image);
		}
	}

	private static void maskColor(byte[] pixels, int tr, int tg, int tb) {
		// loop over all pixels
		for (int loc = 0; loc < pixels.length; loc += 3) {
			int r = (int) pixels[loc] & 0xff; // get red
			int g = ((int) pixels[loc + 1] & 0xff); // get green
			int b = ((int) pixels[loc + 2] & 0xff); // get blue

			// do whatever you want here.
		}

	}
	
}
