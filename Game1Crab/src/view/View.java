package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.CrabController;

public class View {
	private int frameHeight;
	private int frameWidth;	
	
	private double crabX;
	private double crabY;
	
	private int crabHeight = 119;
	private int crabWidth = 243;
	
	public static JButton jump = new JButton("");
	
	private BufferedImage backgroundImage;
	private BufferedImage crabImage;
	
	JFrame frame;
	
	public View(int w, int h){
		frame = new JFrame();
		this.frameHeight = h;
		this.frameWidth = w;
		loadImages();
		//makes a button that covers the whole screen and is invisible
		jump.setOpaque(false);
		jump.setContentAreaFilled(false);
		jump.setBorderPainted(false);
		jump.setBounds(0, 0, w, h);
		jump.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//when the mouse is clicked it calls buttonPress in the controller
				System.out.println("button pressed");
				CrabController.buttonPress();
			}
		});	
		
		frame.add(jump);
		frame.getContentPane().add(frame, this);
		frame.setBackground(Color.BLUE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(frameWidth, frameHeight);
		frame.setVisible(true);
	}
	
	public void animate(){
		crabX = CrabController.player.location.getX();
		crabY = CrabController.player.location.getY();
		
		frame.repaint();
	}
	
	public void paint(Graphics g){
		g.drawImage(backgroundImage, 0, 0, (ImageObserver) this);
		g.drawImage(crabImage, (int) crabX, (int) crabY, (ImageObserver) this);
		//TODO
	}
	
	private void loadImages(){
		crabImage = createImage("images/crab.jpg");
		backgroundImage = createImage("images/tempBackGround.jpg");
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
