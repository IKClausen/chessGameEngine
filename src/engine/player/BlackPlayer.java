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
	protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals, 
			                                        final Collection<Move> opponentsLegals) {
		final List<Move> kingCastles = new ArrayList<>(); 
		//Calculating Black Kings king side castle (E1 to G1)    
		   if(this.playerKing.isFirstMove() && !this.isInCheck()) {
			  if(!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()) {
				 final Tile rookTile = this.board.getTile(7); 
				 if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
				     if(Player.calculateAttacksOnTile(5, opponentsLegals).isEmpty() &&
				        Player.calculateAttacksOnTile(6, opponentsLegals).isEmpty() &&
				        rookTile.getPiece().getPieceType().isRook()){				    				    	
					 kingCastles.add(new KingSideCastleMove(this.board, 
                                                                 this.playerKing, 
                                                                 6, // destination coordinate of King 						          
                                                                 (Rook)rookTile.getPiece(), 
                                                                 rookTile.getTileCoordinate(), 
                                                                 5)); //destination coordinate of Rook 
				     }
				 }	
			}
			
			if(!this.board.getTile(1).isTileOccupied() && 
			   !this.board.getTile(2).isTileOccupied() &&
			   !this.board.getTile(3).isTileOccupied()) {
			  final Tile rookTile = this.board.getTile(0); 
			  if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() &&
			     Player.calculateAttacksOnTile(2, opponentsLegals).isEmpty()    &&
			     Player.calculateAttacksOnTile(3, opponentsLegals).isEmpty()    &&
			     rookTile.getPiece().getPieceType().isRook()) {
				  //TODO ADD CASTLE MOVES! 
				  kingCastles.add(new QueenSideCastleMove(this.board, 
                                                               this.playerKing, 2, 
                                                               (Rook)rookTile.getPiece(), 
                                                               rookTile.getTileCoordinate(), 
                                                               3)); 
			  }
			}
		}
		
		return ImmutableList.copyOf(kingCastles);
	}

}
