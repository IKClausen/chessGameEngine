package engine.player;

import java.util.Collection;

import engine.Alliance;
import engine.board.Board;
import engine.pieces.King;
import engine.pieces.Piece;
import engine.board.Move;

public abstract class Player {
	
	protected final Board board; 
	protected final King playerKing; 
	protected final Collection<Move> legalMoves; 
	
	Player(final Board board, 
		   final Collection<Move> legalMoves, 
		   final Collection<Move> opponentMoves){
		this.board = board; 
		this.playerKing = establishKing(); 
		this.legalMoves = legalMoves; 
	}

	private King establishKing() {
		for(final Piece piece : getActivePieces()) {
			if(piece.getPieceType().isKing()) {
				return (King) piece; 
			}
		}
		// if king can't be found chess board/state is wrong 
		throw new RuntimeException("NO KING - Board Not Valid"); 
		
	}
	// implementing helper routines - and polymorphic abstract methods 
	// TODO implement logic in methods!!! 
	public boolean isMoveLegal(final Move move) {
		return this.legalMoves.contains(move); 
	}
	
	public boolean isInCheck() {
		return false; 
	}
	
	public boolean isInStaleMate() {
		return false; 
	}
	
	public boolean isCastled() {
		return false; 
	}
	
	public MoveTransition makeMove(final Move move) {
		return null; 
	}
	
	public abstract Collection<Piece> getActivePieces(); 
	public abstract Alliance getAlliance(); 
	public abstract Player getOpponent(); 
	

}
