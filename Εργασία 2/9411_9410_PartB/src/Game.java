//���������� ��������� 9411 ekalampv@ece.auth.gr 6981751861 ������ ������ 9410 elpidakelesi@ece.auth.gr 6985938322

//� ����� game 

//� ����� Game �������������� �� ��������.

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

	int min_iter=0; // global ��������� ��� ������� ��� ����� ��� ����� � ������� �� ���� ��� �� ���������� � ����� ��� �� ������



	//��������� ��� ������� ���� ������� ������,��� ������ �� �������� ��������������� ��� ����� ��� ���� ����� � ���� ������� (�� ������� ����� �� Id-1 ��� ������� ��� �� ������ ����� � �����-1, ����� �� ������� �� Id 2 ����� 4 �� ���� ���� ��� ������� [1][3]

	//� check �������� ��� ����� ���� ����� ��� ����� �����, ����� �� ��� 2 ����� ��� ����� ����� 4 �� ��� ��� �������� [3] ��� check 2

	//�����, � rowsToCheck ��� ���������� � ��������� �������� ��� ������ ��� ������ ���� ��� ���� ��� ��� ����� �� ���� ����� ��� ���� �����

	public ArrayList<Integer> checkTable(int[][] binaryTable) {

		ArrayList<Integer> rowsToCheck = new ArrayList<Integer>();

		int check[] = new int[6]; // ������� ��� ���� ��� ����� ���� ����� ����� ����� ��� ������������ �����

		for(int i=0; i<6;i++) {

			for(int j=0; j<6; j++) {

				check[i] += binaryTable[j][i]; }}

		for(int i=0; i<6; i++) {

			if(check[i] > 1) rowsToCheck.add(i);

		}

		return rowsToCheck; 

	}

	

	//��������� ��� ������ ��� �������� ���� ���� ����� ��� ��������� ������, ������ ������ ��� ����� ��� ������ �� �������, ������������ ��� ��������� ������, 

	//���� ����� ��� ������ ��� ������ ������� ������ ��������� �� �������� ���� ��� ������

	//�� ���� ��� ��� ������� ��� ���� ��� �� ����� ����� ��� ���� �����, ���� ����� �� ����������� �� ����,

	//����� 1 �� ���������� �������� ��� ������,���� �� ���������� � checkTable, ������� �� itter ���� 1 , 

	//��� ����� ��� �����  first �� ������ �� id ��� ������, ��� value �� ���������� �����,  ��� ��� �������� ��������, 

	//���� �� �� ������� ����� ��� ��� ������ ������ ��� ����� �� ���������� � ����� ��� �� ������ � ������� �����.

	public void checkRow(int row, int iter, int[][] binaryTable) {

		int check=0; // �������� 

		int newTable[][] = new int[6][6]; // ������� ���� ������ ������������ � array ���� ���� ��� ���� ������ ������� ����� �� ������ ������ ��� ���������� �����.

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

	//��������� ���������� ��� ������ �� ��� ����� �� ������� �� �������. 

	//� ��������� ���� ���������� ���� ����� �� �� ids ��� ������� ������������ �� ���� ��� ����� � ��� ������ ���� ������

	//����� ��� checkTable ��� ��� checkRow ��� ���� ������� �� ����� �� ������� �� id ��� ������� ��� values ��� ��������� � �������� ������ �����

	//����� ���������� �� ����� ���� �� ��� ���� �������� ���������� �� �������� ���

	//����� ������ ��� value ��� ���� ������ ��� ����� sorted, �� ���������������� ��� ���������������� ��� 10^(-itter) ����� ��� ���� ������

	public Map<Integer,Integer> setTurns(ArrayList<Object> players) {

		

		int array[][] = new int [6][6]; // ������� ���� ����� ������������� �� ������ ������ ��� ������� �� ���� �� id ����

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

		 int counter = 0; // �������� �� ������������ 0, ���� �� ����������� ��'���� ���� �� �������

		 do {

			double m = first.get(0); // ������������ ���� �� ���������� � ����� ��������

			int min_i = 0; // ������������ ��� ���������

			for(int i=0; i<players.size(); i++) {  

				if(first.get(i) < m ) {

					m = first.get(i);

					min_i = i;

				}

			}

			value = Math.round((float)(first.get(min_i)*Math.pow(10,(min_iter*(-1))))); // ��������� ���� �� ������� � ��������������� ��� �� ���������

																						//���� �� ������ ������ ��� ������ �� ������� ����� �� ���������� � ���� ����  

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

		int ro=0; //������������ ��� �����

		Player p1= new Player (1, "maria",0, b3); 

		HeuristicPlayer p2= new HeuristicPlayer (2, "mhtsos", 0, b3, ro);

		

		int array[][]=new int [2][5]; // ������� ���� ����� ������������ � ������� ��� ���������� � move ��� �� id ��� �������

		ArrayList<Object> players = new ArrayList<Object>(); // � ��������� players ����� arraylist �������� ���� ������� �������������� �� ���� �� id ����

		players.add(p1);

		players.add(p2);

		Map<Integer,Integer> b = new LinkedHashMap<Integer,Integer>(); 

		b = g1.setTurns(players); // � ��������� b ����� map ��������� ����� �� ���������� ��� setTurns

		

		int id1=1; // M� �� id1 ��� id2 �������������� � ���� ��� ������ ��� ��������� � ���� �������, ���� �������� ���

		int id2=1; // �� 1 ��������� �� ����, ���� ������ �� ����� ����� ��� ���������� � ����� ��� �� �������

		array[0][0] = id1; // ���� ����� ����� ��� array ������������ � ���� ��� ���� ������ 1 ��� ���� ���������� ��� ������ 2


		array[1][0] = id2; 

		//����������� � ����� ��� ������� �� �������, ������� ��� �� ��� ����� ����� ��� �������� � ������� ����������� ��� ��������� 

		//���� ���� ��� ���� ��� ������ ��� ���������� � ���� � ������� ��� ����� ������� �� 100

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

		//� ��������� ������ ��� ��� player

		double ev1 = array[0][0]*0.65 + p1.getScore()*0.35 ;

		//H ��������� ������ ��� ��� heuristic player

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

					System.out.println("We have tie."); //�� ��������� ��� ����� ���� ���� ���� ��� � ��������� ������ ���� ��������� ���� ������ ��������

				}

			}

		}

		System.out.println("The statistics for the heuristic player are :");

		p2.statistics(); //��������� ��� ���������� ��� ������ heuristic player

		System.out.println();

		System.out.println("*****The Game Is Over*****");

	}



}