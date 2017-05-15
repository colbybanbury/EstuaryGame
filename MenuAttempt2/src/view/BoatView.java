package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
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
 *
 */

public class BoatView extends JPanel{
	private int frameHeight;
	private int frameWidth;
	
	private double boatX;
	private double boatY;
	private double boatTheta;
	private double boatAngle;
	
	AffineTransformOp op;
	AffineTransform tx;
	
	
	private BufferedImage backgroundImage;
	private Image scaledBackground;
	private BufferedImage boatImage;
	private BufferedImage boatWake0;
	private BufferedImage boatWake1;
	private BufferedImage boatWake2;
	private BufferedImage estuary;
	
	private BufferedImage[][] protections = new BufferedImage[3][4];
	
	private BufferedImage oyster;
	private BufferedImage seaGrass;
	private BufferedImage buoy;
	private BufferedImage wakeImage = null;
	private BufferedImage noWake;
	
	JFrame frame;
	JPanel panel;
	
	public BoatView(int w, int h){
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
				if(BoatController.end){
					if(e.getKeyCode() == KeyEvent.VK_SPACE){
						BoatController.stopTimers();
						frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
						frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			    	}
				}
				else
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
		boatTheta = (2*Math.PI*BoatController.boat.getXLoc()) / BoatController.Boatboard.getLapLength();
		boatX = BoatController.Boatboard.getWidth()/2 + ((BoatController.Boatboard.getRadius()*
				BoatController.boat.getRadiusScale()) * Math.cos(boatTheta));
		boatY = BoatController.Boatboard.getHeight()/2 + ((BoatController.Boatboard.getRadius()*
				BoatController.boat.getRadiusScale()) * Math.sin(boatTheta));
		boatAngle = boatTheta - BoatController.boat.getPhi();
		//^the angle around the circle + phi or the angle that that boat has turned
		if(BoatController.boat.getSpeed()> (BoatController.boat.getThreshold() / 3) *2){
			if(BoatController.boat.getSpeed()> BoatController.boat.getThreshold()){
				boatImage = boatWake2;
				if(BoatController.curEstuary.getType() !=3 )//display a no wake sign if the player is damaging the estuary
					wakeImage = noWake;
			}	//boat speed is over the threshold so max wake displayed
			else{boatImage = boatWake1;}//close to the threshold so middle wake
		}
		else{
			boatImage = boatWake0;
			wakeImage = null;}	//well under threshold so no wake
		
		changeBoatAngle();
		
		frame.repaint();
	}
	
