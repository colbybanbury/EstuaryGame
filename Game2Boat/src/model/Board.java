package model;

import java.util.ArrayList;

import controller.BoatController;
import enums.POWER_UP;

//Board class stores the size of the screen, size of the racing circle, an array of all of the sections of the estuary,
//and an object array with all the objects on the board.
/**
 * @author colby
 * This class contains the dimensions of the screen and lap along with two collections containing the estuaries and the powerups.
 * The constructor populates the collections with their default values. Other than the constructor the class only has getters and setters
 * @param w 	Width of the screen.
 * @param h 	Height of the screen.
 * @param lapL 	the length of a lap that corresponds to the xLoc of the boat.
 * @param r 	the radius of the circular lap.
 */
public class Board {
	private int height;
	private int width;
	private int radius;
	private int lapLength;	//
	final int lapDivisions = 40; //the number of estuary shore lines and the sections of the lap that can hold a power up
	Estuary[] lapPath = new Estuary[lapDivisions];
	POWER_UP[][] powerUps = new POWER_UP[lapDivisions][3];
	
	public Board(int w, int h, int lapL, int r){
		this.height = h;
		this.width = w;
		this.radius = r;
		this.lapLength = lapL;
		for(int i= 0; i<lapDivisions/3; i++){	//initialize the first 1/3 to be open water
			lapPath[i] = new Estuary(3);
		}
		for(int i= lapDivisions/3; i<lapDivisions; i++){	//add estuaries to the other 2/3s and place protection on some
			// i/3%3 makes protection groupings of 3
			lapPath[i] = new Estuary(i/3%3);
		}
		
		for(int i=0; i<lapDivisions; i++){	//initialize powerUps[][] to have no POWER_UPs
			powerUps[i][0] = POWER_UP.NONE;
			powerUps[i][1] = POWER_UP.NONE;
			powerUps[i][2] = POWER_UP.NONE;
			
			powerUps[lapDivisions*3/4][1] = POWER_UP.ROCK;
			powerUps[lapDivisions*3/4][2] = POWER_UP.ROCK;//placing a dock in the way
			
			powerUps[lapDivisions*28/32][0] = POWER_UP.ROCK;
			powerUps[lapDivisions*28/32][1] = POWER_UP.ROCK;//placing a dock in the way
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

	public int getLapDivisions() {
		return lapDivisions;
	}

	public Estuary[] getLapPath() {
		return lapPath;
	}

	public POWER_UP[][] getPowerUps() {
		return powerUps;
	}
	
	
	
}
