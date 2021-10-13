package engine.board;

import engine.board.Board.Builder;
import engine.pieces.Piece;
//Creates and returns new boards based on executing a move on a piece 
public abstract class Move {
	
	// Constructor that keeps track of board 
	final Board board;  
	final Piece movedPiece; 
	final int destinationCoordinate; 
	
	public static final Move NULL_MOVE = new NullMove();
	
 private Move(final Board board, 
    	 final Piece movedPiece,
    	 final int destinationCoordinate){
	     this.board = board; 
	     this.movedPiece = movedPiece; 
	     this.destinationCoordinate = destinationCoordinate;
}   
 
public int getCurrentCoordinate() {
	return this.getMovedPiece().getPiecePosition();
}
 // Move has a piece + tile coordinate and destination it gets moved to  
public int getDestinationCoordinate() {
	return this.destinationCoordinate; 
			
}

public Piece getMovedPiece() {
	return this.movedPiece; 
}

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
/*Board is immutable - board builder creates new board no mutation of boards to execute moves. 
 public abstract Board execute(); */   
 
 
  public static final class MajorMove extends Move {

	public MajorMove(final Board board, 
			  final Piece movedPiece,
			  final  int destinationCoordinate) {
		super(board, movedPiece, destinationCoordinate);
	   }
 }
  
  public static class AttackMove extends Move {
	  
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
  
  public static final class PawnMove extends Move {

	public PawnMove(final Board board, 
			        final Piece movedPiece,
			        final  int destinationCoordinate) {
		super(board, movedPiece, destinationCoordinate);
	   }
   }
  public static class PawnAttackMove extends AttackMove {

	public PawnAttackMove(final Board board, 
			              final Piece movedPiece,
			              final  int destinationCoordinate,
			              final Piece attackedPiece) {
		
		super(board, movedPiece, destinationCoordinate, attackedPiece);
	   }
   }
  
  public static final class PawnEnPassantAttackMove extends PawnAttackMove {

	public PawnEnPassantAttackMove(final Board board, 
			                       final Piece movedPiece,
			                       final  int destinationCoordinate,
			                       final Piece attackedPiece) {
		
		super(board, movedPiece, destinationCoordinate, attackedPiece);
	   }
   }
  public static final class PawnJump extends Move {

	public PawnJump(final Board board, 
			        final Piece movedPiece,
			        final  int destinationCoordinate) {
		
		super(board, movedPiece, destinationCoordinate);
	   }
   }
  
  static abstract class caslteMove extends Move {

	public caslteMove(final Board board, 
			          final Piece movedPiece,
			          final  int destinationCoordinate) {
		super(board, movedPiece, destinationCoordinate);
	   }
   }
  
  public static final class KingSideCastleMove extends Move {

	public KingSideCastleMove(final Board board, 
			                  final Piece movedPiece,
			                  final  int destinationCoordinate) {
		super(board, movedPiece, destinationCoordinate);
	   }
   } 
  
  public static final class QueenSideCastleMove extends Move {

		public QueenSideCastleMove(final Board board, 
				                   final Piece movedPiece,
				                   final  int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
		   }
	   } 

  public static final class nullMove extends Move {

		public nullMove() {
			super(null,null,-1);
		   }
		@Override 
		public Board execute() {
			throw new RuntimeException("Cannot execute null move!");
			
		}
	   } 
   //Factory class with convenience method 
  public static class MoveFactory{
	  private MoveFactory() {
		  throw new RuntimeException("Not instantiable!"); 
	  }
	  public static Move createMove(final Board board, 
			                        final int currentCoordinate, 
			                        final int destinationCoordinat) {
		  for(final Move move : board.getAlllegalMoves()) {
			  if(move.getCurrentCoordinate()== currentCoordinate &&
			     move.getDestinationCoordinate()== destinationCoordinate) {
				  return move; 
			  }
		  }
		  return NULL_MOVE;
	  }
   }
  
}

