package engine;

import engine.board.Board;
import gui.Table;

// Driver class - main method 
public class Jchess {
	
	public static void main(String []args) {
		
		Board board = Board.createStandardBoard(); 
		
		System.out.println(board);
		
		Table table = new Table(); 
	}

}


