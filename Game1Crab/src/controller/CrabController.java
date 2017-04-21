package controller;

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

public class CrabController {

	final int HEIGHT = 700;
	final int WIDTH = 1000;
	
	public static Board board;
	public static Player player;
	public static ArrayList<Enemy> enemies;
	public static ArrayList<Friend> friends;
	public View view;
	private Timer timer;

	public CrabController(){
		this.board = new Board(WIDTH, HEIGHT);//adjust values for size of board and length of path
		this.player = new Player(board);
		this.view = new View(WIDTH, HEIGHT);
		this.enemies = new ArrayList<Enemy>();
		this.friends = new ArrayList<Friend>();
		
		for (int i = 0; i < 1000; i++) {
			onTick(i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args){
		CrabController crabContoller = new CrabController();
	}
	
	public void onTick(int tick){
		System.out.println("onTick() ran " + tick);
		player.update();
		if (tick % 100 == 0){
			System.out.println("HEY YOU EVER HERE?");
			enemies.add(new Enemy(board));
		}
		if (!enemies.isEmpty()){
			System.out.println("Enemy update ran " + tick);
			for (Enemy e: enemies){
				e.update();
			}
		}
		view.animate();
	}
	
	
	public static void buttonPress(){
		System.out.println("Controller knows button was pressed");
		if (!player.started){
			player.started = true;
		}
		player.jump();
	}
}
