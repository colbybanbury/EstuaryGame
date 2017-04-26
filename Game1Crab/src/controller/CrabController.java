package controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import model.Board;
import model.Mover;
import model.Player;
import model.Enemy;
import model.Friend;
import view.View;

public class CrabController  implements ActionListener{
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	final int HEIGHT = (int)screenSize.getHeight();
	final int WIDTH = (int)screenSize.getWidth();
	
	public static Board board;
	public static Player player;
	public View view;
	private Timer timer;
	private final int DELAY = 50;//update rate (50ms) may need to make faster

	public CrabController(){
		this.board = new Board(WIDTH, HEIGHT);//adjust values for size of board and length of path
		this.view = new View(WIDTH, HEIGHT);
		
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	public static void main(String[] args){
		CrabController crabController = new CrabController();
	}
	
	@Override
    public void actionPerformed(ActionEvent e){
		onTick();
    }
	
	public void onTick(){
		board.enemies.add(new Enemy(board));
		board.update();
		view.animate();
	}
	
	
	public static void buttonPress(){
		if (!board.player.getStarted()){
			board.player.setStarted(true);
		}
		board.player.jump();
	}
}
