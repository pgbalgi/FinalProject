package detectors;
import java.util.ArrayList;

public class Master_Detector {
	public final int HEIGHT, WIDTH;
	ArrayList<Detector> detectors;
	private boolean[][] isEdge;
	
	public Master_Detector(byte[] newPixels, int height, int width) {
		HEIGHT = height;
		WIDTH = width;
		detectors = new ArrayList<Detector>();
		detectors.add(new Brightness(newPixels, height, width));
		detectors.add(new Vertical_Horizontal(newPixels, height, width));
		detectors.add(new Motion(newPixels, height, width));
		isEdge = new boolean[HEIGHT][WIDTH];
	}
	
	public boolean isEdge(int row, int col) {
		return isEdge[row][col];
	}

	//runs each frame and updates isEdge[][]
	public void updateImage(byte[] newPixels) {		
		isEdge = new boolean[HEIGHT][WIDTH];

		for(Detector d : detectors) {
			d.convertTo2DInt(newPixels);
		}
		
		for(int r = 0; r < HEIGHT; r++) {
			for(int c = 0; c < WIDTH; c++) {
				for(Detector d : detectors) {
					if(d.canBeEdge(r, c)) {
						isEdge[r][c] = true;
						break;
					}
				}
			}
		}
		
		for(int r = 0; r < HEIGHT; r++) {
			for(int c = 0; c < WIDTH; c++) {
				isEdge[r][c] = hasAdjacentEdge(r, c);
			}
		}	
	}
	
	//checks if the edge is adjacent to at least 2 other edges
	private boolean hasAdjacentEdge(int row, int col) {
		int adjacent = 0;
		if(!isEdge[row][col]) return false;
		if(row > 0 && isEdge[row - 1][col]) adjacent++;
		if(row < HEIGHT - 1 && isEdge[row + 1][col]) adjacent++;
		if(col > 0 && isEdge[row][col - 1]) adjacent++;
		if(col < WIDTH - 1 && isEdge[row][col + 1]) adjacent++;
		return adjacent > 1;
	}

}
