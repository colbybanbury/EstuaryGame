package model;

import java.util.ArrayList;
import java.util.List;

public class Board {
	int width, height;
	List<Cube> cubes = new ArrayList<Cube>(9);
	public Board(int w, int h){
		this.width = w;
		this.height = h;
		for (int i = 0; i < 9; i++){
			cubes.add(new Cube(i, this));
		}
	}
	public int getHeight(){
		return height;
	}
	public int getWidth(){
		return width;
	}
	public void shuffle(){
		for (Cube c : cubes){
			c.roll();
		}
	}
}
