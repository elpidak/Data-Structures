//���������� ��������� 9411 ekalampv@ece.auth.gr 6981751861 ������ ������ 9410 elpidakelesi@ece.auth.gr 6985938322

//� ����� HeuristicPlayer �������������� ��� ������ ��� ���������� ��� ������ �� ���������� ��� ���� ��� ���������, path ����� arraylist.

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

// � ������� evaluate �������������� ��� ���������� ��� ������ ��� ������ ��� ���������� ���� ��� ���������� ����� ����� ���������� ��� ���������� ������: (������)* 0,35 + (������)* 0,65.
//��������� �� ���������� check, ����� Boolean ��� ��������������� ��� �� ����� do-while, ev, ����� double, ���� ������������ � ���������� ��� �������, po, ����� int, ��� ����������� ����� 
//������� ��� ������ ��� �� ������������ ������ ��� finalPos, ����� int,  ��� ����� � ������ ���� ��� ������ ��� ������������ �����.
	
	double evaluate(int currentPos, int dice) {	
	
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
	
//��� ������ getNextMove ���������� � ����� �� ��� �������� ���������� �������� � ������� �� ������ �����, ������������� �� ���������� �������� ��� ��������� "path" ��� ������������ � ������ ���� 
//��� ������. O�������� ��� ������� �������� Round, move, ���� ������� moves ��������� double, � ��������� evaluation, ����� double ��� � ������� ��������� finaldie.
	
	int getNextMove(int currentPos) {
		
		int[] Round = new int[7];
		int[] move; // ������������ � ������� ��� ���������� � ��������� move ��� ������ player 
		int finaldie = 1; //� ����� ��� ����������
		double evaluation;
		double[] moves = new double[7]; //������������� ���� �� ������� �������� ��� ������
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
		evaluation = evaluate(currentPos, finaldie);//� ���������� ��� ����������� ������
		Round[1] = (int) ((evaluation - move[0]*0.65) / 0.35); // pontoi
		Round[2] = (move[0] - currentPos); // vhmata
		Round[3] = (move[3] + move[4]); // mhla 
		Round[4] = move[1]; // fidia 
		Round[5] = move[2]; // skales
		Round[6] = move[3]; //������� ����
		path.add(Round);
		return move[0];
	}

//��� ������ statistics, ������������ �������� ��� ��� ���� ���� ��� ���������� ��� ���� ��� �������� ��� ������ �� �� ������� ��� ���������� path. �� �������� ���������� s, l, r, b ����������������
//��� �� ������������ �� ���� ���� � ������� ��� ������ ��� ������ ��� �������� ��� ��� ������ ����� (�������� ������������ ���� ��������� path). �� �������� ���������� vSnakes, vLadders, vRapples, 
//vBapples ������������ ��� ���������� �������� ��� ���������� ��� ��� ������.
	
	
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