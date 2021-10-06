package engine.board;

import engine.pieces.Piece;

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

// Board is immuatable - board builder creates new board no mutation of boards to execute moves. 
 public abstract Board execute();   
 
 
  public static final class MajorMove extends Move {

	public MajorMove(final Board board, 
			  final Piece movedPiece,
			  final  int destinationCoordinate) {
		super(board, movedPiece, destinationCoordinate);
	   }

	@Override
	public Board execute() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	} 
	
  }
  
}

