package game3.controller;

import javax.swing.JPanel;

import java.awt.Toolkit;
import java.awt.Dimension;

import game3.model.Board;
import game3.view.Animation;


public class CubeController extends JPanel{
	private static final long serialVersionUID = -7630649033977913941L;
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	final static int frameWidth=(int) screenSize.getWidth();
	final static int frameHeight=(int) screenSize.getHeight();
	
	public static void main(String args[]){
		
		Board board=new Board(frameWidth,frameHeight);
		Animation animation=new Animation(board);
	    animation.paintBoard();
		
	}
}
