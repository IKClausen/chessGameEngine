 package engine.pieces;

import java.util.Collection;

import engine.Alliance;
import engine.board.Board;
import engine.board.Move;


public abstract class Piece {
	 protected final PieceType pieceType; 
	 protected final int piecePosition;  
	 protected final Alliance pieceAlliance; 
	 protected final boolean isFirstMove; 
	 private final int cachedHashCode; 
	    
	    Piece(final PieceType pieceType, 
	    	  final int piecePosition,
	    	  final Alliance pieceAlliance, 
	    	  final boolean isFirstMove){
	    
	    	
	    this.pieceType = pieceType; 	
	    this.piecePosition = piecePosition; 
	    this.pieceAlliance = pieceAlliance; 
	    // TODO - more to add here 
	    this.isFirstMove = isFirstMove; 
	    this.cachedHashCode = computeHashCode(); 
	    
	    }
	    
	    private int computeHashCode() {
	    	int result = pieceType.hashCode();
	    	result = 31 * result + pieceAlliance.hashCode(); 
	    	result = 31 * result + piecePosition; 
	    	result = 31 * result + (isFirstMove ? 1 : 0); 
	    	return result; 
	    }

		@Override
	    public boolean equals(final Object other) {
	    	if(this == other) {
	    		return true; 
	    	}
	    	if(!(other instanceof Piece)) {
	    		return false; 
	    	}
	    	final Piece otherPiece = (Piece) other; 
	    	return piecePosition == otherPiece.getPiecePosition() && pieceType == otherPiece.getPieceType() && 
	    		   pieceAlliance == otherPiece.getPieceAlliance() && isFirstMove == otherPiece.isFirstMove(); 
	    }
	    // possibly needs to include @Override 
	    public int hashcode() {
	    	return this.cachedHashCode; 
	    }
	    
	    public int getPiecePosition() {
	    	return this.piecePosition; 
	    }
	    
	    public Alliance getPieceAlliance() {
	    	return this.pieceAlliance; 
	    }
	    
	    public boolean isFirstMove() {
	    	return this.isFirstMove;
	    }
	    
	    public PieceType getPieceType() {
	    	return this.pieceType; 
	    }
	    
	    public int getPieceValue() {
			return this.pieceType.getPieceValue();
		}
	    
	    
	    
	    // Method for calculating legal-moves 
	    public abstract Collection<Move> calculateLegalMoves(final Board board); 
	    // abstract method that takes in a move and applies to existing piece and return a new piece with updated piece position  
	    public abstract Piece movePiece(Move move); 
	    
	    public enum PieceType{
	    	// Value of piece type based on heuristics 
	    	PAWN("P", 100){
				@Override
				public boolean isKing() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public boolean isRook() {
					// TODO Auto-generated method stub
					return false;
				}
			},
 
	    	KNIGHT("N", 300){
				@Override
				public boolean isKing() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public boolean isRook() {
					// TODO Auto-generated method stub
					return false;
				}
			}, 
 
			BISHOP("B", 300){
				@Override
				public boolean isKing() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public boolean isRook() {
					// TODO Auto-generated method stub
					return false;
				}
             }, 
			
			ROOK("R", 500){
				@Override
				public boolean isKing() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public boolean isRook() {
					// TODO Auto-generated method stub
					return true;
				}
			}, 	    	
			QUEEN("Q", 900){
				@Override
				public boolean isKing() {
					return false;
				}

				@Override
				public boolean isRook() {
					return false;
				}
			}, 	    	
			KING("K", 10000){
				@Override
				public boolean isKing() {
					return true;
				}

				@Override
				public boolean isRook() {
					return false;
				}
			};     	
			
			private String pieceName; 
			private int pieceValue; 
	    	
	    	PieceType(final String pieceName, final int pieceValue){
	    		this.pieceName = pieceName;
	    		this.pieceValue = pieceValue; 
	    	}
	    	@Override
	    	public String toString() {
	    		return this.pieceName;
	    	}
	    	
	    	public int getPieceValue() {
	    		return this.pieceValue;  
	    	}
	    	
	    	public abstract boolean isKing(); 
	    	
	    	public abstract boolean isRook(); 
	    		
	    	}

	    }
	    

