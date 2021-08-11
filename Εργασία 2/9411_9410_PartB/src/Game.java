//Καλαμπούκα Ευαγγελία 9411 ekalampv@ece.auth.gr 6981751861 Κελέση Ελπίδα 9410 elpidakelesi@ece.auth.gr 6985938322

//Η κλάση game 

//Η κλάση Game αντιπροσωπεύει το παιχνίδι.

import java.util.Map;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.LinkedHashMap ;

public class Game {

	

	int round;

	

	public Game(){

		round=0;

	}

	

	public Game(int r){

		round=r;

	}

	

	public void setRound(int r) {

		round=r;

	}

	

	public int getRound() {

		return round;

	}

	

	

	HashMap<Integer,Double> first = new HashMap<Integer,Double>();

	int min_iter=0; // global μεταβλητή που δείχνει τις φορές που έριξε ο παίκτης το ζάρι για να καθοριστεί η σειρά που θα παίξει



	//συνάρτηση που ελέγχει έναν δυαδικό πίνακα,του οποίου τα στοιχεία αντιπροσοπεύουν την ζαριά που έχει ρίξει ο κάθε παίχτης (οι γραμμές είναι τα Id-1 των παιχτών και οι στήλες είναι η ζαριά-1, οπότε αν κάποιος με Id 2 ρίξει 4 θα βάλω άσσο στο στοιχίο [1][3]

	//η check περιέχει ανα ζαριά πόσα άτομα την έχουν ρίξει, οποτε αν έχω 2 άτομα που εχουν ρίξει 4 θα έχω στο στοιχείο [3] της check 2

	//τελος, η rowsToCheck που επιστρέφει η συνάρτητη περιέχει τον αριθμό της ζαριάς όπου έχω πάνω απο ένα άτομο να έχει ρίξει την ίδια ζαρια

	public ArrayList<Integer> checkTable(int[][] binaryTable) {

		ArrayList<Integer> rowsToCheck = new ArrayList<Integer>();

		int check[] = new int[6]; // πίνακας που έχει ανα στήλη πόσα άτομα έχουν ρίξει μια συγκεκριμένη ζαριά

		for(int i=0; i<6;i++) {

			for(int j=0; j<6; j++) {

				check[i] += binaryTable[j][i]; }}

		for(int i=0; i<6; i++) {

			if(check[i] > 1) rowsToCheck.add(i);

		}

		return rowsToCheck; 

	}

	

	//συνάρτηση που μελετά την δεδομένη κάθε φορά στήλη του δεδεμενου πίνακα, δηλαδή μελετά την ζαριά που έριξαν οι παίκτες, μηδενίζοντας τις υπόλοιπες στήλες, 

	//αφού πάρει τον αριθμό του ζαριού κάποιου παίκτη μηδενίζει το στοιχείο αυτό του πινακα

	//αν βρει από δύο παίκτες και πάνω που να έχουν ρίξει την ίδια ζαριά, τους βάζει να ξαναρίχνουν το ζάρι,

	//κάνει 1 το καινούργιο στοιχείο του πίνακα,ώστε να ξανακλιθεί η checkTable, μειώνει το itter κατά 1 , 

	//και θέτει στο χάρτη  first με κλειδί το id του παίκτη, σαν value τη καινούργια ζαριά,  σαν ένα επιπλέον δεκαδικό, 

	//ώστε να μη χάνεται καμία από τις πρώτες ζαριές του μέχρι να καθοριστεί η σειρά που θα παίζει ο παίκτης αυτός.

