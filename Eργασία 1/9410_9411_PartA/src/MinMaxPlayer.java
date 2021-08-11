//���������� ��������� 9411 ekalampv@ece.auth.gr 6981751861 ������ ������ 9410 elpidakelesi@ece.auth.gr 6985938322
//� ����� ���� ���������� ���� ������ ��� ������, ��������� ��� ������ ��� ���������, ��� �������� ��� �������� ������ ��� ���� ��� ��������� path ����� arraylist.

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
	
	// � ������� evaluate �������������� ��� ���������� ��� ������ ��� ������ ��� ���������� ���� ��� ���������� ����� ����� ���������� ��� ���������� ������: (������)* 0,35 + (������)* 0,65.
	//��������� �� ���������� check, ����� Boolean ��� ��������������� ��� �� ����� do-while, ev, ����� double, ���� ������������ � ���������� ��� �������, po, ����� int, ��� ����������� ����� 
	//������� ��� ������ ��� �� ������������ ������ ��� finalPos, ����� int,  ��� ����� � ������ ���� ��� ������ ��� ������������ �����.
	public double evaluate(int currentPos, int dice, Board board) {
		
		boolean check;
		double ev; //� ���������� ��� �������
		int po = 0;	// �� ������ ��� ������� � ������� �� ���� ��� ������
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
	// ��������� ��� �������� �� �������� ������ ��� ������ min max 
	public int chooseMinMaxMove(Node root, int currentPos,int opponentCurrentPos) {
		
		createMySubtree(root, 0, currentPos, opponentCurrentPos);
		double max = sunolikoevaluation(root, 4);							//maxdepth
		for(int i = 0; i<6; i++) {
			if(sunolikoevaluation(root.getChildren().get(i), 4) == max) 	//maxdepth
				return i+1;
		}
		return 0;
		
	}
	
	// ��������� ��� ����� �� evaluation ���� ��� ������
	double sunolikoevaluation(Node n, int maxDepth) {
		double sunevaluation;											// �������� evaluation, � ���� ��� ��������� ����������� ��� �� ���������� � ���������
		if(n.getNodeDepth() == maxDepth)
			return n.getNodeEvaluation();										
		double min = n.getChildren().get(0).getNodeEvaluation(); 		// min  ��������� ���� ����� ������������� �� ������ ��� ������ n �� �� evalaution ����
		double max = n.getChildren().get(0).getNodeEvaluation();		// max ��������� ���� ����� ������������� �� ������ ��� ������ n �� �� evaluation ����
		int paidi = 0;													// ������� �������
		if(n.getNodeDepth() == maxDepth)
			return n.getNodeEvaluation();
		
		if(n.getNodeDepth() % 2 == 1)									//����� ������� ��� ����� ���
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
		sunevaluation = n.getNodeEvaluation() + sunolikoevaluation(n.getChildren().get(paidi), maxDepth);  // �������� evaluation 
		
		return sunevaluation;
		
		
	}
	// ��������� ���������� ��� �������� �������
	int [] getNextMove (Board board, int currentPos, int opponentCurrentPos) {
		int previousscore = score;          // �� score 
		int[] tipota= {currentPos,0};       // ������� ��� �������� �� ���� ��� ������ ��� �� �����
		Node c = new Node(null, 0,tipota ,board,0); // ���������� ���� ���������� ��� ������ �����
		createMySubtree(c,1,currentPos, opponentCurrentPos);
		int bestmove = chooseMinMaxMove(c,currentPos, opponentCurrentPos); // � �������� ������, � ����� ���������� ��� �� chooseMinMaxMove �� �������� �� ��������� ��� ������ ��� ������ ��� �� ���� ��� ������ ��� ��� ���������.
		int [] result = move(currentPos, bestmove);  // ������������ ���� ������ result � ������� ��� ���������� � move �� ����� ��� ������������ ���� ��� ���� � ������� ��� ��� �������� ������(�����) ��� ������ �� �����.
		int [] arraypath = new int [7];   // ��� ���������� arraypath ��� �� ��������� ��� path, ���� �� ��������� �� ������������
		
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

	// ��������� ����������� ��� ������� ��� ������
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
			Board board1 = new Board(parent.getNodeBoard());			// ���������� board �� ������ �� board ��� ������
			Player testPlayer =  new Player(0, "xristos", 0, board1);   // ���� test player ��� ������ ��� board1, ���� ��� ������� �������� �� ����� ��� board1 ��� ��� ��� board, ��� �� �� ������� �� ������.
			int [] array1= testPlayer.move(currentPos, j);				// ������� ���� ������ ������������ � ������� ��� ���������� � move, �� �������� ��� ������ ��� ������ ��� �� �����, ������� ��������� ���� ��� ���������� ������.
			int [] array2 = new int [] {array1[0], j};					//  ������� ���� ������ ������������ ������� �� �� ������ ��� ������ ��� �� ���� ��� ���� ��� ������.
			Node a = new Node(parent, depth, array2, board1,evaluate(currentPos, j, board1));   // ���������� ������-�������
			parent.getChildren().add(a);
			createOpponentSubtree(a,depth+1,array1[0],opponentCurrentPos,evaluate(currentPos, j, board1));	
		}	
	}
	
	// ��������� ����������� ��� ������� ��� ��������� ������
	void createOpponentSubtree(Node parent, int depth, int currentPos, int opponentCurrentPos, double parentEval) {
		int av=0;  						// available movements
		for(int i=1; i<7; i++) {
			if((board.getN()*board.getM())- opponentCurrentPos >=  i) {
				av=av+1;
			}
		}
		for(int j=0; j<av; j++) {
			Board board2 = new Board(parent.getNodeBoard());   			// ���������� board �� ������ �� board ��� ������
			Player testPlayer =  new Player(0, "makis", 0, board2);		// ���� test player ��� ������ ��� board1, ���� ��� ������� �������� �� ����� ��� board1 ��� ��� ��� board, ��� �� �� ������� �� ������.
			int [] array3= testPlayer.move(opponentCurrentPos, j);		// ������� ���� ������ ������������ � ������� ��� ���������� � move, �� �������� ��� ������ ��� ������ ��� �� �����, ������� ��������� ���� ��� ���������� ������.
			int [] array4 = new int [] {array3[0], j};					// ������� ���� ������ ����������� ������� �� �� ������ ��� ������� ��� �� ���� ��� ���� ��� ������.
			Node b = new Node(parent, depth, array4, board2,evaluate(opponentCurrentPos, j, board2));  // ���������� ������ �������
			parent.getChildren().add(b);
			createMySubtree(b,depth+2,currentPos,array3[0]);
		}	
			
	}
	
	//��������� �� ���������� ��� ������ min max player
	
	void statistics() {	
		int s, l, r, b;
		int vSnakes = 0; //���������� �� �������� �� ������ ������.
		int vLadders = 0; //���������� �� �������� �� ���� ������.
		int vRapples = 0; //���������� �� �������� �� ������� ����.
		int vBapples = 0; //���������� �� �������� �� ����� ����.
		
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