package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import controller.BoatController;

public class View {
	private int height;
	private int width;
	public static JButton move = new JButton("move");;
	
	
	public View(int h, int w){
		this.height = h;
		this.width = w;
		//makes a button that covers the whole screen and is invisible
		move.setOpaque(false);
		move.setContentAreaFilled(false);
		move.setBorderPainted(false);
		move.setBounds(0, 0, w, h);
		move.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
	//when the mouse is clicked it calls buttonPress in the controller
				BoatController.buttonPress();
			}
		});
		
		//TODO
	}
	
	public void paint(Graphics g){
		//g.drawImage(boat, x, y, Observer)
		//TODO
	}
	
	

}
