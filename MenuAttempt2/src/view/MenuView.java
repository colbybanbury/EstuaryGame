package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.View;

import controller.CrabController;
import controller.CubeController;
import controller.MenuController;
import controller.BoatController;
import view.Animation;
import view.BoatView;
import view.CrabView;

public class MenuView {
	
/*
 *  Has the old code for the games, but that can easily be changed out. 
 *  Each game would require a pass back to the main menu at the games end, or recreation of the menu, 
 *  and potentially a listener for a pause option that would let the user return to the main menu. 
 *  Right now the menu just passes control to the controller, 
 *  which creates the right image and runs.	
 */
	
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	private static int width = (int)screenSize.getWidth();
	private static int height = (int)screenSize.getHeight();
	static MenuController menu;
	
	private static JPanel menuPanel = new JPanel();

	//public static CrabController game1Controller = new CrabController();
	//public static BoatController game2Controller = new BoatController();
	//public static CubeController game3Controller = new CubeController();
	
	//public static CrabView game1View = game1Controller.view;
	//public static BoatView game2View = game2Controller.boatView;
	//Animation game3view = new Animation(width, height)
	
	public MenuView(int width, int height, MenuController menuCntrlr){
		this.width = width;
		this.height = height;
		menu = menuCntrlr;
	}
	
	public static void main(String[] args){
		run();
	}
	
	public static void run(){
		
		JFrame frame = new JFrame("");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height); //change to appropriate dimensions
		
		//frame.setSize(800, 800);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);

		//frame.add(game1View);
		//frame.add(game2View);
		
		menuPanel.setBounds(width/2 - 400, height/2 - 400, 800, 800);
		frame.add(menuPanel);
		
		//game1View.setVisible(false);
		//game2View.setVisible(false);
		
		
		JButton game1button = new JButton("Game 1: Crab");
		JButton game2button = new JButton("Game 2: Boat");
		JButton game3button = new JButton("Game 3: Cube");
		
		game1button.setBounds(new Rectangle(50, 300, 200, 100));
		game2button.setBounds(new Rectangle(300, 300, 200, 100));
		game3button.setBounds(new Rectangle(550, 300, 200, 100));
		
		menuPanel.add(game1button);
		menuPanel.add(game2button);
		menuPanel.add(game3button);
		
		game1button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("Game 1 Button Pressed");
				//Give control to CrabController
				game1pressed();
			}
		});
		
		game2button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("Game 2 Button Pressed");
				//Give control to BoatController
				game2pressed();
			}
		});
		
		game3button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("Game 3 Button Pressed");
				//Give control to CubeController
				game3pressed();
			}
		});
	
	}
	
	public static void game1pressed(){
		//game1View.setVisible(true);
		menuPanel.setVisible(false);
		//game1Controller.run();
		CrabController game1Controller = new CrabController();
	}
	
	public static void game2pressed(){
		BoatController game2Controller = new BoatController();
	}
	
	public static void game3pressed(){
		CubeController game3Controller = new CubeController();
	}
	
}
