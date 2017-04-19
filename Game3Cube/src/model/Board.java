package model;

import java.util.ArrayList;
import java.util.List;

public class Board {
	int width, height;
	static final int NUM_CUBES = 6;
	public List<Cube> cubes = new ArrayList<Cube>(NUM_CUBES);
	public Board(int w, int h){
		this.width = w;
		this.height = h;
		for (int i = 0; i < NUM_CUBES; i++){
			cubes.add(new Cube(i, this));
		}
	}
	public int getHeight(){
		return height;
	}
	public int getWidth(){
		return width;
	}
	public int getNumCubes(){
		return NUM_CUBES;
	}
	public void shuffle(){
		for (Cube c : cubes){
			c.roll();
		}
	}
	
	public List<Cube> getCubes(){
		return cubes;
	}
	
}
