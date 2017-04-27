package test;

import static org.junit.Assert.*;

import model.Board;
import model.Enemy;
import org.junit.Test;


public class EnemyTest {
	@Test
	public void test(){
		Board b = new Board(100,100);
		Enemy e = new Enemy(b);
	}
}
