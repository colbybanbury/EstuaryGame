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

public class View extends JPanel{
	private int frameHeight;
	private int frameWidth;
	
	public static JButton jump = new JButton("");
	public static JButton answer1 = new JButton("");
	public static JButton answer2 = new JButton("");
	public static JButton answer3 = new JButton("");
	
	private BufferedImage backgroundImage;
	private BufferedImage[] crabImage;
	private BufferedImage[] enemyImage;
	private BufferedImage[] friendImage;
	
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
				CrabController.buttonPress();
			}
		});
		
		answer1.setContentAreaFilled(true);
		answer1.setBorderPainted(true);
		answer1.setEnabled(true);
		answer1.setText("ANSWER a");
		answer1.setBackground(Color.RED);
		answer1.setBounds(178, CrabController.board.getHeight() - 240 , 320, 80);
		answer1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				CrabController.answerButton1Press();
			}
		});
		
		answer2.setContentAreaFilled(true);
		answer2.setBorderPainted(true);
		answer2.setEnabled(true);
		answer2.setText("ANSWER b");
		answer2.setBackground(Color.BLUE);
		answer2.setBounds(518, CrabController.board.getHeight() - 240 , 320, 80);
		answer2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				CrabController.answerButton2Press();
			}
		});
		
		answer3.setContentAreaFilled(true);
		answer3.setBorderPainted(true);
		answer3.setEnabled(true);
		answer3.setText("ANSWER c");
		answer3.setBackground(Color.GREEN);
		answer3.setBounds(858, CrabController.board.getHeight() - 240 , 320, 80);
		answer3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				CrabController.answerButton3Press();
			}
		});
		
		frame.add(jump);
		//frame.add(answer1);
		//frame.add(answer2);
		//frame.add(answer3);
		frame.getContentPane().add(this);
		frame.setBackground(Color.BLUE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(frameWidth, frameHeight);
		frame.setVisible(true);
	}
	
	public JFrame getFrame(){
		return this.frame;
	}
	
	public void animate(){		
		frame.repaint();
	}
	
	public void paint(Graphics g){
		
		g.setColor(new Color(103, 229, 255, 255));
		g.fill3DRect(0, 0, CrabController.board.getWidth(), CrabController.board.getHeight(), false);
		
		g.setColor(new Color(225, 200, 100, 255));
		g.fill3DRect(0, CrabController.board.getHeight() - 110, CrabController.board.getWidth(), CrabController.board.getHeight(), false);
		
		g.drawImage(crabImage[CrabController.board.player.getPicNum()], (int) CrabController.board.player.getLocation().getX(), (int) CrabController.board.player.getLocation().getY(), this);
		
		if (!CrabController.board.enemies.isEmpty()){
			for (model.Enemy e: CrabController.board.enemies){
				g.drawImage(enemyImage[e.getPicNum()], (int) e.getLocation().getX(), (int) e.getLocation().getY(), this);
			}
		}
		
		g.setColor(new Color(0, 0, 0, 255));
		g.setFont(g.getFont().deriveFont(g.getFont().getStyle(),30));
		
		if (!CrabController.board.friends.isEmpty() && CrabController.getIsFriendTimerRunning()){
			for (model.Friend f: CrabController.board.friends){
				g.drawImage(friendImage[f.getPicNum()], (int) f.getLocation().getX(), (int) f.getLocation().getY(), this);
				
					f.setTextSize(g.getFontMetrics().stringWidth(CrabController.board.facts[f.getFriendCounter()]));
					
				g.drawString(CrabController.board.facts[f.getFriendCounter()], (int) (f.getLocation().getX() + f.getLocation().getWidth()), (int) f.getLocation().getY());
			}
		}
		
		g.setColor(new Color(239, 211, 70, 127));
		
		for (Rectangle r: CrabController.board.scentTrail){
			g.fill3DRect((int) r.getX(), (int) r.getY(), (int) r.getWidth()-1, (int) r.getHeight()-1, false);
		}
		
		g.setColor(new Color(0, 0, 0, 255));
		g.draw3DRect(20, 40, CrabController.board.getWidth() - 40, 20, false);
		
		g.setColor(new Color(255, 0, 0, 255));
		
		g.fill3DRect(21, 41, (int) CrabController.board.getProgress(), 19, false);
	

		if(!CrabController.board.player.getStarted() && CrabController.getCanBeAskedAQuestion()){			
			g.setColor(new Color(255, 255, 255, 255));
			g.fill3DRect(20, 70, CrabController.board.getWidth() - 39, CrabController.board.getHeight() - 160, true);
		
			g.setColor(new Color(0, 0, 0, 255));
			g.setFont(g.getFont().deriveFont(g.getFont().getStyle(),48));
			
			int newX = ((CrabController.board.getWidth() - 39) - (g.getFontMetrics().stringWidth(CrabController.board.questions[CrabController.board.getCurrQuestion()]))) / 2;
			
			g.drawString(CrabController.board.questions[CrabController.board.getCurrQuestion()], 20 + newX, 115);

			g.setFont(g.getFont().deriveFont(g.getFont().getStyle(),36));
			
			int answerYCoord = 180; // change
			
			for (String s: CrabController.board.answers[CrabController.board.getCurrQuestion()]){
				g.drawString(s, 40, answerYCoord);
				
				answerYCoord += 70; // change
			}			
		}
		
		if(CrabController.getDroughtStatus() >= 2){			
			g.setFont(g.getFont().deriveFont(g.getFont().getStyle(),52));
			
			int droughtLength = g.getFontMetrics().stringWidth("DROUGHT APPROACHING");
			
			g.drawString("DROUGHT APPROACHING", (CrabController.board.getWidth() - droughtLength) / 2, CrabController.board.getHeight() / 2);
		}
		
		if(CrabController.getStormStatus() >= 4){			
			g.setFont(g.getFont().deriveFont(g.getFont().getStyle(),52));
			
			int stormLength = g.getFontMetrics().stringWidth("STORM APPROACHING");
			
			g.drawString("STORM APPROACHING", (CrabController.board.getWidth() - stormLength) / 2, CrabController.board.getHeight() / 2);
		}
	}
	
	private void loadImages(){
		crabImage = new BufferedImage[3];
		crabImage[0] = createImage("images/bluecrab_0.png");
		crabImage[1] = createImage("images/bluecrab_1.png");
		crabImage[2] = createImage("images/bluecrab_2.png");
		backgroundImage = createImage("images/tempBackGround.jpg");
		enemyImage = new BufferedImage[3];
		enemyImage[0] = createImage("images/fish_bass_left.png");
		enemyImage[1] = createImage("images/fish_pickerel_left.png");
		enemyImage[2] = createImage("images/fish_trout_left.png");
		friendImage = new BufferedImage[3];
		friendImage[0] = createImage("images/bogturtle_left_0.png");
		friendImage[1] = createImage("images/bogturtle_left_1.png");
		friendImage[2] = createImage("images/bogturtle_left_2.png");
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
