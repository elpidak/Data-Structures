//Καλαμπούκα Ευαγγελία 9411 ekalampv@ece.auth.gr 6981751861 Κελέση Ελπίδα 9410 elpidakelesi@ece.auth.gr 6985938322
//Η κλάση board αντιπροσωπεύει το ταμπλό του παιχνιδιού.
import java.util.Arrays;
import java.util.Random;

public class Board {
	
	int N, M;
	int[][] tiles;
	Snake[] snakes;
	Ladder[] ladders;
	Apple[] apples;
	
	public Board(){
		N=0;
		M=0;
	}
//Στη συνάρτηση Board δημιουργούνται οι πίνακες της κλάσης και αρχικοποιούνται οι μεταβλητές.
	public Board(int n, int m, int sn, int ln, int an){
	N=n;
	M=m;
	tiles = new int[n][m];
	snakes = new Snake[sn];
	for(int i=0; i<sn; i++) {
		snakes[i]=new Snake();
	}
	ladders = new Ladder[ln];
	for(int i=0; i<ln; i++) {
		ladders[i]=new Ladder();
	}
	apples = new Apple[an];
	for(int i=0; i<an; i++) {
		apples[i]=new Apple();
	}
	

}
//Constructor με όρισμα αντικείμενο τύπου board
	public Board(Board b1){
		N=b1.N;
		M=b1.M;
		tiles = new int[N][M];
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				tiles[i][j]=b1.tiles[i][j];
			}
		}
		ladders = new Ladder[b1.ladders.length];
		for(int i=0; i<ladders.length; i++) {
			ladders[i] = new Ladder(b1.ladders[i]);
		}
		apples = new Apple[b1.apples.length];
		for(int i=0; i<apples.length; i++) {
			apples[i] = new Apple(b1.apples[i]);
		}		
		snakes = new Snake[b1.snakes.length];
		for(int i=0; i<snakes.length; i++) {
			snakes[i] = new Snake(b1.snakes[i]);
		}		
		
	}
	
	public void setN(int n) {
		N=n;
	}
	
	public void setM(int m) {
		M=m;
	}
	
	public void setTiles(int N, int M, int w) {
		tiles[N][M]=w;
	}
	
	public void setSnakes(Snake ob1, int i) {
		snakes[i]=ob1;
	}
	
	public void setLadders(Ladder ob2, int j) {
		ladders[j]=ob2;
	}
	
	public void setApples(Apple ob3, int k) {
		apples[k]=ob3;
	}
	
	public int[][] getTiles() {
		return Arrays.copyOf(tiles, tiles.length);
	}
	
	public Snake[] getSnakes() {
		return Arrays.copyOf(snakes, snakes.length);
	}
	
	public int getSnakesLength() {
		return snakes.length;
	}
	
	public Ladder[] getLadders() {
		return Arrays.copyOf(ladders, ladders.length);
	}
	
	public int getLaddersLength() {
		return ladders.length;
	}
	
	public Apple[] getApples() {
		return Arrays.copyOf(apples, apples.length);
	}
	
	public int getApplesLength() {
		return apples.length;
	}
	public int getN() {
		return N;
	}
	
	public int getM() {
		return M;
	}
