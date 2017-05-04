package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import model.Estuary;

public class EstuaryTest {

	@Test
	public void constructorTest() {
		Estuary e1 = new Estuary(0); // no wall
		Estuary e2 = new Estuary(1); // sea wall
		Estuary e3 = new Estuary(2); // gabion
		Estuary e4 = new Estuary(3); // error handling
		assertEquals(e4.getDamage(), 0);
		assertEquals(e4.getIntegrity(), 0);
		assertEquals(e1.getDamage(), 0);
		assertEquals(e2.getDamage(), 0);
		assertEquals(e3.getDamage(), 0);
		//System.out.println(e1.getIntegrity());
		assertEquals(e1.getIntegrity(), 0);
		assertEquals(e2.getIntegrity(), 2);
		assertEquals(e3.getIntegrity(), 6);
		e1.setType(2); // gabion
		assertEquals(e1.getDamage(), 0);
		assertEquals(e1.getIntegrity(), 6);
		e3.setType(1); // sea wall
		assertEquals(e3.getDamage(), 0);
		assertEquals(e3.getIntegrity(), 2);
		e2.setType(0);
		assertEquals(e2.getDamage(), 0);
		assertEquals(e2.getIntegrity(), 0);
		e2.setType(3);
		assertEquals(e2.getDamage(), 0);
		assertEquals(e2.getIntegrity(), 0);
	}
	@Test
	public void damageTest(){
		int oldIntegrity, oldDamage;
		int damage = 1;
		List<Estuary> l1 = new ArrayList<Estuary> ();

		l1.add(new Estuary(0));
		l1.add(new Estuary(1));
		l1.add(new Estuary(2));
		l1.add(new Estuary(3));
		l1.add(new Estuary(4));
		for (int i=0; i < 13; i++){
			for (Estuary e : l1){
				oldIntegrity = e.getIntegrity();
				oldDamage = e.getDamage();
				e.damage(damage);
				if (oldIntegrity > 0){
					//System.out.println(e.getIntegrity());
					assertEquals(e.getIntegrity(), oldIntegrity - damage);
				}
				else {
					if (e.getType() != 3){
						assertEquals(e.getDamage(), oldDamage + damage);
						assertEquals(e.getType(), 0); // barrier should disappear if integrity is 0
					}
					else{
						assertEquals(e.getDamage(), 0);
					}
				}

			}
		}
		Estuary e2 = new Estuary(2);
		e2.setDamage(6);
		e2.setIntegrity(6);
		assertEquals(6, e2.getDamage());
		assertEquals(6, e2.getIntegrity());
		e2.damage(6);
		assertEquals(6, e2.getDamage());
		assertEquals(0, e2.getIntegrity());
	}	
}
