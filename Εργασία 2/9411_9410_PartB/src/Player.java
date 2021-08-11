//���������� ��������� 9411 ekalampv@ece.auth.gr 6981751861 ������ ������ 9410 elpidakelesi@ece.auth.gr 6985938322
//� ����� player �������������� ���� ������� ��� ����������
public class Player {
	int playerId;
	String name;
	int score;
	Board board;
	
	
	public Player(){
		playerId = 0;
		name = "";
		score = 0;
	}
	
	public Player(int pi, String na, int sc, Board b){
		playerId=pi;
		name=na;
		score=sc;
		board=b;
	}
	
	public void setPlayerId(int pi) {
		playerId=pi;
	}
	
	public void setName(String na) {
		name=na;
	}
	
	public void setScore(int sc) {
		score=sc;
	}
	public void setBoard(Board b) {
		board=b;	
	}
	
	public int getPlayerId() {
		return playerId;
	}
	
	public String getName() {
		return name;
	}
	
	public int getScore() {
		return score;
	}
	public Board getBoard() {
		return board;
	}
	
//� ������� move �������������� ��� ������ ��� ������ ��� ���������� ���� ������ (results) �� ��� ������ ���� ��� ������, ��� ������ ��� �����, ��� ������,
//��� ��� ������ ��� ���� ���������� � ������� ���� ������ ���. � ��������� pos ����������� ��� ���� ��� ������. � ��������� check ��������������� ��� ��� ����������
//��� do-while ��� ��� ����� ������ ��� ������.	
	public int[]move(int id, int die){	
	int[] results = new int[5];
	int pos = id + die;
	boolean check;
	
	do	{
		check=false;
		for(int w=0; w<board.apples.length; w++) {
			if(pos == board.apples[w].getAppleTileId()) {
				System.out.println("Molis efage ena milo o paiktis "+ getName());
				setScore(getScore() + board.apples[w].getPoints());
				if(board.apples[w].getColor()=="Red")
					results[3]++;
				else
					results[4]++;
			}
		}
		
		for(int k=0; k<board.snakes.length; k++) {	
			if(pos==board.snakes[k].getHeadId()) {
				check=true;
				System.out.println("MOLIS SAS EFAGE ENA FIDI...SSHSHSHHSHS " + getName());
				results[1]++;
				pos = board.snakes[k].getTailId();
			}	
			if(check==true)
				continue;
		}
		
		
		for(int k=0; k<board.ladders.length; k++) {
			if(pos == board.ladders[k].getDownStepId()) {
				check=true;
				System.out.println("MPRAVO! MOLIS ANEVHKATE SE MIA SKALA!! "+ getName());
				results[2]++;
				pos = board.ladders[k].getUpStepId();
				board.ladders[k].setUpStepId(0);
				board.ladders[k].setDownStepId(0);
				board.ladders[k].setBroken(true);
			}
		}
		
	}
	while(check==true); 	
	results[0]=pos;
	return results;
		

	}
}