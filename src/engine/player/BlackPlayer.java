package engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;

import engine.Alliance;
import engine.board.Board;
import engine.board.Move;
import engine.board.Tile;
import engine.pieces.Piece;
 

public class BlackPlayer extends Player {

	public BlackPlayer(final Board board, 
		   final Collection<Move> whiteStandardLegalMoves,
		   final Collection<Move> blackStandardLegalMoves) {
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
		return this.board.whitePlayer(); 
	}

	@Override
	protected Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentsLegals) {
		final List<Move> kingCastles = new ArrayList<>(); 
		//Calculating Black Kings king side castle (E1 to G1)    
		   if(this.playerKing.isFirstMove() && !this.isInCheck()) {
			  if(!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()) {
				 final Tile rookTile = this.board.getTile(7); 
				 if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
				     if(Player.calculateAttacksOnTile(5, opponentsLegals).isEmpty() &&
				        Player.calculateAttacksOnTile(6, opponentsLegals).isEmpty() &&
				        rookTile.getPiece().getPieceType().isRook()){
				    	 
				    	 //TODO ADD CASTLE MOVES!
					 kingCastles.add(null);  
				     }
				 }	
			}
			
			if(!this.board.getTile(1).isTileOccupied() && 
			   !this.board.getTile(2).isTileOccupied() &&
			   !this.board.getTile(3).isTileOccupied()) {
			  final Tile rookTile = this.board.getTile(0); 
			  if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
				  //TODO ADD CASTLE MOVES! 
				  kingCastles.add(null); 
			  }
			}
		}
		
		return ImmutableList.copyOf(kingCastles);
	}

}