//Η συνάρτηση createBoard, δημιουργει το ταμπλό τοποθετώντας τυχαία τα μήλα, τα φίδια και τις σκάλες. Οι μεταβλητές a,b,c,d,e χρησιμοποιούνται για την απόδοση 
//των φιδιών, των μήλων και των σκαλών. Η μεταβλητή col χρησιμοποιείται για το χρώμα των μήλων και η μεταβλητή control χρησιμοποιείται για τον τερματισμό των τριών do-while,
//με τις οποίες εξασφαλίζεται οτι δεν συμπιπτουν κεφαλια φιδιων και κεφαλια φιδιων με βάσεις σκαλών ή μήλα. 	
	public void createBoard() {
		Random rand = new Random();
		int a, b, c, d, e;
		boolean col=true;
		boolean control;
		for (int i=0; i<snakes.length; i++) {
			snakes[i].setSnakeId(i+1);
			// Generally speaking, if you need to generate numbers from min to max (including both), 
			//you write rand.nextInt(max - min + 1) + min
				do {
					control=false;
					d=rand.nextInt(N*M-M)+ M+1;
					e=rand.nextInt(d-1) +1;
					for(int k=0; k<i; k++) {
					if (snakes[k].getHeadId()==d) {
						control=true;
						continue;
					}
					if(snakes[k].getTailId()==e)
						control=true;
					}
						
				}while(control==true);
					
			snakes[i].setHeadId(d);
			snakes[i].setTailId(e);
			}
		
		for(int i=0; i<ladders.length; i++) {
			ladders[i].setLadderId(i+1);
				do {
					control=false;
					c=rand.nextInt(N*M-M)+(M+1);
					b=rand.nextInt(c-1)+1;
					for(int j=0; j<snakes.length; j++) {
						for(int k=0; k<i; k++){
							if(ladders[k].getDownStepId()==b) {
								control=true;
								continue;
							}
							if(ladders[k].getUpStepId()==c)
								control = true;
							if(snakes[j].getHeadId()==b)
								control=true;
						}
					}
				}while( control==true);
			ladders[i].setDownStepId(b);
			ladders[i].setUpStepId(c);
			}
		
		for(int i=0; i<apples.length; i++) {
			apples[i].setAppleId(i+1);
				do {
				control=false;	
				a=rand.nextInt(N*M) +1 ;
				for(int j=0; j<snakes.length; j++) {
					for(int k=0; k<i; k++) {
						if(apples[k].getAppleTileId()==a) {
							control=true;
							continue;
						}
						if(snakes[j].getHeadId()==a)
							control=true;
					}
				}
				}while(control==true );
				apples[i].setAppleTileId(a);
				col=rand.nextBoolean();
				if(col) {
					apples[i].setColor("Red");
					apples[i].setPoints(rand.nextInt(10)+ 1);
				}
				else {
					apples[i].setColor("Black");
					apples[i].setPoints(-rand.nextInt(10)- 1);
				}
		}
			
		int k=1;
		for (int i=N-1; i>=0; i--) {
			for (int j=0; j<M; j++) {
				tiles[i][j]=k;
				k++;
			}
			k=k+M;
			i--;
		}
		int p=M+1;
		for(int i=N-2; i>=0; i--) {
			for(int j=M-1; j>=0; j--) {
				tiles[i][j]=p;
				p++;
			}
			p=p+M;
			i--;
		}
}
//Η συναρτηση αυτη δημιουργει και τυπωνει τρεις πινακες οπου είναι φανερο που υπαρχουν φιδια σκαλες και μηλα οπως ζητηθηκε στην εκφωνηση.
//η μεταβλητες check, check1, check2 χρησιμοποιούνται για την ομαλη εκβαση του κωδικα.
	public void createElementBoard() {
		String[][] elementBoardSnakes = new String[N][M];
		String[][] elementBoardLadders = new String[N][M];
		String[][] elementBoardApples = new String[N][M];
		
		System.out.println("The array of snakes is:");
		int check;
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				for(int k=0; k<snakes.length; k++) {
					check=0;
						if(snakes[k].getHeadId()==tiles[i][j]) {
							elementBoardSnakes[i][j]="SH"+Integer.toString(snakes[k].getSnakeId());
							check++;
						}
						else if(snakes[k].getTailId()==tiles[i][j]) {
							elementBoardSnakes[i][j]="ST"+Integer.toString(snakes[k].getSnakeId());
							check++;
						}
						
						else {
							elementBoardSnakes[i][j]="___";
						}
						if(check==1)
							break;
						if(check==0)
							continue;
				}
			System.out.print(elementBoardSnakes[i][j]+ "  ");
			}
		System.out.println();
		}	
		System.out.println();
		System.out.println("The array of ladders is: ");
		int check1;
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				for(int k=0; k<ladders.length; k++) {
					check1=0;
						if(ladders[k].getUpStepId()==tiles[i][j]) {
							elementBoardLadders[i][j]="LU"+Integer.toString(ladders[k].getLadderId());
							check1++;
					}
						else if(ladders[k].getDownStepId()==tiles[i][j]) {
							elementBoardLadders[i][j]="LD"+Integer.toString(ladders[k].getLadderId());
							check1++;
						}
						
						else {
							elementBoardLadders[i][j]="___";
				}
						if(check1==1)
							break;
						if(check1==0)
							continue;
				}
				
				System.out.print(elementBoardLadders[i][j]+ "  ");
			}
		
		System.out.println();
	}
		System.out.println();
		System.out.println("The array of apples is:");
		int check2;
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				for(int k=0; k<apples.length; k++) {
					check2=0;
						if(apples[k].getAppleTileId()==tiles[i][j] && apples[k].getColor()=="Red") {
							elementBoardApples[i][j]="AR"+Integer.toString(apples[k].getAppleId());
							check2++;
							
					}
						else if(apples[k].getAppleTileId()==tiles[i][j] && apples[k].getColor()=="Black") {
							elementBoardApples[i][j]="AB"+Integer.toString(apples[k].getAppleId());
							check2++;
						}
						
						else {
							elementBoardApples[i][j]="___";
						}
						if(check2==1) 
							break;
						if(check2==0)
							continue;
				}
					System.out.print(elementBoardApples[i][j]+ "  ");
				
			}
		System.out.println();	
		}
	}
}