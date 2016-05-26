package main;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

import boofcv.gui.image.ShowImages;
import detectors.Master_Detector;

public class Main {
	static Webcam webcam;
	static BufferedImage image;
	static byte[] pixels;
	static Master_Detector detector;
	static ClickableWindow gui;
	
	public static void main(String args[]) {
		setup();
		while (true) {
			// load and convert the image into a usable format
			image = webcam.getImage();
			pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
			detector.updateImage(pixels);
			drawEdge();
			gui.setBufferedImageSafe(image);
		}
	}
	
	private static void setup() {
		/* OPEN WEBCAM */
		Dimension captureSize = new Dimension(640, 480);
		Dimension displaySize = new Dimension(640, 480);

		webcam = Webcam.getWebcams().get(0);
//		webcam = Webcam.getDefault();

		webcam.setViewSize(captureSize);

		/* This is necessary to initialize webcam */
		WebcamPanel panel = new WebcamPanel(webcam, displaySize, false);
		panel.start();

		/* This is the actual display panel */
		gui = new ClickableWindow(webcam);
		gui.setPreferredSize(webcam.getViewSize());

		ShowImages.showWindow(gui, "Original Image");

		image = webcam.getImage();
		pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		
		detector = new Master_Detector(pixels, image.getHeight(), image.getWidth());
	}

	private static void drawEdge() {
		for (int r = 0; r < detector.HEIGHT; r++) {
			for(int c = 0; c < detector.WIDTH; c++) {
				if(detector.isEdge(r, c)) {
					image.setRGB(c, r, getColor(0, 255, 0));
				}
			}
		}
	}
	
	public static int getColor(int r, int g, int b) {
		return b + (g << 8) + (r << 16);
	}
	
}
