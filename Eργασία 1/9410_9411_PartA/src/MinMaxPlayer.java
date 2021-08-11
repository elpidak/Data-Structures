//Καλαμπούκα Ευαγγελία 9411 ekalampv@ece.auth.gr 6981751861 Κελέση Ελπίδα 9410 elpidakelesi@ece.auth.gr 6985938322
//Η κλάση αυτή δημιουργεί έναν παίκτη που παίζει, βλέποντας την κίνηση του αντιπάλου, και επιλέγει την καλύτερη κίνηση και έχει μια μεταβλητή path τύπου arraylist.

import java.util.ArrayList;

public class MinMaxPlayer extends Player {
	
	ArrayList<int[]> path;
	
	public MinMaxPlayer(){
		super();
		path = new ArrayList<int[]>();
	}
	
	public MinMaxPlayer(int playerId, String name, int score, Board board, int n){
		super(playerId, name, score, board);
		path = new ArrayList<int[]>(n);
	}
	
	// Η μεθοδος evaluate αντιπροσωπευει την αξιολογηση της ζαριας του παικτη και επιστρεφει αυτη την αξιολογηση οποια ειναι αποτελεσμα της συναρτησης στοχου: (ποντοι)* 0,35 + (βήματα)* 0,65.
	//Ορίζονται οι μεταβλητές check, τύπου Boolean που χρησιμοποιείται για το βρόχο do-while, ev, τύπου double, όπου αποθηκεύεται η αξιολόγηση της κίνησης, po, τύπου int, που αντιστοιχεί στους 
	//πόντους του παίκτη για τη συγκεκριμένη κίνηση και finalPos, τύπου int,  που είναι η τελική θέση του παίκτη στη συγκεκριμένη ζαριά.
	public double evaluate(int currentPos, int dice, Board board) {
		
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
	// Συνάρτηση που επιλέγει τη καλύτερη κίνηση του παίκτη min max 
	public int chooseMinMaxMove(Node root, int currentPos,int opponentCurrentPos) {
		
		createMySubtree(root, 0, currentPos, opponentCurrentPos);
		double max = sunolikoevaluation(root, 4);							//maxdepth
		for(int i = 0; i<6; i++) {
			if(sunolikoevaluation(root.getChildren().get(i), 4) == max) 	//maxdepth
				return i+1;
		}
		return 0;
		
	}
	
	// Συνάρτηση που κανει το evaluation όλου του κόμβου
	double sunolikoevaluation(Node n, int maxDepth) {
		double sunevaluation;											// συνολικο evaluation, η τιμή της συνολικής αξιολόγισης που θα επιστρέφει η συνάρτηση
		if(n.getNodeDepth() == maxDepth)
			return n.getNodeEvaluation();										
		double min = n.getChildren().get(0).getNodeEvaluation(); 		// min  μεταβλητή στην οποία αποθηκεύονται τα παιδια του κομβου n με το evalaution τους
		double max = n.getChildren().get(0).getNodeEvaluation();		// max μεταβλητή στην οποία αποθηκεύονται τα παιδιά του κόμβου n με το evaluation τους
		int paidi = 0;													// αριθμός παιδιών
		if(n.getNodeDepth() == maxDepth)
			return n.getNodeEvaluation();
		
		if(n.getNodeDepth() % 2 == 1)									//μονός αριθμός άρα παίζω εγώ
			for(int i=1; i<6; i++) {
				if(n.getChildren().get(i).getNodeEvaluation() < min) {
					min = n.getChildren().get(i).getNodeEvaluation();
					paidi = i;
				}			
		}
		
		else
			for(int i=1; i<6; i++) {
				if(n.getChildren().get(i).getNodeEvaluation() > max) {
					max = n.getChildren().get(i).getNodeEvaluation();
					paidi = i;
				}
			}
		sunevaluation = n.getNodeEvaluation() + sunolikoevaluation(n.getChildren().get(paidi), maxDepth);  // συνολικό evaluation 
		
		return sunevaluation;
		
		
	}
	// Συνάρτηση καθορισμού της επόμενης κίνησης
	int [] getNextMove (Board board, int currentPos, int opponentCurrentPos) {
		int previousscore = score;          // το score 
		int[] tipota= {currentPos,0};       // πίνακας που περιέχει τη θέση του παίκτη και τη ζαριά
		Node c = new Node(null, 0,tipota ,board,0); // δημιουργία ενός αντιγράφου του κόμβου ριζας
		createMySubtree(c,1,currentPos, opponentCurrentPos);
		int bestmove = chooseMinMaxMove(c,currentPos, opponentCurrentPos); // η καλύτερη κίνηση, η οποία επιλέγεται απο τη chooseMinMaxMove με ορίσματα το αντίγραφο του κόμβου του πατέρα και τη θέση του παίκτη και του αντιπάλου.
		int [] result = move(currentPos, bestmove);  // αποθηκεύεται στον πίνακα result ο πίνακας που επιστρέφει η move με όριμα την συγκεκριμένη θέση που έχει ο παίκτης και την καλύτερη κίνηση(ζαριά) που μπορεί να κάνει.
		int [] arraypath = new int [7];   // ένα καινούργιο arraypath που θα προστεθεί στο path, ώστε το τελευταίο να αναβαθμιστεί
		
		arraypath[0] = bestmove;        	 // dice
		arraypath[1]= result[0]; 			//position
		arraypath[2]= previousscore ; 		// score
		arraypath[3]= result[4]; 			// black apples
		arraypath[4]= result[1]; 			//snakes
		arraypath[5]= result[2];			// ladders
		arraypath[6]= result[3]; 			// red apples
		path.add(arraypath);
		return arraypath;
	}

	// Συνάρτηση δηιμουργίας του δέντρου του παίκτη
	void createMySubtree(Node parent, int depth, int currentPos, int opponentCurrentPos) {
		int av=0;                     // available movements
		if(depth > 4)                //  maxdepth
			return;
		
		for(int i=1; i<7; i++) {
			if((board.getN()*board.getM())- currentPos >=  i) {
				av=av+1;
			}
		}
		for(int j=0; j<av; j++) {
			Board board1 = new Board(parent.getNodeBoard());			// καινούργιο board με όρισμα το board του πατέρα
			Player testPlayer =  new Player(0, "xristos", 0, board1);   // ένας test player που παίζει στο board1, ώστε ότι αλλαγές γίνονται να γίνου στο board1 και όχι στο board, για να μη σπάσουν οι σκάλες.
			int [] array1= testPlayer.move(currentPos, j);				// πίνακας στον οποίον αποθηκεύεται ο πίνακας που επιστρέφει η move, με ορίσματα την κίνηση του παίκτη και ως ζαρία, παίρνει διαδοχικά όλες τις διαθέσιμες ζαριές.
			int [] array2 = new int [] {array1[0], j};					//  πίνακας στον οποίον αποθηκεύεται πίνακας με τη κίνηση του παίκτη και τη θέση του μετά την κίνηση.
			Node a = new Node(parent, depth, array2, board1,evaluate(currentPos, j, board1));   // δημιουργία κομβου-παιδιού
			parent.getChildren().add(a);
			createOpponentSubtree(a,depth+1,array1[0],opponentCurrentPos,evaluate(currentPos, j, board1));	
		}	
	}
	
	// Συνάρτηση δημιουργίας του δέντρου του αντίπαλου παίκτη
	void createOpponentSubtree(Node parent, int depth, int currentPos, int opponentCurrentPos, double parentEval) {
		int av=0;  						// available movements
		for(int i=1; i<7; i++) {
			if((board.getN()*board.getM())- opponentCurrentPos >=  i) {
				av=av+1;
			}
		}
		for(int j=0; j<av; j++) {
			Board board2 = new Board(parent.getNodeBoard());   			// καινούργιο board με όρισμα το board του πατέρα
			Player testPlayer =  new Player(0, "makis", 0, board2);		// ένας test player που παίζει στο board1, ώστε ότι αλλαγές γίνονται να γίνου στο board1 και όχι στο board, για να μη σπάσουν οι σκάλες.
			int [] array3= testPlayer.move(opponentCurrentPos, j);		// πίνακας στον οποίον αποθηκεύεται ο πίνακας που επιστρέφει η move, με ορίσματα την κίνηση του παίκτη και ως ζαρία, παίρνει διαδοχικά όλες τις διαθέσιμες ζαριές.
			int [] array4 = new int [] {array3[0], j};					// πίνακας στον οποίον αποθηκεύται πίνακας με τη κίνηση του παίκτης και τη θέση του μετά την κίνηση.
			Node b = new Node(parent, depth, array4, board2,evaluate(opponentCurrentPos, j, board2));  // δημιουργία κόμβου παιδιού
			parent.getChildren().add(b);
			createMySubtree(b,depth+2,currentPos,array3[0]);
		}	
			
	}
	
	//Εκτυπώνει τα στατιστικά του παίκτη min max player
	
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
			b = (path.get(i)[3]);
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