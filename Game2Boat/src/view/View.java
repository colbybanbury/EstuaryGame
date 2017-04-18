package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.BoatController;

public class View extends JPanel{
	private int frameHeight;
	private int frameWidth;
	
	
	private int boatHeight = 34;
	private int boatWidth = 65;
	
	private double boatX;
	private double boatY;
	private double boatAngle;
	
	AffineTransformOp op;
	
	public static JButton move = new JButton("");
	
	private BufferedImage backgroundImage;
	private BufferedImage boatImage;
	
	JFrame frame;
	
	public View(int w, int h){
		frame = new JFrame();
		this.frameHeight = h;
		this.frameWidth = w;
		loadImages();
		//makes a button that covers the whole screen and is invisible
		move.setOpaque(false);
		move.setContentAreaFilled(false);
		move.setBorderPainted(false);
		move.setBounds(0, 0, w, h);
		move.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//when the mouse is clicked it calls buttonPress in the controller
				System.out.println("button pressed");
				BoatController.buttonPress();
			}
		});
		
		frame.add(move);
		frame.getContentPane().add(this);
		frame.setBackground(Color.BLUE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(frameWidth, frameHeight);
		frame.setVisible(true);
		
		//TODO
	}
	
	public void animate(){
		boatX = BoatController.boat.getBoatCircleX();
		boatY = BoatController.boat.getBoatCircleY();
		boatAngle = BoatController.boat.getTheta();
		
		changeBoatAngle();
		
		frame.repaint();
	}
	
	public void paint(Graphics g){
		g.drawImage(op.filter(boatImage, null), (int) boatX, (int)boatY, this);
		System.out.println("In View x: " + BoatController.boat.getBoatCircleX() + ", y: " + BoatController.boat.getBoatCircleY());
		//TODO
	}
	
	public void changeBoatAngle(){//rotates the boat image depending on the part of the circle it's on
		AffineTransform tx = AffineTransform.getRotateInstance(boatAngle, boatHeight/2, boatWidth/2);
		op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
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
