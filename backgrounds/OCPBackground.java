import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class OCPBackground implements Background {

	public static final int TILE_HEIGHT = 5;
	public static final int TILE_WIDTH  = 5;
	
	private static int maxRows;
	private static int maxCols;
	
	private static Tile image;
	private static Image redSquare;
	private static Image purpleSquare;
	
	private int[][] map = new int[][] { 
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
	};
	
									
	public ArrayList<DisplayableSprite> barriers = new ArrayList<DisplayableSprite>();
	
	public OCPBackground() {
		try {
			this.redSquare = ImageIO.read(new File("res/background/red.png"));
			this.purpleSquare = ImageIO.read(new File("res.background/red.png"));
		}
		catch(IOException e) {
			
		}
		maxRows = map.length - 1;
    	maxCols = map[0].length - 1;
	}
	@Override
	public Tile getTile(int col, int row) {
		if (row < 0 || row > maxRows || col < 0 || col > maxCols) {
			image = null;
		}
		else if (map[row][col] == 0) {
			image = redSquare;
		}
		else if (map[row][col] == 1) {
			image = purpleSquare;
		}
		return image;
	}

	@Override
	public int getCol(double x) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRow(double y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getShiftX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getShiftY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setShiftX(double shiftX) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setShiftY(double shiftY) {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<DisplayableSprite> getBarriers() {
		return barriers;
	}

}
