package game3.controller;

import java.awt.Toolkit;
import java.awt.Dimension;

import game3.model.Board;
import game3.view.Animation;


public class CubeController{
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	final static int frameWidth=(int) screenSize.getWidth();
	final static int frameHeight=(int) screenSize.getHeight();
	
	public CubeController(){
		Board board=new Board(frameWidth,frameHeight);
		Animation animation=new Animation(board);
	    //animation.paintBoard();
	}
	public static void main(String args[]){
		CubeController cubeControl=new CubeController();
		
		
	}
}
