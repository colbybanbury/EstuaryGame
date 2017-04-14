package model;

import java.util.ArrayList;

public class Board {
	private int height;
	private int width;
	private int outerDiameter;
	private int innerDiameter;
	static ArrayList<Estuary> estuaries = new ArrayList<Estuary>();
	static int[][] boardGrid;
	
	public Board(int h, int w, int outer, int inner){
		this.height = h;
		this.width = w;
		this.outerDiameter = outer;
		this.innerDiameter = inner;
		this.boardGrid = new int[width][height];
	}
	
	public static ArrayList<Estuary> getEstuaries() {
		return estuaries;
	}

	public static void setEstuaries(ArrayList<Estuary> estuaries) {
		Board.estuaries = estuaries;
	}

	public static int[][] getBoardGrid() {
		return boardGrid;
	}

	public static void setBoardGrid(int[][] boardGrid) {
		Board.boardGrid = boardGrid;
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
