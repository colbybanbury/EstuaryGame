package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
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

public class CrabView extends JPanel{
	private int frameHeight;
	private int frameWidth;
	
	public static JButton jump = new JButton("");
	
	private BufferedImage backgroundImage;
	private BufferedImage crabImage;
	private BufferedImage enemyImage;
	private BufferedImage friendImage;
	
	JFrame frame;
	
	public CrabView(int w, int h){
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
		
		g.setColor(new Color(103, 229, 255, 255));
		g.fill3DRect(0, 0, CrabController.crabBoard.getWidth(), CrabController.crabBoard.getHeight(), false);
		
		g.setColor(new Color(225, 200, 100, 255));
		g.fill3DRect(0, CrabController.crabBoard.getHeight() - 110, CrabController.crabBoard.getWidth(), CrabController.crabBoard.getHeight(), false);
		
		g.drawImage(crabImage, (int) CrabController.crabBoard.crabPlayer.getLocation().getX(), (int) CrabController.crabBoard.crabPlayer.getLocation().getY(), this);
		
		if (!CrabController.crabBoard.crabEnemies.isEmpty()){
			for (model.CrabEnemy e: CrabController.crabBoard.crabEnemies){
				g.drawImage(enemyImage, (int) e.getLocation().getX(), (int) e.getLocation().getY(), this);
			}
		}
		
		g.setColor(new Color(0, 0, 0, 255));
		g.setFont(g.getFont().deriveFont(g.getFont().getStyle(),30));
		
		if (!CrabController.crabBoard.crabFriends.isEmpty()){
			for (model.CrabFriend f: CrabController.crabBoard.crabFriends){
				g.drawImage(friendImage, (int) f.getLocation().getX(), (int) f.getLocation().getY(), this);
			
				f.setTextSize(g.getFontMetrics().stringWidth(CrabController.crabBoard.facts[f.getFriendCounter()]));
				
				g.drawString(CrabController.crabBoard.facts[f.getFriendCounter()], (int) (f.getLocation().getX() + f.getLocation().getWidth()), (int) f.getLocation().getY());
			}
		}
		
		g.setColor(new Color(239, 211, 70, 127));
		
		for (Rectangle r: CrabController.crabBoard.scentTrail){
			g.fill3DRect((int) r.getX(), (int) r.getY(), (int) r.getWidth()-1, (int) r.getHeight()-1, false);
		}
		
		g.setColor(new Color(0, 0, 0, 255));
		g.draw3DRect(20, 40, CrabController.crabBoard.getWidth() - 40, 20, false);
		
		g.setColor(new Color(255, 0, 0, 255));
		
		g.fill3DRect(21, 41, (int) CrabController.crabBoard.getProgress(), 19, false);
		
		if(!CrabController.crabBoard.crabPlayer.getStarted() && CrabController.getCanBeAskedAQuestion()){			
			g.setColor(new Color(255, 255, 255, 255));
			g.fill3DRect(20, 70, CrabController.crabBoard.getWidth() - 39, CrabController.crabBoard.getHeight() - 160, false);
		
			g.setColor(new Color(0, 0, 0, 255));
			g.setFont(g.getFont().deriveFont(g.getFont().getStyle(),48));
			
			int newX = ((CrabController.crabBoard.getWidth() - 39) - (g.getFontMetrics().stringWidth(CrabController.crabBoard.questions[CrabController.crabBoard.getCurrQuestion()]))) / 2;
			
			g.drawString(CrabController.crabBoard.questions[CrabController.crabBoard.getCurrQuestion()], 20 + newX, 115);
		
		
		}
		
	}
	
	private void loadImages(){
		crabImage = createImage("CrabImages/bluecrab_0.png");
		backgroundImage = createImage("CrabImages/tempBackGround.jpg");
		enemyImage = createImage("CrabImages/fish_bass_left.png");
		friendImage = createImage("CrabImages/bogturtle_left_1.png");
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