	public void paint(Graphics g){
		/**
		 * draws everything on the screen as well as calculates the x and y locations of the estuaries.
		 * different types of estuaries have different levels of damage draws as well as different kinds of protection.
		 * Powerups x and y locations are also calculated
		 */
		g.drawImage(scaledBackground, 0, 0, this);
		g.drawImage(op.filter(boatImage, null), (int) boatX, (int)boatY, this);
		g.drawImage(wakeImage, frameWidth / 2 - noWake.getWidth()/6, frameHeight / 2 - noWake.getHeight()/3, this );
		for(int j = 0; j < BoatController.Boatboard.getLapDivisions(); j++){
			Estuary e = BoatController.Boatboard.getLapPath()[j];
			double tempTheta = (2*Math.PI*j) / BoatController.Boatboard.getLapDivisions();
			int tempX =   (int) (BoatController.Boatboard.getWidth()/2 + (BoatController.Boatboard.getRadius()+100) * Math.cos(tempTheta));
			int tempY = (int) (BoatController.Boatboard.getHeight()/2 + (BoatController.Boatboard.getRadius()+100) * Math.sin(tempTheta));
			if(e.getType()!=3){
				//g.drawImage(estuary, tempX, tempY, this);
				switch(e.getType()){//draws the protection type on top of the estuary centered
				case 0:
					if(e.getDamage()>0)
						g.drawImage(protections[0][e.getDamage()/3], tempX, tempY, this);
					break;
				case 1://sea wall
					g.drawImage(protections[1][e.getIntegrity()*3/2], tempX + (estuary.getWidth()/2 - protections[1][0].getWidth()/2), tempY + (estuary.getHeight()/2 - protections[1][0].getHeight()/3), this);
					break;
				case 2://Gabion 
					g.drawImage(protections[2][e.getIntegrity()/2], tempX + (estuary.getWidth()/2 - protections[1][0].getWidth()/2), tempY + (estuary.getHeight()/2 - protections[1][0].getHeight()/3), this);
					break;
				}
			}
			g.drawImage(buoy, (int) (BoatController.Boatboard.getWidth()/2 + (BoatController.Boatboard.getRadius()*.7) * Math.cos(tempTheta)), (int) (BoatController.Boatboard.getHeight()/2 + (BoatController.Boatboard.getRadius()*.7) * Math.sin(tempTheta)), this);
		}
		for(int i = 0; i< 3; i++){
			int tempRadius  = (int) (BoatController.Boatboard.getRadius()* (0.9+0.15*i));
			for(int j = 0; j<BoatController.Boatboard.getLapDivisions(); j++){
				double tempTheta = (2*Math.PI*j) / BoatController.Boatboard.getLapDivisions();
				int tempX = (int) (frameWidth/2 + tempRadius * Math.cos(tempTheta)) + 15;
				int tempY = (int) (frameHeight/2 + tempRadius * Math.sin(tempTheta)) + 15;
				switch(BoatController.Boatboard.getPowerUps()[j][i]){
				case OYSTER:
					g.drawImage(oyster, tempX, tempY, this);
					break;
				case SEAGRASS:
					g.drawImage(seaGrass, tempX, tempY, this);
					break;
				case ROCK:
					g.drawImage(buoy, tempX, tempY, this);
					break;
				default://NONE
					break;
				}
			}
		}
		System.out.println("In BoatView x: " + boatX + ", y: " + boatY);
		
		
		g.setColor(new Color(0, 0, 0, 255));
		g.setFont(g.getFont().deriveFont(g.getFont().getStyle(),48));
		
		
		g.drawString("Score: " + BoatController.Boatgame.getScore().toString(), 20, 40);
		g.drawString("Time: " + BoatController.Boatgame.getTime().toString(), 20, 80);
		//TODO improve background to actually have land around the estuaries
		
		int x1 = (BoatController.Boatboard.getWidth()/2)+BoatController.Boatboard.getRadius()-75;
		int y = BoatController.Boatboard.getHeight()/2;
		int x2 = (BoatController.Boatboard.getWidth()/2)+BoatController.Boatboard.getRadius()+100;
		
		for(int i = -5; i < 5; i++){//drawing the finish line
			if(i%2==0)
				g.setColor(Color.GREEN);
			else
				g.setColor(Color.WHITE);
			g.drawLine(x1, y+i, x2, y+i);
		}
		
		//Power Ups Key
		g.setColor(new Color(255, 255, 255, 100));
		g.fill3DRect(frameWidth / 45, frameHeight / 6, frameWidth / 5, frameHeight/3 + 50, true);
		g.setColor(new Color(0, 0, 0, 255));
		g.setFont(g.getFont().deriveFont(g.getFont().getStyle(),40));
		g.drawString("POWER UPS:", frameWidth/42, frameHeight*4/16);
		g.setFont(g.getFont().deriveFont(g.getFont().getStyle(),24));
		g.drawString("Pick up Oysters to", frameWidth/42, frameHeight*6/16);
		g.drawString("Build Gabions", frameWidth/42, frameHeight*13/32);
		g.drawImage(oyster, frameWidth/38, frameHeight*5/16, this);
		g.drawString("Pick up Sea Grass to", frameWidth/42, frameHeight*8/16);
		g.drawString("Undo Estuary Damage", frameWidth/42, frameHeight*17/32);
		g.drawImage(seaGrass, frameWidth/38, frameHeight*7/16, this);
		
		int shift = frameHeight*4/10; //height difference of the two games
		
		//Game Control key
		g.setColor(new Color(255, 255, 255, 100));
		g.fill3DRect(frameWidth / 45, frameHeight*5/24 + shift, frameWidth / 5, frameHeight*2/9, true);
		g.setColor(new Color(0, 0, 0, 255));
		g.setFont(g.getFont().deriveFont(g.getFont().getStyle(),40));
		g.drawString("Controls:", frameWidth/42, frameHeight*4/16 + shift);
		g.setFont(g.getFont().deriveFont(g.getFont().getStyle(),24));
		g.drawString("Press SPACE to Throttle", frameWidth/42, frameHeight*5/16 + shift);
		g.drawString("Press The LEFT", frameWidth/42, frameHeight*6/16 + shift);
		g.drawString("and RIGTH Keys to Turn", frameWidth/42, frameHeight*13/32 + shift);
	
		
		if(BoatController.end){
			g.setColor(new Color(255, 255, 255, 240));
			g.fill3DRect(20, 70, frameWidth - 39, frameHeight - 100, true);
			g.setColor(new Color(0, 0, 0, 255));
			g.setFont(g.getFont().deriveFont(g.getFont().getStyle(),52));
			g.drawString("Time limit reached", frameWidth/4, frameHeight*2/12);
			g.drawString("Laps Completed: " + BoatController.Boatgame.getLap().toString(), frameWidth/4, frameHeight*4/12);
			g.drawString("Score Before Penalty: " + BoatController.Boatgame.getScore().toString(), frameWidth/4, frameHeight*5/12);
			g.drawString("Score Penalty to Estuary Damage: "+ BoatController.Boatgame.getDamagePenalty().toString(), frameWidth/4, frameHeight*6/12);
			g.drawString("Final Score: " + (BoatController.Boatgame.getScore() - BoatController.Boatgame.getDamagePenalty()), frameWidth/4, frameHeight*7/12);
			g.drawString("Press SPACE to Return to the Menu", frameWidth/4, frameHeight*11/12);
		}

		g.setColor(new Color(0,0,0,255));
	}
	
	
	private void changeBoatAngle(){//rotates the boat image depending on the part of the circle it's on
		/**
		 * uses affineTranformation library to change the angle of the boat
		 * 
		 */
		//Tried Graphics2D.rotate but it made the boat disappear...
		tx = AffineTransform.getRotateInstance(boatAngle, boatImage.getWidth()/2, boatImage.getHeight()/2);
		op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
	}
	
