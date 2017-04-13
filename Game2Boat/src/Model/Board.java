package Model;

import java.util.ArrayList;



//Board class stores the size of the screen, size of the racing circle, an array of all of the sections of the estuary,
//and an object array with all the objects on the board.
public class Board {
	private int height;	//size of the screen
	private int width;
	private int outerDiameter;	//size of the circle that you race in
	private int innerDiameter;
	static ArrayList<Estuary> estuaries = new ArrayList<Estuary>();	//the list of all of the estuaries on the board
	static Object[][] boardArray;	//an array the same size as the board that stores all the objects on the board
	
	public Board(int h, int w, int outer, int inner){
		this.height = h;
		this.width = w;
		this.innerDiameter = inner;
		this.outerDiameter = outer;
		this.boardArray = new Object[height][width];
	}
	
	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getOuterDiameter() {
		return outerDiameter;
	}

	public int getInnerDiameter() {
		return innerDiameter;
	}

}
