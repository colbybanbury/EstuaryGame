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
import model.Estuary;

public class View extends JPanel{
	private int frameHeight;
	private int frameWidth;
	
	private double boatX;
	private double boatY;
	private double boatAngle;
	
	AffineTransformOp op;
	
	public static JButton move = new JButton("");
	
	private BufferedImage backgroundImage;
	private BufferedImage boatImage;
	private BufferedImage estuary;
	private BufferedImage seaWall;
	private BufferedImage gabion;
	private BufferedImage damage1;
	private BufferedImage damage2;
	private BufferedImage damage3;
	
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
		AffineTransform tx = AffineTransform.getRotateInstance(boatAngle, boatImage.getHeight()/2, boatImage.getWidth()/2);
		op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
	}
	
	private void loadImages(){
		boatImage = createImage("images/boat.jpg");
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
