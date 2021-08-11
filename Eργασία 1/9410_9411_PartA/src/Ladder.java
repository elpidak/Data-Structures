//Καλαμπούκα Ευαγγελία 9411 ekalampv@ece.auth.gr 6981751861 Κελέση Ελπίδα 9410 elpidakelesi@ece.auth.gr 6985938322
//Η κλάση ladder αντιστοιχεί στις σκάλες του παιχνιδιού. Οι μέθοδοι θεωρούνται απλές και δεν εξηγούνται.
public class Ladder {
	int ladderId;
	int upStepId;
	int downStepId;
	boolean broken;
	
	public Ladder(){
		ladderId=0;
		upStepId=0;

		downStepId=0;
		broken=false;
	}
	
	public Ladder(int l, int up, int down, boolean br){
		ladderId=l;
		upStepId=up;
		downStepId=down;
		broken=br;
	}
	
	public Ladder(Ladder ob1){
		ladderId= ob1.ladderId;
		upStepId=ob1.upStepId;
		downStepId=ob1.downStepId;
		broken=ob1.broken;
	}
	public void setLadderId(int l) {
		ladderId=l;
		
	}
	
	public void setUpStepId(int up) {
		upStepId=up;
	}
	
	public void setDownStepId(int down) {
		downStepId=down;
	}
	
	public void setBroken(boolean br) {
		broken=br;
	}
	
	public int getLadderId() {
		return ladderId;
	}
	
	public int getUpStepId() {
		return upStepId;
	}
	
	public int getDownStepId() {
		return downStepId;
	}
	
	public boolean getBroken() {
		return broken;
	}

}