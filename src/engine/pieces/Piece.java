package engine.pieces;

import java.util.Collection;

import engine.Alliance;
import engine.board.Board;
import engine.board.Move;


public abstract class Piece {
	 protected final int piecePosition;  
	 protected final Alliance pieceAlliance; 
	 protected final boolean isFirstMove; 
	    
	    Piece(final int piecePosition, final Alliance pieceAlliance){
	    
	    	
	    	
	    this.piecePosition = piecePosition; 
	    this.pieceAlliance = pieceAlliance; 
	    this.isFirstMove = false; 
	    
	    
	    }
	    
	    public Alliance getPieceAlliance() {
	    	return this.pieceAlliance; 
	    }
	    
	    public boolean isFirstMove() {
	    	return this.isFirstMove;
	    }
	    
	    
	    
	    // Method for calculating legal-moves 
	    public abstract Collection<Move> calculateLegalMoves(final Board board); 
	    

}
