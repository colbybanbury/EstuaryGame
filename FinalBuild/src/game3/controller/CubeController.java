package game3.controller;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.imageio.ImageIO;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Dimension;

import game3.model.Board;
import game3.view.Animation;


public class CubeController extends JPanel{
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	final static int frameWidth=(int) screenSize.getWidth();
	final static int frameHeight=(int) screenSize.getHeight();
	
	public static void main(String args[]){
		
		Board board=new Board(frameWidth,frameHeight);
		Animation animation=new Animation(board);
	    animation.paintBoard();
		
	}
}