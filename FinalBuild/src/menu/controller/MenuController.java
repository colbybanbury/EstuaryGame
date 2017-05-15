package menu.controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import menu.view.MenuView;
import game1.view.CrabView;
import game1.controller.CrabController;

public class MenuController {
	
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	final static int frameWidth =(int) screenSize.getWidth();
	final static int frameHeight =(int) screenSize.getHeight();
	public static MenuView menuView;
	
	public void MenuController(){
		MenuView v = new MenuView(frameWidth, frameHeight, this);
		this.menuView = v;
	}
	
	public static void main(String[] args){
		MenuController controller = new MenuController();	
		menuView.run();
	}
	/*
	public void game1pressed(){
		CrabController game1Controller = new CrabController();
		//View game1View = new View(frameWidth, frameHeight);
	}
	*/
}
