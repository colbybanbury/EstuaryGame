package game2.model;

public class Estuary{
	/**
	 * Barriers that prevent the shore from being damaged (includes empty shore
	 * and empty water)
	 * @author Colby Banbury, Collin Clark
	 */
	private int damage = 0;//increase as the estuary gets damaged without protection

	private int integrity;//how much more damage the protection can take before it falls
	private int type; //0 is empty, 1 is sea wall, 2 is Gabion
						//needed for different image representations
	
	public Estuary(int t){
		/**
		 * constructor
		 * @param t type of estuary. Ranges between 0 and 3. 0: no wall. 1: sea wall.
		 * 2: gabion. 3: open water
		 * @param cX x location in view (soon to be changed to linear representation)
		 * @param cY y location in view (soon to be changed to linear representation)
		 */
		
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
	
	public int damage(int wakeDamage){
		/**
		 * updates integrity of the wall and returns the damage left over
		 * @param wakeDamage amount of damage to add to the estuary
		 */
		
		if(type!=3){	//if it's not open water
			if(integrity>wakeDamage){
				integrity -= wakeDamage;
				return 0;
			}
			else{
				int tempDamage = wakeDamage - integrity;
				damage += tempDamage;
				integrity = 0;
				type = 0;
				return tempDamage;
			}
		}
		return 0;
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
		/**
		 * updates type and integrity changes with it
		 * @param type type to change estuary to
		 */
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

	
	
	
}
