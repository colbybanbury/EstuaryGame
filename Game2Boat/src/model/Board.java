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
	
	public Board(int w, int h, int lapL, int r){
		this.height = h;
		this.width = w;
		this.radius = r;
		this.lapLength = lapL;
		for(int i= 0; i<estuaryCount/3; i++){
			double tempTheta = (2*Math.PI*i) / estuaryCount;
			double tempX =   width/2 + (radius+65) * Math.cos(tempTheta);
			double tempY = height/2 + (radius+65) * Math.sin(tempTheta);
			lapPath[i] = new Estuary(0, (int)tempX, (int)tempY);
		}
		for(int i= estuaryCount/3; i<estuaryCount; i++){
			double tempTheta = (2*Math.PI*i) / estuaryCount;
			double tempX =   width/2 + (radius+100) * Math.cos(tempTheta);
			double tempY = height/2 + (radius+100) * Math.sin(tempTheta);
			lapPath[i] = new Estuary(2, (int)tempX, (int)tempY);
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
