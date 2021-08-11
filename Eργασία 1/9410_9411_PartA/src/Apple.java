//Καλαμπούκα Ευαγγελία 9411 ekalampv@ece.auth.gr 6981751861 Κελέση Ελπίδα 9410 elpidakelesi@ece.auth.gr 6985938322
//Η κλάση apple αντιστοιχεί στα μήλα του παιχνιδιού. Οι μέθοδοι θεωρούνται απλές και δεν εξηγούνται.
public class Apple {
	int appleId;
	int appleTileId;
	String color;
	int points;
	
	public Apple(){
		appleId=0;
		appleTileId=0;
		color="";
		points=0;
	}
	
	public Apple(int a, int at, String c, int p){
		appleId=a;
		appleTileId=at;
		color=c;
		points=p;
	}
	
	public Apple(Apple ob2){
		appleId = ob2.appleId;
		appleTileId=ob2.appleTileId;
		color=ob2.color;
		points=ob2.points;
	}
	public void setAppleId(int a) {
		appleId=a;
	}
	
	public void setAppleTileId(int at) {
		appleTileId=at;
	}
	
	public void setColor(String c) {
		color=c;
	}
	
	public void setPoints(int p) {
		points=p;
	}
	
	public int getAppleId() {
		return appleId;
	}
	
	public int getAppleTileId() {
		return appleTileId;
	}
	
	public String getColor() {
		return color;
	}
	
	public int getPoints() {
		return points;
	}


}