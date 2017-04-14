package model;

import java.util.ArrayList;

//Board class stores the size of the screen, size of the racing circle, an array of all of the sections of the estuary,
//and an object array with all the objects on the board.
public class Board {
	private int height;
	private int width;
	private int lapLength;	//
	final int estuaryCount = 10; //the number of estuary shore lines
	Estuary[] lapPath = new Estuary[estuaryCount];
	
	public Board(int h, int w, int lL){
		this.height = h;
		this.width = w;
		this.lapLength = lL;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getLapLength() {
		return lapLength;
	}

	public int getEstuaryCount() {
		return estuaryCount;
	}

	public Estuary[] getLapPath() {
		return lapPath;
	}
	
	
	
}
