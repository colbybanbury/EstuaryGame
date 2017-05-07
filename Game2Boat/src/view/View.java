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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.BoatController;
import enums.POWER_UP;
import model.Estuary;

/**
 * 
 * @author colby
 *
 *Creates all the visuals and calculates the positions of things on the screen.
 */

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
	private BufferedImage rock;
	
	JFrame frame;
	JPanel panel;
	
	public View(int w, int h){
		/**
		 * sets up the frame, panel, and keylisteners. Calls load images to initialize the buffered images
		 * 
		 * @param w 	the width of the screen
		 * @param h 	the height of the screen.
		 */
		
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
	}
	
	public void animate(){
		/**
		 * Is called every tick. Calculates the boats x and y on the screen along with the the angle of the boat.
		 * It determines what level of wake it should display.
		 * It calls change boat angle as well and repaint as well.
		 */
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
		/**
		 * draws everything on the screen as well as calculates the x and y locations of the estuaries.
		 * different types of estuaries have different levels of damage draws as well as different kinds of protection.
		 * Powerups x and y locations are also calculated
		 */
		g.drawImage(backgroundImage, 0, 0, this);
		g.drawImage(op.filter(boatImage, null), (int) boatX, (int)boatY, this);
		for(int j = 0; j < BoatController.board.getLapDivisions(); j++){
			Estuary e = BoatController.board.getLapPath()[j];
			double tempTheta = (2*Math.PI*j) / BoatController.board.getLapDivisions();
			int tempX =   (int) (BoatController.board.getWidth()/2 + (BoatController.board.getRadius()+100) * Math.cos(tempTheta));
			int tempY = (int) (BoatController.board.getHeight()/2 + (BoatController.board.getRadius()+100) * Math.sin(tempTheta));
			if(e.getType()!=3){
				//g.drawImage(estuary, tempX, tempY, this);
				switch(e.getType()){//draws the protection type on top of the estuary centered
				case 0:
					if(e.getDamage()>6)//most damage
						g.drawImage(damage3, tempX, tempY, this);
					else if(e.getDamage()>3)//middle
						g.drawImage(damage2, tempX, tempY, this);
					else if(e.getDamage() >0){//least
						g.drawImage(damage1, tempX, tempY, this);
					}
					break;
				case 1://sea wall TODO the wall should visably deteriorate based on integrity
					g.drawImage(seaWall, tempX + (estuary.getWidth()/2 - seaWall.getWidth()/2), tempY + (estuary.getHeight()/2 - seaWall.getHeight()/3), this);
					break;
				case 2://Gabion TODO the wall should visably deteriorate based on integrity
					g.drawImage(gabion, tempX + (estuary.getWidth()/2 - seaWall.getWidth()/2), tempY + (estuary.getHeight()/2 - seaWall.getHeight()/3), this);
					break;
				}
			}
		}
		for(int i = 0; i< 3; i++){
			int tempRadius  = (int) (BoatController.board.getRadius()* (0.9+0.1*i));
			for(int j = 0; j<BoatController.board.getLapDivisions(); j++){
				double tempTheta = (2*Math.PI*j) / BoatController.board.getLapDivisions();
				int tempX = (int) (frameWidth/2 + tempRadius * Math.cos(tempTheta));
				int tempY = (int) (frameHeight/2 + tempRadius * Math.sin(tempTheta));
				switch(BoatController.board.getPowerUps()[j][i]){
				case OYSTER:
					g.drawImage(oyster, tempX, tempY, this);
					break;
				case SEAGRASS:
					g.drawImage(seaGrass, tempX, tempY, this);
					break;
				case ROCK:
					g.drawImage(rock, tempX, tempY, this);
					break;
				default://NONE
					break;
				}
			}
		}
		System.out.println("In View x: " + boatX + ", y: " + boatY);
		
		
		g.setColor(new Color(0, 0, 0, 255));
		g.setFont(g.getFont().deriveFont(g.getFont().getStyle(),48));
		
		
		g.drawString("Score: " + BoatController.game.getScore().toString(), 20, 40);
		g.drawString("Time: " + BoatController.game.getTime().toString(), 20, 80);
		//TODO improve background to actually have land around the estuaries
		//TODO have an indication of where the lap ends (where the boat starts at 0 degrees on the circle)
		
		if(BoatController.end){
			g.setColor(new Color(255, 255, 255, 255));
			g.fill3DRect(20, 70, frameWidth - 39, frameHeight - 160, false);
			g.setColor(new Color(0, 0, 0, 255));
			g.setFont(g.getFont().deriveFont(g.getFont().getStyle(),64));
			g.drawString("Time limit reached", frameWidth/2 - 80, frameHeight/3);
			g.drawString("Score: " + BoatController.game.getScore().toString(), frameWidth/2 - 80, frameHeight/2);
			g.drawString("Laps Completed: " + BoatController.game.getLap().toString(), frameWidth/2 - 80, frameHeight*2/3);
		}
	}
	
	
	private void changeBoatAngle(){//rotates the boat image depending on the part of the circle it's on
		/**
		 * uses affineTranformation library to change the angle of the boat
		 */
		tx = AffineTransform.getRotateInstance(boatAngle, boatImage.getWidth()/2, boatImage.getHeight()/2);
		op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
	}
	
	private void loadImages(){
		/**
		 * loads the buffered images in from the images folder
		 */
		boatWake0 = createImage("images/boat.jpg");
		boatWake1 = createImage("images/boatWake1.gif");
		boatWake2 = createImage("images/boatWake2.gif");
		backgroundImage = createImage("images/tempBackGroundWithLand.jpg");
		estuary = createImage("images/grass_tile.jpg");
		seaWall = createImage("images/box2.png");
		gabion = createImage("images/bucket.png");
		damage1 = createImage("images/puddle small.png");
		damage2 = createImage("images/puddle medium.png");
		damage3 = createImage("images/puddle large.png");
		oyster = createImage("images/clam_back_0.png");
		seaGrass = createImage("images/seagrass.png");
		//TODO add the different levels of Gabion and seaWall damage
		rock = createImage("images/seed.png");//TODO make this actually be a better size/shape Will probably have to adjust the x y in paint for these
	}
	
	private BufferedImage createImage(String file){
		/**
		 * is used to read in each image
		 */
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