	public void checkRow(int row, int iter, int[][] binaryTable) {

		int check=0; // ελεγκτής 

		int newTable[][] = new int[6][6]; // πίνακας στον οποίον αποθηκεύεται ο array στην αρχή και μετά όποιος πίνακας είναι το όρισμα κλήσης της συνάρτησης αυτης.

		for(int i=0; i<6; i++) {

			for(int j=0; j<6; j++) {

				newTable[i][j] = binaryTable[i][j]; }}

		for(int i=0; i<6; i++) {

			if(row!=i) for(int j=0; j<6; j++) newTable[j][i] = 0; }

		for (int i=0; i<6; i++) {

			check += newTable[i][row];

			if(newTable[i][row]==1) {

				newTable[i][row]= 0;

				newTable[i][(int)(Math.random()*6+1)-1] = 1; }}

		if(check>1) {

			iter--;

			if (iter<min_iter) min_iter = iter;

			for(int i=0; i<6; i++) {

				for(int j=0; j<6; j++) {

					if(newTable[j][i]==1) first.put(j, first.get(j)+(i+1)*Math.pow(10, iter)); } }

			ArrayList<Integer> rowsToCheck = checkTable(newTable);

			for(int rowCheck:rowsToCheck) {

				checkRow(rowCheck, iter, newTable); }}

	}

	//Συνάρτηση καθορισμού της σειράς με την οποία θα παίξουν οι παίκτες. 

	//Η συνάρτηση αυτή επιστρέφει εναν χάρτη με τα ids των παικτών ταξινομημένα με βάση την πρώτη ή τις πρώτες τους ζαριές

	//καλεί την checkTable και την checkRow και μετά έχοντας το χάρτη με κλειδιά τα id των παικτών και values τις δεκαδικές ή ακέραιες ζαριές οτους

	//κάνει ταξινόμηση το χάρτη αυτό με μία απλή σύγκριση βρίσκοντας το ελάχιστο και

	//θέτει τελικά σαν value του κάθε παίκτη στο χάρτη sorted, τη στογυλλοποιημένη και πολλαπλασιασμένη επι 10^(-itter) ζαριά του κάθε παίκτη

	public Map<Integer,Integer> setTurns(ArrayList<Object> players) {

		

		int array[][] = new int [6][6]; // πίνακας στον οποίο αποθηκέυονται οι πρώτες ζαριές των παικτών με βάση τα id τους

		for(int i=0; i<players.size(); i++) {

			int temp = (int)(Math.random()*6+1);

			first.put(i,(double) temp);

			array[i][temp-1] = 1;

		}

		System.out.println();

		ArrayList<Integer> rowsToCheck = checkTable(array);

		for(int rowCheck:rowsToCheck) {

			checkRow(rowCheck, 0, array);

		}



		 LinkedHashMap<Integer,Integer> sorted;

		 sorted = new LinkedHashMap<Integer, Integer>();

		 int value;

		 int counter = 0; // μετρητής με αρχικοποίηση 0, ώστε να λαμβάνονται υπ'όψην όλοι οι παίκτες

		 do {

			double m = first.get(0); // αρχικοποίηση ώστε να επιτευχθεί η πρώτη σύγκριση

			int min_i = 0; // αρχικοποίηση του ελαχίστου

			for(int i=0; i<players.size(); i++) {  

				if(first.get(i) < m ) {

					m = first.get(i);

					min_i = i;

				}

			}

			value = Math.round((float)(first.get(min_i)*Math.pow(10,(min_iter*(-1))))); // μεταβλητή ώστε να γίνεται η στρογγυλοποίηση και να φαίνονται

																						//όλες οι πρώτες ζαριές που έριξαν οι παίκτες μέχρι να καθοριστεί η θέση τους  

			sorted.put(min_i, value);

			System.out.print("The player "+ min_i + " rolls the dice: ");

			System.out.println(sorted.get(min_i));





			first.put(min_i,10.0);

			counter++;

		 }while(counter<players.size());

		return sorted;

	}

	

 



