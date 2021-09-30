package engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;

import engine.Alliance;
import engine.board.Board;
import engine.board.BoardUtils;
import engine.board.Move;
import engine.board.Tile;

public class Queen extends Piece {

	private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-9,-8,-7,-1,1,7,8,9}; 
	
	
	public Queen(Alliance pieceAlliance, int piecePosition) {
		super(PieceType.QUEEN, piecePosition, pieceAlliance);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board){
		
		
		final List<Move> legalMoves = new ArrayList<>(); 
		for(final int candidateCoordinateOffset: CANDIDATE_MOVE_VECTOR_COORDINATES) {
			
			int candidateDestinationCoordinate = this.piecePosition; 
			while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
		
				
				
				if(isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) ||
						isEighthColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)) {
					break; 
				}
				
				
				candidateDestinationCoordinate += candidateCoordinateOffset; 
				if(BoardUtils.isValidTileCoordinate(candidateCoordinateOffset)) {
					// if we don't go about of bounds  get tile 
					final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate); 
					// if the tile is not occupied add to legals moves list. 
					if(!candidateDestinationTile.isTileOccupied()){
						legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate)); // needs parameters 
					}else {
						// if the location is occupied 
						final Piece pieceAtDestination = candidateDestinationTile.getPiece(); 
						final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
						
						// if alliance is not equal = enemy 
						if(this.pieceAlliance != pieceAlliance) {
							legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination)); // needs parameters 
						}
						 break; 
					  }
				    }
			      }
	            } 
		         return ImmutableList.copyOf(legalMoves); 
	   }
         
	   @Override
	   public String toString() {
		   return PieceType.QUEEN.toString();
	   }
	
	   private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        	  return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == 1 || candidateOffset == -9 || candidateOffset == 7); 
         }
	   
	   private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
     	  return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 1 || candidateOffset == 9); 
      }

}
