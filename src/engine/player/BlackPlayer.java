package engine.player;

import java.util.Collection;

import engine.board.Board;
import engine.board.Move;
import engine.pieces.Piece;
import engine.player.Player; 

public class BlackPlayer extends Player {

	public BlackPlayer(Board board, 
			Collection<Move> whiteStandardLegalMoves,
			Collection<Move> blackStandardLegalMoves) {
		
		super(board, blackStandardLegalMoves, whiteStandardLegalMoves); 

	}

	@Override
	public Collection<Piece> getActivePieces() {
		// TODO Auto-generated method stub
		return this.board.getBlackPieces(); 
	}

}
