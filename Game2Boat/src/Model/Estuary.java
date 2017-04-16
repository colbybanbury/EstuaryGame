package model;

public class Estuary {
	private int damage = 0;//increase as the estuary gets damaged without protection

	private int integrity;//how much more damage the protection can take before it falls
	private int type; //0 is empty, 1 is sea wall, 2 is Gabion
						//needed for different image representations
	
	public Estuary(int t){
		this.type = t;
		switch(t){
			case 0: this.integrity = 0; //no wall so no protection
			case 1: this.integrity = 2; //sea wall so some protection
			case 2: this.integrity = 10;//Gabion so a lot of protection
		}
	}
	
	public void damage(int wakeDamage){//updates the integrity of the wall and returns the damage left over
		if(integrity>wakeDamage)
			integrity -= wakeDamage;
		else
			damage += wakeDamage - integrity;
	}
	
	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public int getIntegrity() {
		return integrity;
	}

	public void setIntegrity(int integrity) {
		this.integrity = integrity;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}
