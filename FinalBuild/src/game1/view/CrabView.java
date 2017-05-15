package game1.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.net.MalformedURLException;
import java.net.URL;

import java.text.StringCharacterIterator;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import game1.controller.CrabController;
import game1.model.Friend;

public class CrabView extends JPanel{
	private int frameHeight;
	private int frameWidth;
	
	public static JButton jump = new JButton("");
	public static JButton answer1 = new JButton("");
	public static JButton answer2 = new JButton("");
	public static JButton answer3 = new JButton("");
	
	private BufferedImage[] crabImage;
	private BufferedImage[] enemyImage;
	private BufferedImage[] friendImage;
	private BufferedImage wrongImage;
	
	private Font titleFont;
	
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
		
		answer1.setContentAreaFilled(true);
		answer1.setBorderPainted(true);
		answer1.setEnabled(true);
		answer1.setFont(answer1.getFont().deriveFont(answer1.getFont().getStyle(),30));
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
		answer2.setFont(answer2.getFont().deriveFont(answer2.getFont().getStyle(),30));
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
		answer3.setFont(answer3.getFont().deriveFont(answer3.getFont().getStyle(),30));
		answer3.setText("ANSWER c");
		answer3.setBackground(Color.GREEN);
		answer3.setBounds(858, CrabController.board.getHeight() - 240 , 320, 80);
		answer3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				CrabController.answerButton3Press();
			}
		});
		
		frame.add(jump);
		frame.add(answer1);
		frame.add(answer2);
		frame.add(answer3);
		
		answer1.setEnabled(false);
		answer1.setVisible(false);
		
		answer2.setEnabled(false);
		answer2.setVisible(false);
		
		answer3.setEnabled(false);
		answer3.setVisible(false);
		
		frame.getContentPane().add(this);
		frame.setBackground(Color.BLUE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(frameWidth, frameHeight);
		frame.setVisible(true);
		
		try {
			this.titleFont = Font.createFont(Font.TRUETYPE_FONT, (new File("fonts/PWBubbles.ttf")));
		} catch (FontFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        this.titleFont = titleFont.deriveFont(Font.PLAIN,20);
        GraphicsEnvironment ge =
            GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(titleFont);
        
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
			for (game1.model.Enemy e: CrabController.board.enemies){
				g.drawImage(enemyImage[e.getPicNum()], (int) e.getLocation().getX(), (int) e.getLocation().getY(), this);
			}
		}
		
		g.setColor(new Color(0, 0, 0, 255));
		g.setFont(g.getFont().deriveFont(g.getFont().getStyle(),30));
		
		if (!CrabController.board.friends.isEmpty()){
			for(Iterator<game1.model.Friend> friendIterator = CrabController.board.friends.iterator(); friendIterator.hasNext();){
				game1.model.Friend f = friendIterator.next();
				
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
	

		if(!CrabController.board.player.getStarted() && CrabController.board.player.getFinished() && CrabController.getCanBeAskedAQuestion()){
			g.setColor(new Color(255, 255, 255, 127));
			g.fill3DRect(0, 0, CrabController.board.getWidth(), CrabController.board.getHeight(), false);
			
			Font defaultFont = g.getFont();
			
			g.setFont(titleFont);
			g.setColor(new Color(0, 0, 0, 255));
			g.setFont(g.getFont().deriveFont(g.getFont().getStyle(),218));
			
			int titleLength = g.getFontMetrics().stringWidth("YOU WIN");
			
			g.drawString("YOU WIN", (CrabController.board.getWidth() - titleLength) / 2, CrabController.board.getHeight() / 2);
		
			g.setFont(defaultFont);
			
			//Add Replay button, oh boy
			
		}else if(!CrabController.board.player.getStarted() && !CrabController.getCanBeAskedAQuestion()){
			g.setColor(new Color(255, 255, 255, 127));
			g.fill3DRect(0, 0, CrabController.board.getWidth(), CrabController.board.getHeight(), false);
			
			g.setColor(new Color(0, 0, 0, 255));
			g.setFont(g.getFont().deriveFont(g.getFont().getStyle(),34));
			
			int startScreenLength = g.getFontMetrics().stringWidth("[Press SPACE to start]");
			
			g.drawString("[Press SPACE to start]", (CrabController.board.getWidth() - startScreenLength) / 2, 3 * CrabController.board.getHeight() / 5);
		
			Font defaultFont = g.getFont();
			
			g.setFont(titleFont);
			g.setFont(g.getFont().deriveFont(g.getFont().getStyle(),218));
			
			int titleLength = g.getFontMetrics().stringWidth("CRAB RUN");
			
			g.drawString("CRAB RUN", (CrabController.board.getWidth() - titleLength) / 2, CrabController.board.getHeight() / 2);
		
			g.setFont(defaultFont);
		}else if(!CrabController.board.player.getStarted() && CrabController.getCanBeAskedAQuestion() && !CrabController.getIsItGracePeriod()){
			
			g.setColor(new Color(255, 255, 255, 255));
			g.fill3DRect(20, 70, CrabController.board.getWidth() - 39, CrabController.board.getHeight() - 160, true);
		
			g.setColor(new Color(0, 0, 0, 255));
			g.setFont(g.getFont().deriveFont(g.getFont().getStyle(),48));
			
			StringCharacterIterator charIterator = new StringCharacterIterator(CrabController.board.questions.get(CrabController.board.getCurrQuestion()).getPrompt());
			char c = charIterator.first();
			int newX;
			int index = 0;
			ArrayList<String> questionLines = new ArrayList<String>();
			
			questionLines.add("");
			
			while(c!= StringCharacterIterator.DONE){
				String newString = questionLines.get(index).concat(Character.toString(c));
				questionLines.remove(index);
				questionLines.add(newString);
				newX = 25 + g.getFontMetrics().stringWidth(questionLines.get(index));
			
				if(newX >= CrabController.board.getWidth() - 30){
					index++;
					questionLines.add("");
				}
				c = charIterator.next();
			}
			
			int yIncrement = 0;
			for(String s: questionLines){
				g.drawString(s, 20 + ((CrabController.board.getWidth() / 2) - (g.getFontMetrics().stringWidth(s)) / 2), 115 + yIncrement);
			
				yIncrement += 45;
			}	
			yIncrement += 15;
			
			g.setFont(g.getFont().deriveFont(g.getFont().getStyle(),36));
			
			int answerYCoord = 115 + yIncrement;
			
			for (String s: CrabController.board.questions.get(CrabController.board.getCurrQuestion()).getAnswers()){
					
				if(CrabController.getAnswer1Wrong() && s.equals(CrabController.board.questions.get(CrabController.board.getCurrQuestion()).getAnswers().get(0))){
					g.setColor(new Color(160, 160, 160, 255));
					
					g.drawImage(wrongImage, 50 + g.getFontMetrics().stringWidth(s), answerYCoord - 40, this);
				}
				if(CrabController.getAnswer2Wrong() && s.equals(CrabController.board.questions.get(CrabController.board.getCurrQuestion()).getAnswers().get(1))){
					g.setColor(new Color(160, 160, 160, 255));
					
					g.drawImage(wrongImage, 50 + g.getFontMetrics().stringWidth(s), answerYCoord - 40, this);					
				}
				if(CrabController.getAnswer3Wrong() && s.equals(CrabController.board.questions.get(CrabController.board.getCurrQuestion()).getAnswers().get(2))){
					g.setColor(new Color(160, 160, 160, 255));
					
					g.drawImage(wrongImage, 50 + g.getFontMetrics().stringWidth(s), answerYCoord - 40, this);					
				}				

				g.drawString(s, 40, answerYCoord);
				
				g.setColor(new Color(0, 0, 0, 255));
					
				answerYCoord += 70;
			}			
		}else if(!CrabController.board.player.getStarted() && CrabController.getIsItGracePeriod()){
			
			g.setColor(new Color(255, 255, 255, 127));
			g.fill3DRect(0, 0, CrabController.board.getWidth(), CrabController.board.getHeight(), false);
			
			Font defaultFont = g.getFont();
			
			g.setFont(titleFont);
			g.setColor(new Color(0, 0, 0, 255));
			g.setFont(g.getFont().deriveFont(g.getFont().getStyle(),218));
			
			int titleLength = g.getFontMetrics().stringWidth(String.valueOf(CrabController.getGracePeriodCounter()));
			
			g.drawString(String.valueOf(3 - CrabController.getGracePeriodCounter()), (CrabController.board.getWidth() - titleLength) / 2, CrabController.board.getHeight() / 2);
		
			g.setFont(defaultFont);
		}
		
		if(CrabController.getDroughtStatus() >= 1){			
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
		enemyImage = new BufferedImage[3];
		enemyImage[0] = createImage("images/fish_bass_left.png");
		enemyImage[1] = createImage("images/fish_pickerel_left.png");
		enemyImage[2] = createImage("images/fish_trout_left.png");
		friendImage = new BufferedImage[3];
		friendImage[0] = createImage("images/bogturtle_left_0.png");
		friendImage[1] = createImage("images/bogturtle_left_1.png");
		friendImage[2] = createImage("images/bogturtle_left_2.png");
		wrongImage = createImage("images/no.png");
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
