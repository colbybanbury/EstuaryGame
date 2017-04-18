package model;

import java.util.ArrayList;

import controller.BoatController;

//Board class stores the size of the screen, size of the racing circle, an array of all of the sections of the estuary,
//and an object array with all the objects on the board.
/**
 * @author colby
 *
 */
public class Board {
	private int height;
	private int width;
	private int radius;
	private int lapLength;	//
	final int estuaryCount = 20; //the number of estuary shore lines
	Estuary[] lapPath = new Estuary[estuaryCount];
	
	
	public Board(int w, int h, int r, int lapL){
		this.height = h;
		this.width = w;
		this.radius = r;
		this.lapLength = lapL;
		for(int i= 0; i<estuaryCount; i++){
			lapPath[1] = new Estuary(2);
		}
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getRadius() {
		return radius;
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
