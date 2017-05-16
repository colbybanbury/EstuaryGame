package game3.model;

import java.awt.Rectangle;
import java.util.Random;

import game3.model.Board;

public class Cube {
	private Rectangle location;
	private int picNum;
	private int cubeNum;
	public static int SIDE_LENGTH;
	private Random rand = new Random();
	private Board board;
	
	/**
	 * Constructor for this class
	 * @param picNum  integer value specifying which picture to display
	 * @param b		  board the cube has to stay within
	 */
	public Cube(int cubeNum,int picNum, Board b){
		this.cubeNum=cubeNum;
		this.picNum = picNum;
		this.board = b;
		Cube.SIDE_LENGTH=((board.getWidth()-20)/Board.NUM_CUBES)-10;
		/*
		location = new Rectangle(rand.nextInt(board.getWidth() - sideLength),
								rand.nextInt(board.getHeight() - sideLength),
								sideLength,sideLength);
		*/
		location = new Rectangle(10+ cubeNum * (board.getWidth()-20) / Board.NUM_CUBES, board.getHeight() *5/ 9, SIDE_LENGTH, SIDE_LENGTH);
	}
	/**
	 * Moves cube to random location within Board, assigns new picNum
	 * (as if rolling a real cube on a board)
	 */
	public void roll(int numPics){
		location.setLocation(10+ cubeNum * (board.getWidth()-20) / Board.NUM_CUBES, board.getHeight() *5/9);
		picNum = rand.nextInt(numPics-1)+1;
		
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
	
	/**
	 * changed the location of that cube
	 * @param x
	 * @param y
	 */
	public void changeLocation(int x, int y){
		location.setLocation(x, y);
	}
	public int getCubeNum(){ return cubeNum; }
	
}
