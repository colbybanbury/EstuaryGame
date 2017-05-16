package menu.controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import menu.view.MenuView;
import game2.controller.BoatController;
import game3.controller.CubeController;
import game1.controller.CrabController;

public class MenuController implements ActionListener{
	
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	final static int frameWidth =(int) screenSize.getWidth();
	final static int frameHeight =(int) screenSize.getHeight();
	
	private static Timer tick;
	
	public static MenuView menuView;
	
	public MenuController(){
		MenuController.menuView = new MenuView(frameWidth, frameHeight);
		
		this.tick = new Timer(100, this); // .1 sec
	}
	
	public static void main(String[] args){
		@SuppressWarnings("unused")
		MenuController controller = new MenuController();
	}
	
	public static void game1pressed(){		
		@SuppressWarnings("unused")
		CrabController game1Controller = new CrabController();
	}
	
	public static void game2pressed(){
		@SuppressWarnings("unused")
		BoatController game2Controller = new BoatController();
	}
	
	public static void game3pressed(){
		@SuppressWarnings("unused")
		CubeController game3Controller = new CubeController();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == tick){
			menuView.animate();
		}		
	}
}
