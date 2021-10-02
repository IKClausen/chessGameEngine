package engine.player;

import engine.board.Board;
import engine.board.Move;

// A class that represents the transition of one move from one board to another 
public class MoveTransition {
	
	private final Board transitionBoard; 
	private final Move move; 
	private final MoveStatus moveStatus; // Specifies if move was successful or not - and the reason  

	public MoveTransition(final Board transitionBoard, 
		                  final Move move, 
		                  final MoveStatus moveStatus) {
		this.transitionBoard = transitionBoard; 
		this.move = move; 
		this.moveStatus = moveStatus; 
	}
	
	public MoveStatus getMoveStatus() {
		return this.moveStatus; 
	}

}
