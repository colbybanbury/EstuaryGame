package menu.view;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import menu.controller.MenuController;

/**
 * 
 * @author Zach
 * @author chris
 * 
 * handles the visuals for the menu and creates the buttons to call the other games
 *
 */
public class MenuView extends JPanel{

	private static final long serialVersionUID = -2804351704146548063L;
	private int frameHeight;
	private int frameWidth;	
	JFrame frame;	
	private Font crabFont;
	private Font boatFont;
	private Font cubeFont;
	private Font titleFont;
	
	private static JButton game1button = new JButton("");
	private static JButton game2button = new JButton("");
	private static JButton game3button = new JButton("");

	private BufferedImage background;
	private Image scaledBackground;
	
	/**
	 * 
	 * @param width
	 * @param height
	 * 
	 * scales to the size of the screen, calls loadImages(), creates the three buttons,
	 *  and imports the special fonts
	 */
	public MenuView(int width, int height){
		frameHeight = height;
		frameWidth = width;
		frame = new JFrame();
		
		loadImages();
		
		try {
			this.crabFont = Font.createFont(Font.TRUETYPE_FONT, (new File("game1.fonts/PWBubbles.ttf")));
		} catch (FontFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        this.crabFont = crabFont.deriveFont(Font.PLAIN,20);
        GraphicsEnvironment ge1 =
            GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge1.registerFont(crabFont);
        
        try {
			this.boatFont = Font.createFont(Font.TRUETYPE_FONT, (new File("game1.fonts/Cardiff.ttf")));
		} catch (FontFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        this.boatFont = boatFont.deriveFont(Font.PLAIN,20);
        GraphicsEnvironment ge2 =
            GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge2.registerFont(boatFont);
        
        try {
			this.cubeFont = Font.createFont(Font.TRUETYPE_FONT, (new File("game1.fonts/ChockABlockNF.ttf")));
		} catch (FontFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        this.cubeFont = cubeFont.deriveFont(Font.PLAIN,20);
        GraphicsEnvironment ge3 =
            GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge3.registerFont(cubeFont);
        
        try {
			this.titleFont = Font.createFont(Font.TRUETYPE_FONT, (new File("game1.fonts/palamecia titling.ttf")));
		} catch (FontFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        this.titleFont = titleFont.deriveFont(Font.PLAIN,20);
        GraphicsEnvironment ge4 =
            GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge4.registerFont(titleFont);
		
		game1button.setContentAreaFilled(true);
		game1button.setBorderPainted(true);
		game1button.setEnabled(true);
		game1button.setFont(crabFont);
		game1button.setFont(game1button.getFont().deriveFont(game1button.getFont().getStyle(),56));
		game1button.setText("CRAB RUN");
		game1button.setBackground(new Color(0, 204, 254, 255));
		
		game2button.setContentAreaFilled(true);
		game2button.setBorderPainted(true);
		game2button.setEnabled(true);
		game2button.setFont(boatFont);
		game2button.setFont(game2button.getFont().deriveFont(game2button.getFont().getStyle(),48));
		game2button.setText("ESTUARY RACER");
		game2button.setBackground(new Color(76, 153, 0, 255));
		
		game3button.setContentAreaFilled(true);
		game3button.setBorderPainted(true);
		game3button.setEnabled(true);
		game3button.setFont(cubeFont);
		game3button.setFont(game3button.getFont().deriveFont(game3button.getFont().getStyle(),38));
		game3button.setText("Story Cubes");
		game3button.setBackground(new Color(255, 0, 0, 255));
		
		game1button.setBounds(new Rectangle((width-1200)/4, 300, 400, 100));		
		game2button.setBounds(new Rectangle((width-1200)/2 + 400, 300, 400, 100));		
		game3button.setBounds(new Rectangle(3*(width-1200)/4 + 800, 300, 400, 100));
		
		game1button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("Game 1 Button Pressed");
				//Give control to CrabController
				MenuController.game1pressed();
			}
		});
		
		game2button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("Game 2 Button Pressed");
				//Give control to BoatController
				MenuController.game2pressed();
			}
		});
		
		game3button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("Game 3 Button Pressed");
				//Give control to CubeController
				MenuController.game3pressed();
			}
		});
		
		game1button.setVisible(true);
		game2button.setVisible(true);
		game3button.setVisible(true);
		
		frame.add(game1button);
		frame.add(game2button);
		frame.add(game3button);
		
		frame.getContentPane().add(this);
		frame.setBackground(Color.WHITE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.setVisible(true);		
	}
	
	public void animate(){		
		frame.repaint();
	}
	
	/**
	 * draws the background and prints choose a game
	 */
	public void paint(Graphics g){
		g.drawImage(scaledBackground, 0, 0, null);	

		g.setFont(titleFont);
		g.setFont(g.getFont().deriveFont(g.getFont().getStyle(),160));
		int titleLength = g.getFontMetrics().stringWidth("CHOOSE A GAME");	
		
		g.drawString("CHOOSE A GAME", (frameWidth - titleLength)/2, 160);
	}
	
	/**
	 * loads in the background and scales it
	 */
	private void loadImages(){
		background = createImage("menu.images/2D_estuary.jpg");
		scaledBackground = background.getScaledInstance(frameWidth, frameHeight, Image.SCALE_DEFAULT);
	}
	
	/**
	 * reads an image from a file and returns it
	 * @param file	name of the file the the image is read from
	 * @return BufferedImage 	image read from the file
	 */
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
