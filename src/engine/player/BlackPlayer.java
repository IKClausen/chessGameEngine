package engine.player;

import java.util.Collection;

import engine.Alliance;
import engine.board.Board;
import engine.board.Move;
import engine.pieces.Piece;
 

public class BlackPlayer extends Player {

	public BlackPlayer(Board board, 
			Collection<Move> whiteStandardLegalMoves,
			Collection<Move> blackStandardLegalMoves) {
		// Black players 
		super(board, blackStandardLegalMoves, whiteStandardLegalMoves); 

	}

	@Override
	public Collection<Piece> getActivePieces() {
		// TODO Auto-generated method stub
		return this.board.getBlackPieces(); 
	}

	@Override
	public Alliance getAlliance() {
		// TODO Auto-generated method stub
		return Alliance.BLACK; 
	}

	@Override
	public Player getOpponent() {
		// TODO Auto-generated method stub
		return null;
	}

}
