import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.Arrays;

import com.github.sarxos.webcam.Webcam;

import boofcv.gui.image.ImagePanel;

public class ClickableWindow extends ImagePanel implements MouseListener {
	private static final double RADIUS = 10;
	private Webcam w;
	private int r, g, b; // red, green, blue values where last clicked
	private Point lastClickedLocation;

	public ClickableWindow(Webcam webcam) {
		super();
		w = webcam;
		addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point p = e.getPoint();
		lastClickedLocation = p;
		
		BufferedImage image = w.getImage();
		byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer())
				.getData();

		int redsum = 0;
		int greensum = 0;
		int bluesum = 0;

		int count = 0;
		for (int r = (int) (p.getY() - RADIUS); r <= p.getY() + RADIUS; r++) {
			for (int c = (int) (p.getX() - RADIUS); c <= p.getX() + RADIUS; c++) {
				int loc = 3 * ((int) r * image.getWidth() + (int) c);
				redsum += (int) pixels[loc] & 0xff;
				greensum += ((int) pixels[loc + 1] & 0xff);
				bluesum += ((int) pixels[loc + 2] & 0xff);
				count++;
			}
		}

		r = redsum / count;
		g = greensum / count;
		b = bluesum / count;

		//System.out.println("R: " + r + " G: " + g + "B: " + b);
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	public Point getLastClickedLocation() {
		return lastClickedLocation;
	}

	public int getR() {
		return r;
	}

	public int getG() {
		return g;
	}

	public int getB() {
		return b;
	}

}
