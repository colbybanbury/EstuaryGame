package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * Contains all model components
 * @author Collin Clark
 */
public class Board {
	private int width, height;
	static Random rand=new Random();;
	static final int NUM_CUBES = 6;
	public List<Cube> cubes = new ArrayList<Cube>(NUM_CUBES);
	/**
	 * Constructor for the class
	 * @param  w  width of the board
	 * @param  h  height of the board
	 */
	public Board(int w, int h){
		this.width = w;
		this.height = h;
		for (int i=0;i<NUM_CUBES;i++){
			cubes.add(new Cube(i,0, this));
		}
	}
	/**
	 * returns the height of the board
	 * @return height
	 */
	public int getHeight(){
		return height;
	}
	/**
	 * returns the width of the board
	 * @return width
	 */
	public int getWidth(){
		return width;
	}
	/**
	 * returns the number of cubes in the board
	 * @return NUM_CUBES
	 */
	public int getNumCubes(){
		return NUM_CUBES;
	}
	/**
	 * rolls all dice on the board (gives them a random location and 
	 * side to display)
	 */
	public void shuffle(int numPics){
		for (Cube c : cubes){
			c.roll(numPics);
		}
	}
	/**
	 * returns list of cubes on board
	 * @return cubes
	 * @see Cube
	 */
	public List<Cube> getCubes(){
		return cubes;
	}
}
