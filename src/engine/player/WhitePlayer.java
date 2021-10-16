package engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;

import engine.Alliance;
import engine.board.Board;
import engine.board.Move;
import engine.board.Move.KingSideCastleMove;
import engine.board.Move.QueenSideCastleMove;
import engine.board.Tile;
import engine.pieces.Piece;
import engine.pieces.Rook;

public class WhitePlayer extends Player {

	public WhitePlayer(final Board board, 
			final Collection<Move> whiteStandardLegalMoves,
			final Collection<Move> blackStandardLegalMoves) {
		
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
	
	@Override
	protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals,final Collection<Move> opponentsLegals) {
		final List<Move> kingCastles = new ArrayList<>(); 
		//Calculating White Kings king side castle (E1 to G1)    
		   if(this.playerKing.isFirstMove() && !this.isInCheck()) {
			  if(!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()) {
				 final Tile rookTile = this.board.getTile(63); 
				 if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
				     if(Player.calculateAttacksOnTile(61, opponentsLegals).isEmpty() &&
				        Player.calculateAttacksOnTile(62, opponentsLegals).isEmpty() &&
				        rookTile.getPiece().getPieceType().isRook()){
					 kingCastles.add(new KingSideCastleMove(this.board, 
							                                     this.playerKing, 
							                                     62, // destination coordinate of King 						          
							                                     (Rook)rookTile.getPiece(), 
							                                     rookTile.getTileCoordinate(), 
							                                     61)); //destination coordinate of Rook 
				     }
				 }	
			}
			
			if(!this.board.getTile(59).isTileOccupied() && 
			   !this.board.getTile(58).isTileOccupied() &&
			   !this.board.getTile(57).isTileOccupied()) {
			  final Tile rookTile = this.board.getTile(56); 
			  if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
				  //TODO ADD CASTLE MOVES! 
				  kingCastles.add(new QueenSideCastleMove(this.board, 
						                                       this.playerKing, 58, 
						                                       (Rook)rookTile.getPiece(), 
						                                       rookTile.getTileCoordinate(), 
						                                       59)); 
			  }
		   }
		}
		
		return ImmutableList.copyOf(kingCastles);
	}
}
