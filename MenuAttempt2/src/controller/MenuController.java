package controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import view.MenuView;
import view.CrabView;
import controller.CrabController;

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
}
