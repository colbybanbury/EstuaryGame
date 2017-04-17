package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import controller.BoatController;

public class View extends JPanel{
	private int height;
	private int width;
	
	private int boatHeight = 666;
	private int boatWidth = 1300;
	
	public static JButton move = new JButton("move");
	
	private BufferedImage backgroundImage;
	private BufferedImage boatImage;
	
	
	public View(int h, int w){
		this.height = h;
		this.width = w;
		//makes a button that covers the whole screen and is invisible
		move.setOpaque(false);
		move.setContentAreaFilled(false);
		move.setBorderPainted(false);
		move.setBounds(0, 0, w, h);
		move.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//when the mouse is clicked it calls buttonPress in the controller
				BoatController.buttonPress();
			}
		});
		
		//TODO
	}
	
	public void paint(Graphics g){
		g.drawImage(boatImage, BoatController.boat.getBoatCircleX(), BoatController.boat.getBoatCircleY(), this);
		//TODO
	}
	
	public void loadImages(){
		boatImage = createImage("images/boat.jpg");
		//TODO
	}
	
	private BufferedImage createImage(String file) {
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File(file));
			return bufferedImage;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
