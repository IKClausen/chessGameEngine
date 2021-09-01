package engine;

import engine.board.Board;

public class Jchess {
	
	public static void main(String []args) {
		
		Board board = Board.createStandardBoard(); 
		
		System.out.println(board);
	}

}
