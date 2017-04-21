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

public class View extends JPanel{
	private int frameHeight;
	private int frameWidth;
	
	public static JButton jump = new JButton("");
	
	private BufferedImage backgroundImage;
	private BufferedImage crabImage;
	private BufferedImage enemyImage;
	private BufferedImage friendImage;
	
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
		frame.getContentPane().add(this);
		frame.setBackground(Color.BLUE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(frameWidth, frameHeight);
		frame.setVisible(true);
	}
	
	public void animate(){		
		frame.repaint();
	}
	
	public void paint(Graphics g){
		g.drawImage(backgroundImage, 0, 0, this);
		g.drawImage(crabImage, (int) CrabController.player.location.getX(), (int) CrabController.player.location.getY(), this);
		
		if (!CrabController.enemies.isEmpty()){
			for (model.Enemy e: CrabController.enemies){
				g.drawImage(enemyImage, (int) e.location.getX(), (int) e.location.getY(), this);
			}
		}
		if (!CrabController.friends.isEmpty()){
			for (model.Friend f: CrabController.friends){
				g.drawImage(friendImage, (int) f.location.getX(), (int) f.location.getY(), this);
			}
		}
	}
	
	private void loadImages(){
		crabImage = createImage("images/bluecrab_0.png");
		backgroundImage = createImage("images/tempBackGround.jpg");
		enemyImage = createImage("images/fish_bass_left.png");
		friendImage = createImage("images/bogturtle_left_1.png");
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
