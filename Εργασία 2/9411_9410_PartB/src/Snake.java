//Καλαμπούκα Ευαγγελία 9411 ekalampv@ece.auth.gr 6981751861 Κελέση Ελπίδα 9410 elpidakelesi@ece.auth.gr 6985938322
//Η κλάση snake αντιστοιχεί στα φίδια του παιχνιδιού. Οι μέθοδοι θεωρούνται απλές και δεν εξηγούνται.
public class Snake {
	int snakeId;
	int headId;
	int tailId;
	
	public Snake(){
		snakeId=0;
		headId=0;
		tailId=0;
	}
	
	public Snake(int s, int h, int t){
		snakeId=s;
		headId=h;
		tailId=t;
	}
	
	public Snake(Snake ob){
		snakeId=ob.snakeId;
		headId=ob.headId;
		tailId=ob.tailId;
	}
	
	public void setSnakeId(int s) {
		snakeId=s;
	}
	
	public void setHeadId(int h) {
		headId=h;
	}
	
	public void setTailId(int t) {
		tailId=t;
	}
	
	public int getSnakeId() {
		return snakeId;
	}
	
	public int getHeadId() {
		return headId;
	}
	
	public int getTailId() {
		return tailId;
	}


}
