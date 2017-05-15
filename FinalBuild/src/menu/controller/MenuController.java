package menu.controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import menu.view.MenuView;
import game1.view.CrabView;
import game2.controller.BoatController;
import game3.controller.CubeController;
import game1.controller.CrabController;

public class MenuController {
	
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	final static int frameWidth =(int) screenSize.getWidth();
	final static int frameHeight =(int) screenSize.getHeight();
	
	public static MenuView menuView;
	
	public MenuController(){
		this.menuView = new MenuView(frameWidth, frameHeight, this);
	}
	
	public static void main(String[] args){
		MenuController controller = new MenuController();
	}
	
	public static void game1pressed(){
		MenuController.menuView.setVisible(false);
		
		CrabController game1Controller = new CrabController();
	}
	
	public static void game2pressed(){
		BoatController game2Controller = new BoatController();
	}
	
	public static void game3pressed(){
		CubeController game3Controller = new CubeController();
	}
}
