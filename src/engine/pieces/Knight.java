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
import engine.pieces.Piece.PieceType;

// https://en.wikipedia.org/wiki/Knight_(chess)

public class Knight extends Piece {
	
// Legal move based on coordinate and a knight with most possible options. 
private final int [] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};  	
	
	
	    //Constructor matching super 
        public Knight(final Alliance pieceAlliance, final int piecePosition) {
		super(piecePosition, pieceAlliance);
	}

@Override 
public Collection<Move> calculateLegalMoves(final Board board){
	
	final List<Move> legalMoves = new ArrayList<>(); 
	
	
	 for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES ) {
		 // Applying offset to current position variable 
		 final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset; 
		
				if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
					
				 if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) || 
				   isSecondColumnExclusion(this.piecePosition, currentCandidateOffset) ||  
				   isSeventhColumnExclusion(this.piecePosition, currentCandidateOffset)|| 
				   isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) 
				 {
					 
			      continue; 
				 }	
				 
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

            @Override
            public String toString() {
	        return PieceType.KNIGHT.toString();
            }
   
     // Methods for edge cases where above approach doesn't apply - column exclusions 
            private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
            	// a way to capture all tile coordinates that correspond to a given row and given column 
            	return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10 ||
            			candidateOffset == 6 || candidateOffset == 15); 
            }
            private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset) {
            	return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset == -10 || candidateOffset == 6); 
            }
            private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset) {
            	return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset == -6 || candidateOffset == 10); 
            }
            private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
            	return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -15 || candidateOffset == 6 || 
            			candidateOffset == 10 || candidateOffset == 17); 
            }
             
            

}