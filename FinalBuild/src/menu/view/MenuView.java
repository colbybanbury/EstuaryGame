package menu.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.View;

import game1.controller.CrabController;
import game3.controller.CubeController;
import menu.controller.MenuController;
import game2.controller.BoatController;
import game3.view.Animation;
import game2.view.BoatView;
import game1.view.CrabView;

public class MenuView extends JPanel{
	
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

	JFrame frame;
	
	public MenuView(int width, int height, MenuController menuController){
		this.width = width;
		this.height = height;
		menu = menuController;
		
		frame = new JFrame();
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height); //change to appropriate dimensions
		
		//frame.setSize(800, 800);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);

		//frame.add(game1View);
		//frame.add(game2View);
		
		//game1View.setVisible(false);
		//game2View.setVisible(false);
		
		
		JButton game1button = new JButton("Game 1: Crab");
		JButton game2button = new JButton("Game 2: Boat");
		JButton game3button = new JButton("Game 3: Cube");
		
		game1button.setBounds(new Rectangle((width-600)/4, 300, 200, 100));
		
		
		
		game2button.setBounds(new Rectangle((width-600)/2 + 200, 300, 200, 100));
		
		
		
		game3button.setBounds(new Rectangle(3*(width-600)/4 + 400, 300, 200, 100));
		
		frame.add(game1button);
		frame.add(game2button);
		frame.add(game3button);
		
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
	}
	
	public static void main(String[] args){
		
		
		run();
	}
	
	public static void run(){
	
	}	
}
