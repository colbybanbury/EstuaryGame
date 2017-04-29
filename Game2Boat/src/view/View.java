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
import enums.POWER_UP;
import model.Estuary;

public class View extends JPanel{
	private int frameHeight;
	private int frameWidth;
	
	private double boatX;
	private double boatY;
	private double boatTheta;
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
	private BufferedImage oyster;
	private BufferedImage seaGrass;
	
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
		boatTheta = (2*Math.PI*BoatController.boat.getXLoc()) / BoatController.board.getLapLength();
		boatX = BoatController.board.getWidth()/2 + ((BoatController.board.getRadius()*
				BoatController.boat.getRadiusScale()) * Math.cos(boatTheta));
		boatY = BoatController.board.getHeight()/2 + ((BoatController.board.getRadius()*
				BoatController.boat.getRadiusScale()) * Math.sin(boatTheta));
		boatAngle = boatTheta - BoatController.boat.getPhi();
		//^the angle around the circle + phi or the angle that that boat has turned
		
		if(BoatController.boat.getSpeed()> (BoatController.boat.getThreshold() / 3) *2){
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
		for(int i = 0; i< 3; i++){
			int tempRadius  = (int) (BoatController.board.getRadius()* (0.9+0.1*i));
			for(int j = 0; j<BoatController.board.getlapDivisions(); j++){
				double tempTheta = (2*Math.PI*j) / BoatController.board.getlapDivisions();
				int tempX = (int) (frameWidth/2 + tempRadius * Math.cos(tempTheta));
				int tempY = (int) (frameHeight/2 + tempRadius * Math.sin(tempTheta));
				switch(BoatController.board.getPowerUps()[j][i]){
				case OYSTER:
					g.drawImage(oyster, tempX, tempY, this);
					break;
				case SEAGRASS:
					g.drawImage(seaGrass, tempX, tempY, this);
					break;
				default://NONE
					break;
				}
			}
		}
		System.out.println("In View x: " + boatX + ", y: " + boatY);
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
		oyster = createImage("images/clam_back_0.png");
		seaGrass = createImage("images/seagrass.png");
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
