package engine.board;

import engine.board.Board.Builder;
import engine.pieces.Piece;
//Creates and returns new boards based on executing a move on a piece 
public abstract class Move {
	
	// Constructor that keeps track of board 
	final Board board;  
	final Piece movedPiece; 
	final int destinationCoordinate; 
	
 private Move(final Board board, 
    	 final Piece movedPiece,
    	 final int destinationCoordinate){
	     this.board = board; 
	     this.movedPiece = movedPiece; 
	     this.destinationCoordinate = destinationCoordinate;
}   
 
 // Move has a piece + tile coordinate and destination it gets moved to  
public int getDestinationCoordinate() {
	return this.destinationCoordinate; 
			
}

public Piece getMovedPiece() {
	return this.movedPiece; 
}

// Board is immutable - board builder creates new board no mutation of boards to execute moves. 
 public abstract Board execute();   
 
 
  public static final class MajorMove extends Move {

	public MajorMove(final Board board, 
			  final Piece movedPiece,
			  final  int destinationCoordinate) {
		super(board, movedPiece, destinationCoordinate);
	   }

	@Override
	public Board execute() {
		//Board builder to create new board traverse incoming boards current player pieces and for each piece that isn't the moved piece stay the same
		final Board.Builder builder = new Builder(); 
		
		for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
			//TO DO hashcode and equals for pieces 
			if(!this.movedPiece.equals(piece)) {
				builder.setPiece(piece); 
			}
		}
		// Same for the opponent players pieces as above - no moved piece 
		 for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
			 builder.setPiece(piece);
		 }
		 // move and set the moved piece - switch the incoming move maker 
		 builder.setPiece(this.movedPiece.movePiece(this)); 
		 builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance()); 
		 return builder.build();
	}  
 }
  
  public static final class AttackMove extends Move {
	  
	public final Piece attackedPiece; 

	public AttackMove(final Board board,
			   final Piece movedPiece,
			   final int destinationCoordinate,
			   final Piece attackedPiece) {
		
		  super(board, movedPiece, destinationCoordinate);
		  this.attackedPiece = attackedPiece; 
	}

	@Override
	public Board execute() {
		return null;
	} 
	
  }
  
}

