package engine.player;

import java.util.Collection;

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
	}
	
	public abstract Collection<Piece> getActivePieces(); 

}
