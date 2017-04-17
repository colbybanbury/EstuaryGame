package model;

import java.awt.Rectangle;
import java.util.Random;

import model.Board;

public class Cube {
	Rectangle location;
	int picNum;
	int cubeNum;
	int sideLength = 300;
	Random rand = new Random();
	Board board;
	
	public Cube(int picNum, Board b){
		this.cubeNum = cubeNum;
		this.board = b;
		location = new Rectangle(rand.nextInt(board.getWidth() - sideLength),
								rand.nextInt(board.getHeight() - sideLength),
										sideLength,sideLength);
	}
	public void roll(){
		picNum = rand.nextInt(6);
		location.setLocation(rand.nextInt(board.getWidth()), rand.nextInt(board.getHeight()));
	}
	public int getPicNum(){
		return picNum;
	}
}