	private void loadImages(){
		/**
		 * loads the buffered images in from the images folder
		 */
		boatWake0 = createImage("BoatImages/boat.jpg");
		boatWake1 = createImage("BoatImages/boatWake1.gif");//TODO have a better indication of wake
		boatWake2 = createImage("BoatImages/boatWake2.gif");
		backgroundImage = createImage("BoatImages/water.png");
		scaledBackground = backgroundImage.getScaledInstance(frameWidth, frameHeight, backgroundImage.SCALE_DEFAULT);
		estuary = createImage("BoatImages/grass_tile.jpg");
		
		protections[1][3] = createImage("BoatImages/seaWall0.png");
		protections[1][2] = createImage("BoatImages/seaWall1.png");
		protections[1][1] = createImage("BoatImages/seaWall2.png");
		protections[1][0] = createImage("BoatImages/seaWall3.png");
		
		protections[2][3] = createImage("BoatImages/gabion0.png");
		protections[2][2] = createImage("BoatImages/gabion1.png");
		protections[2][1] = createImage("BoatImages/gabion2.png");
		protections[2][0] = createImage("BoatImages/gabion3.png");
		
		protections[0][0] = createImage("BoatImages/puddle small.png");
		protections[0][1] = createImage("BoatImages/puddle medium.png");
		protections[0][2] = createImage("BoatImages/puddle large.png");
		
		oyster = createImage("BoatImages/clam_back_0.png");
		seaGrass = createImage("BoatImages/seagrass.png");
		buoy = createImage("BoatImages/bouy.png");
		noWake = createImage("BoatImages/noWake.png");
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
