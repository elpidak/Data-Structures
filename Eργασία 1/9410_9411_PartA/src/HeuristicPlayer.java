//Καλαμπούκα Ευαγγελία 9411 ekalampv@ece.auth.gr 6981751861 Κελέση Ελπίδα 9410 elpidakelesi@ece.auth.gr 6985938322

//η κλαση HeuristicPlayer αντιπροσωπεύει τον παίκτη του παιχνιδιού που παίζει με στρατηγική και εχει μια μεταβλητη, path τυπου arraylist.

import java.util.ArrayList;

public class HeuristicPlayer extends Player {
	
	ArrayList<int[]> path;
	
	public HeuristicPlayer(){
		super();
		path = new ArrayList<int[]>();
	}
	
	public HeuristicPlayer(int playerId, String name, int score, Board board, int n){
		super(playerId, name, score, board);
		path = new ArrayList<int[]>(n);
	
	}

// Η μεθοδος evaluate αντιπροσωπευει την αξιολογηση της ζαριας του παικτη και επιστρεφει αυτη την αξιολογηση οποια ειναι αποτελεσμα της συναρτησης στοχου: (ποντοι)* 0,35 + (βήματα)* 0,65.
//Ορίζονται οι μεταβλητές check, τύπου Boolean που χρησιμοποιείται για το βρόχο do-while, ev, τύπου double, όπου αποθηκεύεται η αξιολόγηση της κίνησης, po, τύπου int, που αντιστοιχεί στους 
//πόντους του παίκτη για τη συγκεκριμένη κίνηση και finalPos, τύπου int,  που είναι η τελική θέση του παίκτη στη συγκεκριμένη ζαριά.
	
	double evaluate(int currentPos, int dice) {	
	
		boolean check;
		double ev; //η αξιολογηση της κινησης
		int po = 0;	// οι ποντοι που παιρνει ο παικτης με αυτη την κινηση
		int finalPos = (currentPos + dice);
		do {
			
		check = false;
		for(int i = 0; i<board.getApplesLength(); i++) {
			if(finalPos == board.apples[i].getAppleTileId())
				po = board.apples[i].getPoints();
		}
		for(int i=0; i<board.getSnakesLength(); i++) {
			if(finalPos == board.snakes[i].getHeadId()) {
				check = true;
				finalPos = board.snakes[i].getTailId();
			}
			if(check == true)
				continue;
		}
		for(int i=0; i<board.getLaddersLength(); i++) {
			if(finalPos == board.ladders[i].getDownStepId()) {
				check = true;
				finalPos = board.ladders[i].getUpStepId();
			}
		}
		}
		while(check == true);
		ev = ((finalPos - currentPos) * 0.65 + po * 0.35);
		return ev;
	}
	
//Στη μεθοδο getNextMove επιλέγεται η ζαρια με την καλυτερη αξιολογηση κινείται ο παίκτης με αυτητη ζαρια, αποθηκευονται τα απαραιτητα στοιχεια στη μεταβλητη "path" και επιστρεφεται η τελικη θεση 
//του παικτη. Oρίζονται δύο πίνακες ακεραίων Round, move, ένας πίνακας moves στοιχείων double, η μεταβλητή evaluation, τύπου double και η ακέραια μεταβλητή finaldie.
	
	int getNextMove(int currentPos) {
		
		int[] Round = new int[7];
		int[] move; // αποθηκευεται ο πινακας που επιστρεφει η συναρτηση move της κλασης player 
		int finaldie = 1; //η ζαρια που επιλεχθηκε
		double evaluation;
		double[] moves = new double[7]; //αποθηκευονται ολες οι δυνατες κινησεις του παικτη
		for(int i=1; i<=6; i++) 
			moves[i] = evaluate(currentPos, i);
		
		for(int j=1; j<6; j++) {
			if(moves[j] <= moves[j+1])
				finaldie = (j+1);
			else
				finaldie = j;
		}
		Round[0] = finaldie; //epilegmenh zaria 
		move = move(currentPos, finaldie);
		evaluation = evaluate(currentPos, finaldie);//η αξιολογηση της επιλεγμενης ζαριας
		Round[1] = (int) ((evaluation - move[0]*0.65) / 0.35); // pontoi
		Round[2] = (move[0] - currentPos); // vhmata
		Round[3] = (move[3] + move[4]); // mhla 
		Round[4] = move[1]; // fidia 
		Round[5] = move[2]; // skales
		Round[6] = move[3]; //κοκκινα μηλα
		path.add(Round);
		return move[0];
	}

//Στη μεθοδο statistics, εκτυπωνονται στοιχεια για τον καθε γυρο και στατιστικα για ολες τις κινησεις του παικτη με τη βοηθεια της μεταβλητης path. Οι ακέραιες μεταβλητες s, l, r, b χρησιμοποιουνται
//για να αποθηκευεται σε καθε γυρο ο αριθμος των φιδιων των σκαλων των κοκκινων και των μαυρων μηλων (στοιχεια αποθηκευμενα στην μεταβλητη path). Οι ακεραιες μεταβλητες vSnakes, vLadders, vRapples, 
//vBapples αντιστοιχουν στα στατιστικα στοιχεια του παιχνιδιου για τον παικτη.
	
	
	void statistics() {	
		int s, l, r, b;
		int vSnakes = 0; //Επισκεψεις σε πλακίδιο με κεφάλι φιδιού.
		int vLadders = 0; //Επισκεψεις σε πλακίδιο με βάση σκάλας.
		int vRapples = 0; //Επισκεψεις σε πλακίδιο με κόκκινο μήλο.
		int vBapples = 0; //Επισκεψεις σε πλακίδιο με μαύρο μήλο.
		
		for(int i=0; i< path.size(); i++) {
			s = path.get(i)[4];
			l = path.get(i)[5];
			r = path.get(i)[6];
			b = (path.get(i)[3] - path.get(i)[6]);
			System.out.println("O paikths sto guro " + (i + 1) + " ethese to zari iso me " + path.get(i)[0] + ". Anevhke " + path.get(i)[5] + " skales. Ton efagan " 
					+ path.get(i)[4] + " fidia kai efage " + path.get(i)[3] + " mhla.");
			vSnakes = (vSnakes + s);
			vLadders = (vLadders +l);
			vRapples = (vRapples + r);
			vBapples = (vBapples +b);
		}
		
		System.out.println("Episkepseis se plakidio me kefali fidiou: " + vSnakes + " Episkepseis se plakidio me vash skalas: " + vLadders + " Episkepseis se plakidio me kokkino mhlo: " + vRapples +
				" Episkepseis se plakidio me mauro mhlo: " + vBapples);
	}

}