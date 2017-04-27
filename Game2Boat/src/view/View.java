package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import model.Estuary;

public class View extends JPanel{
	private int frameHeight;
	private int frameWidth;
	
	private double boatX;
	private double boatY;
	private double boatAngle;
	
	AffineTransformOp op;
	AffineTransform tx;
	
	
	private BufferedImage backgroundImage;
	private BufferedImage boatImage;
	private BufferedImage boatWake0;
	private BufferedImage boatWake1;
	private BufferedImage boatWake2;
	private BufferedImage estuary;
	private BufferedImage seaWall;
	private BufferedImage gabion;
	private BufferedImage damage1;
	private BufferedImage damage2;
	private BufferedImage damage3;
	
	JFrame frame;
	JPanel panel;
	
	public View(int w, int h){
		frame = new JFrame();
		panel = new JPanel();
		
		frame.getContentPane().add(panel);
		
		panel.addKeyListener(new KeyListener(){
			//action listener that calls the handling function in the controller
			@Override
			public void keyPressed(KeyEvent e){
				BoatController.keyPressed(e);
			}

			@Override
			public void keyReleased(KeyEvent arg0) {}

			@Override
			public void keyTyped(KeyEvent e) {}
		});
		
		panel.setFocusable(true);
        panel.requestFocusInWindow();
		
		this.frameHeight = h;
		this.frameWidth = w;
		loadImages();
		
		
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
		boatAngle = BoatController.boat.getTheta() - BoatController.boat.getPhi();
		//^the angle around the circle + phi or the angle that that boat has turned
		
		if(BoatController.boat.getSpeed()> (BoatController.boat.getThreshold() / 6) *5){
			if(BoatController.boat.getSpeed()> BoatController.boat.getThreshold()){
				boatImage = boatWake2;}	//boat speed is over the threshold so max wake displayed
			else{boatImage = boatWake1;}//close to the threshold so middle wake
		}
		else{boatImage = boatWake0;}	//well under threshold so no wake
		
		changeBoatAngle();
		
		frame.repaint();
	}
	
	public void paint(Graphics g){
		g.drawImage(backgroundImage, 0, 0, this);
		g.drawImage(op.filter(boatImage, null), (int) boatX, (int)boatY, this);
		for(Estuary e : BoatController.board.getLapPath()){
			if(e.getType()!=3){
				g.drawImage(estuary, e.getCircleX(), e.getCircleY(), this);
				switch(e.getType()){//draws the protection type on top of the estuary centered
				case 0://TODO no protection add change based on damage
					if(e.getDamage()>6)//most damage
						g.drawImage(damage3, e.getCircleX(), e.getCircleY(), this);
					else if(e.getDamage()>3)//middle
						g.drawImage(damage2, e.getCircleX(), e.getCircleY(), this);
					else if(e.getDamage() >0){//least
						g.drawImage(damage1, e.getCircleX(), e.getCircleY(), this);
					}
					break;
				case 1://sea wall
					g.drawImage(seaWall, e.getCircleX() + (estuary.getWidth()/2 - seaWall.getWidth()/2), e.getCircleY() + (estuary.getHeight()/2 - seaWall.getHeight()/3), this);
					break;
				case 2://Gabion
					g.drawImage(gabion, e.getCircleX() + (estuary.getWidth()/2 - seaWall.getWidth()/2), e.getCircleY() + (estuary.getHeight()/2 - seaWall.getHeight()/3), this);
					break;
				}
			}
		}
		System.out.println("In View x: " + BoatController.boat.getBoatCircleX() + ", y: " + BoatController.boat.getBoatCircleY());
		//TODO
	}
	
	
	private void changeBoatAngle(){//rotates the boat image depending on the part of the circle it's on
		tx = AffineTransform.getRotateInstance(boatAngle, boatImage.getWidth()/2, boatImage.getHeight()/2);
		op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
	}
	
	private void loadImages(){
		boatWake0 = createImage("images/boat.png");
		boatWake1 = createImage("images/boatWake1.gif");//TODO add images that have different levels of Wake
		boatWake2 = createImage("images/boatWake2.gif");
		backgroundImage = createImage("images/tempBackGround.jpg");
		estuary = createImage("images/grass_tile.jpg");
		seaWall = createImage("images/box.png");
		gabion = createImage("images/bucket.png");
		damage1 = createImage("images/puddle small.png");
		damage2 = createImage("images/puddle medium.png");
		damage3 = createImage("images/puddle large.png");
		//TODO
	}
	
	private BufferedImage createImage(String file){
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
