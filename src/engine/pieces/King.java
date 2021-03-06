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

public class King extends Piece {
	
	private final static int[] CANDIDATE_MOVE_COORDINATE = {-9,-8,-7,-1,1,7,8,9}; 

	public King(final Alliance pieceAlliance, final int piecePosition) {
		super(PieceType.KING, piecePosition, pieceAlliance, true);

	}
	
	public King(final Alliance pieceAlliance, final int piecePosition, final boolean isFirstMove) {
		super(PieceType.KING, piecePosition, pieceAlliance, isFirstMove);

	}

	@Override
	public Collection<Move> calculateLegalMoves(Board board) {
		
		
		final List<Move> legalMoves = new ArrayList<>(); 
		
		for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
			
			final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;  
			
			if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) || 
			   isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)){
			continue; 
			}
			
			
			if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
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
				}
			}
		} 
	
		return ImmutableList.copyOf(legalMoves);
	}
	   //Creates new identical piece in new piece position 
	   @Override
	   public King movePiece(Move move) {
		// TODO Auto-generated method stub
		   return new King(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	   }
	
	   @Override
	   public String toString() {
		   return PieceType.KING.toString();
	   }
	
	// Methods for edge cases where above approach doesn't apply - column exclusions 
     private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
     	// a way to capture all tile coordinates that correspond to a given row and given column 
     	return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == -1 ||
     			candidateOffset == 7); 
     }
     private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
     	return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 1 || 
     			candidateOffset == 9); 
     }

}
