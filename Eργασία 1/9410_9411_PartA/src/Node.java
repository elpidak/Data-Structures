//Καλαμπούκα Ευαγγελία 9411 ekalampv@ece.auth.gr 6981751861 Κελέση Ελπίδα 9410 elpidakelesi@ece.auth.gr 6985938322
// Η κλάση Node υλοποιεί ένα δέντρο διαθέσιμων κινήσεων.

import java.util.ArrayList;

public class Node {
	Node parent;                   
	ArrayList<Node>children;	   
	int nodeDepth;				   
	int[] nodeMove;				     
	Board nodeBoard ;
	double nodeEvaluation ;
	
	public Node() {
		children =  new ArrayList<Node>();
		nodeDepth=0;
		nodeEvaluation = 0;		
	}
	
	public Node(Node p, int nd,int[] nm, Board nb, double ne) {
		parent = p;
		children =  new ArrayList<Node>();
		nodeDepth =nd;
		nodeMove= nm;
		nodeBoard = nb;
		nodeEvaluation = ne;	
		
	}
	
	public void setParent(Node p) {
		parent = p;
	}
	
	public void setNodeDepth (int nd) {
		nodeDepth =nd;
	}
	
	public void setNodeMove(int nm, int w) {
		nodeMove[nm] = w;
	}
	
	public void setNodeBoard(Board nb) {
		nodeBoard = nb;
	}
	
	public void setNodeEvaluation(double ne) {
		nodeEvaluation = ne;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public ArrayList<Node> getChildren(){
		return children;
	}
	
	public int getNodeDepth() {
		return nodeDepth;
	}
	
	public int[] getNodeMove() {
		return nodeMove;
	}
	
	public int getNodeMoveLength() {
		return nodeMove.length;
	}
	
	public Board getNodeBoard() {
		return nodeBoard;
	}
	
	public double getNodeEvaluation() {
		return nodeEvaluation;
	}
	

}
