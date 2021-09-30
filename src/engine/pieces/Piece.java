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
	    
	    Piece(final PieceType pieceType, 
	    	  final int piecePosition,
	    	  final Alliance pieceAlliance){
	    
	    	
	    this.pieceType = pieceType; 	
	    this.piecePosition = piecePosition; 
	    this.pieceAlliance = pieceAlliance; 
	    this.isFirstMove = false; 
	    
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
	    
	    
	    
	    // Method for calculating legal-moves 
	    public abstract Collection<Move> calculateLegalMoves(final Board board); 
	    
	    public enum PieceType{
	    	
	    	PAWN("P"){
				@Override
				public boolean isKing() {
					// TODO Auto-generated method stub
					return false;
				}
			},
 
	    	KNIGHT("N"){
				@Override
				public boolean isKing() {
					// TODO Auto-generated method stub
					return false;
				}
			}, 
 
			BISHOP("B"){
				@Override
				public boolean isKing() {
					// TODO Auto-generated method stub
					return false;
				}
             }, 
			
			ROOK("R"){
				@Override
				public boolean isKing() {
					// TODO Auto-generated method stub
					return false;
				}
			}, 	    	
			QUEEN("Q"){
				@Override
				public boolean isKing() {
					// TODO Auto-generated method stub
					return false;
				}
			}, 	    	
			KING("K"){
				@Override
				public boolean isKing() {
					// TODO Auto-generated method stub
					return true;
				}
			};     	
			
			private String pieceName; 
	    	
	    	PieceType(final String pieceName){
	    		this.pieceName = pieceName;
	    	}
	    	@Override
	    	public String toString() {
	    		return this.pieceName;
	    	}
	    	
	    	public abstract boolean isKing(); 
	    		
	    	}
	    }
	    

