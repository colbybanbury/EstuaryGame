package model;

import java.util.ArrayList;

import controller.BoatController;
import enums.POWER_UP;

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
	final int lapDivisions = 20; //the number of estuary shore lines and the sections of the lap that can hold a power up
	Estuary[] lapPath = new Estuary[lapDivisions];
	POWER_UP[][] powerUps = new POWER_UP[lapDivisions][3];
	
	public Board(int w, int h, int lapL, int r){
		this.height = h;
		this.width = w;
		this.radius = r;
		this.lapLength = lapL;
		for(int i= 0; i<lapDivisions/3; i++){
			double tempTheta = (2*Math.PI*i) / lapDivisions;
			double tempX =   width/2 + (radius+65) * Math.cos(tempTheta);
			double tempY = height/2 + (radius+65) * Math.sin(tempTheta);
			lapPath[i] = new Estuary(3, (int)tempX, (int)tempY);
		}
		for(int i= lapDivisions/3; i<lapDivisions; i++){
			double tempTheta = (2*Math.PI*i) / lapDivisions;
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

	public int getlapDivisions() {
		return lapDivisions;
	}

	public Estuary[] getLapPath() {
		return lapPath;
	}
	
	
	
}
