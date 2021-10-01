package engine.player;

import java.util.Collection;

import engine.Alliance;
import engine.board.Board;
import engine.board.Move;
import engine.pieces.Piece;

public class WhitePlayer extends Player {

	public WhitePlayer(Board board, 
			Collection<Move> whiteStandardLegalMoves,
			Collection<Move> blackStandardLegalMoves) {
		
	super(board, whiteStandardLegalMoves, blackStandardLegalMoves); 	
	}

	@Override
	public Collection<Piece> getActivePieces() {
		// TODO Auto-generated method stub
		return this.board.getWhitePieces(); 
	}

	@Override
	public Alliance getAlliance() {
		// TODO Auto-generated method stub
		return Alliance.WHITE;
	}

	@Override
	public Player getOpponent() {
		// TODO Auto-generated method stub
		return this.board.blackPlayer(); 
	}

}
