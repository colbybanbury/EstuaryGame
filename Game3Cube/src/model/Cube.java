package model;

import java.awt.Rectangle;
import java.util.Random;

import model.Board;

public class Cube {
	Rectangle location;
	int picNum;
	int cubeNum;
	int sideLength = 100;
	Random rand = new Random();
	Board board;
	
	/**
	 * Constructor for this class
	 * @param picNum  integer value specifying which picture to display
	 * @param b		  board the cube has to stay within
	 */
	public Cube(int picNum, Board b){
		this.cubeNum = cubeNum;
		this.board = b;
		/*
		location = new Rectangle(rand.nextInt(board.getWidth() - sideLength),
								rand.nextInt(board.getHeight() - sideLength),
								sideLength,sideLength);
		*/
		location = new Rectangle(picNum * board.width / board.NUM_CUBES, board.height / 2, sideLength, sideLength);
	}
	/**
	 * Moves cube to random location within Board, assigns new picNum
	 * (as if rolling a real cube on a board)
	 */
	public void roll(){
		picNum = rand.nextInt(6);
		location.setLocation(rand.nextInt(board.getWidth() - sideLength),
							rand.nextInt(board.getHeight()) - sideLength);
	}
	/**
	 * returns the current side of the cube being shown
	 * @return picNum
	 */
	public int getPicNum(){
		return picNum;
	}
	/**
	 * returns the location occupied by the cube
	 * @return location
	 */
	public Rectangle getLocation(){
		return location;
	}
}
