package game3.controller;

import java.awt.Toolkit;
import java.awt.Dimension;

import game3.model.Board;
import game3.view.Animation;


public class CubeController{
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	final static int frameWidth=(int) screenSize.getWidth();
	final static int frameHeight=(int) screenSize.getHeight();
	Board board;
	Animation animation;
	
	public CubeController(){

		this.board = new Board(frameWidth,frameHeight);
		this.animation=new Animation(board);
	}
	
	public static void main(String args[]){
		CubeController cubeController = new CubeController();
	}
}
