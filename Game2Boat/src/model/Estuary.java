package model;

public class Estuary{
	private int damage = 0;//increase as the estuary gets damaged without protection

	private int integrity;//how much more damage the protection can take before it falls
	private int type; //0 is empty, 1 is sea wall, 2 is Gabion
						//needed for different image representations
	
	int circleX;
	int circleY;
	
	public Estuary(int t, int cX, int cY){
		this.circleX = cX;
		this.circleY = cY;
		this.type = t;
		switch(t){
			case 0: 
				this.integrity = 0; //no wall so no protection
				break;
			case 1: 
				this.integrity = 2; //sea wall so some protection
				break;
			case 2: 
				this.integrity = 6;//Gabion so a lot of protection
				break;
			default:
				this.integrity = 0;	//open water
				break;
		}
	}
	
	public void damage(int wakeDamage){//updates the integrity of the wall and returns the damage left over
		if(type!=3){	//if it's not open water
			if(integrity>wakeDamage)
				integrity -= wakeDamage;
			else{
				damage += wakeDamage - integrity;
				integrity = 0;
				type = 0;
			}
		}
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

	public void setType(int type) {	//updates type and integrity changes with it
		this.type = type;
		switch(type){
		case 0: 
			this.integrity = 0; //no wall so no protection
			break;
		case 1: 
			this.integrity = 2; //sea wall so some protection
			break;
		case 2: 
			this.integrity = 6;//Gabion so a lot of protection
			break;
		default:
			this.integrity = 0;
			break;
		}
	}

	public int getCircleX() {
		return circleX;
	}

	public int getCircleY() {
		return circleY;
	}
	
	
}