	public static void main(String[] args) {

		// TODO Auto-generated method stub

		System.out.println("*****The Game Starts*****");

		Game g1= new Game(0);

		Board b3=new Board(20,10,3,3,6);

		b3.createBoard();

		b3.createElementBoard();

		int ro=0; //αρχικοποίηση των γύρων

		Player p1= new Player (1, "maria",0, b3); 

		HeuristicPlayer p2= new HeuristicPlayer (2, "mhtsos", 0, b3, ro);

		

		int array[][]=new int [2][5]; // πίνακας στον οποίο αποθηκεύεται ο πίνακας που επιστρέφει η move και τα id των παικτών

		ArrayList<Object> players = new ArrayList<Object>(); // η μεταβλητή players τύπου arraylist περιέχει τους παίκτες ταξινομημένους με βάση τα id τους

		players.add(p1);

		players.add(p2);

		Map<Integer,Integer> b = new LinkedHashMap<Integer,Integer>(); 

		b = g1.setTurns(players); // η μεταβλητή b τύπου map περιέρχει πλέον το αποτέλεσμα της setTurns

		

		int id1=1; // Mε τα id1 και id2 αρχικοποιείται η θέση του πίνακα που βρίσκεται ο κάθε παίκτης, όλοι ξεκινούν από

		int id2=1; // το 1 ρίχνοντας το ζάρι, αφού ρίξουν τη πρώτη ζαριά και καθοριστεί η σειρά που θα παίζουν

		array[0][0] = id1; // στην πρώτη στήλη του array αποθηκεύεται η θέση του κάθε παίκτη 1 και μετά αντίστοιχα του παίκτη 2


		array[1][0] = id2; 

		//Καθορίζεται η σειρά που παίζουν οι παίκτες, ανάλογα και με την πρώτη ζαριά και ορίζεται η συνθήκη τερματισμού του παιχνδιού 

		//όταν ένας από τους δύο φτάσει στο τερματισμό ή όταν ο αριθμός των γύρων υπερβεί το 100

		do {

			ro++;

			g1.setRound(ro);

			for(Map.Entry<Integer, Integer> entry : b.entrySet()) {

				if(players.get(entry.getKey()) instanceof HeuristicPlayer) {

					array[entry.getKey()][0] = ((HeuristicPlayer) players.get(entry.getKey())).getNextMove(array[entry.getKey()][0]);

				}

				else {

					array[entry.getKey()] = ((Player) players.get(entry.getKey())).move(array[entry.getKey()][0], (int) (Math.random()*6+1));

				}

			}

		}while((ro<100) && (array[0][0]<(b3.getN()*b3.getM())) && (array[1][0]<(b3.getN()*b3.getM())) );

		

		System.out.println("Rounds:" + g1.getRound());

		System.out.println("The points of the player "+ p1.getName() +" are: " + p1.getScore());

		System.out.println("The points of the player "+ p2.getName() +" are: " + p2.getScore());

		//Η συνάρτηση στόχου για τον player

		double ev1 = array[0][0]*0.65 + p1.getScore()*0.35 ;

		//H συνάρτηση στόχου για τον heuristic player

		double ev2 = array[1][0]*0.65 + p2.getScore()*0.35 ;

		

		if(ev1 > ev2) {

			System.out.println("The winner is : " + p1.getName());	

		}

		else if(ev1 < ev2) {

			if(p1.getScore() > p2.getScore()) {

				System.out.println("The winner is: " + p2.getName());

			}

			else

			{

				if(array[0][0]>array[1][0]) {

					System.out.println("The winner is: " + p1.getName());

				}

				else if(array[0][0] < array[1][0]) {

					System.out.println("The winner is: " + p2.getName());

				}

				else

				{

					System.out.println("We have tie."); //Σε περίπτωση που είναι στην ίδια θέση και η συνάρτηση στόχου τους είναι΄ίδια τότε έχουμε ισοπαλία

				}

			}

		}

		System.out.println("The statistics for the heuristic player are :");

		p2.statistics(); //Εκτυπώνει την στατιστικά του παίκτη heuristic player

		System.out.println();

		System.out.println("*****The Game Is Over*****");

	}



}