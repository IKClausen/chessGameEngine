package engine.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

import engine.Alliance;
import engine.pieces.Bishop;
import engine.pieces.King;
import engine.pieces.Knight;
import engine.pieces.Pawn;
import engine.pieces.Piece;
import engine.pieces.Queen;
import engine.pieces.Rook;

public class Board {
	
	private final List<Tile> gameBoard; 
	// Keep track of the black and white tiles also 
	private final Collection<Piece> whitePieces; 
	private final Collection<Piece> blackPieces; 
	

	public Board(Builder builder) {
		this.gameBoard = createGameBoard(builder); 
		this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.WHITE); 
		this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.BLACK); 
		
		final Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces); 
		
		final Collection<Move> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces); 
	}

	
	// Method to track active pieces based on alliance 
	private Collection<Piece> calculateActivePieces(List<Tile> gameBoard, Alliance alliance) {
		final List<Piece> activePieces = new ArrayList<>(); 
		for(final Tile tile : gameBoard) {
			if(tile.isTileOccupied()) {
				final Piece piece = tile.getPiece();
				if(piece.getPieceAlliance() == alliance) {
					
				}
				
		    }
		}
		return ImmutableList.copyOf(activePieces);
	}

	public Tile getTile(int tileCoordinate) {
		return gameBoard.get(tileCoordinate);
	}
	
	
	// Method that populates list of tiles 0-64 representing chess board - loop goes through and gets 
	public static List<Tile> createGameBoard(final Builder builder){
		final Tile[] tiles = new Tile[BoardUtils.NUM_TILES]; 
		for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
			tiles[i] = Tile.createTile(i, builder.boardConfig.get(i)); 
		}
		
		return ImmutableList.copyOf(tiles); 
		
	}
	
	// Creates initial chess positions 
	public static Board createStandardBoard(){
		final Builder builder = new Builder(); 
		// Team black 
		builder.setPiece(new Rook(Alliance.BLACK, 0)); 
		builder.setPiece(new Knight(Alliance.BLACK, 1));
		builder.setPiece(new Bishop(Alliance.BLACK, 2));
		builder.setPiece(new Queen(Alliance.BLACK, 3));
		builder.setPiece(new King(Alliance.BLACK, 4));
		builder.setPiece(new Bishop(Alliance.BLACK, 5));
		builder.setPiece(new Knight(Alliance.BLACK, 6));
		builder.setPiece(new Rook(Alliance.BLACK, 7));
		builder.setPiece(new Pawn(Alliance.BLACK, 8));
		builder.setPiece(new Pawn(Alliance.BLACK, 9));
		builder.setPiece(new Pawn(Alliance.BLACK, 10));
		builder.setPiece(new Pawn(Alliance.BLACK, 11));
		builder.setPiece(new Pawn(Alliance.BLACK, 12));
		builder.setPiece(new Pawn(Alliance.BLACK, 13));
		builder.setPiece(new Pawn(Alliance.BLACK, 14));
		builder.setPiece(new Pawn(Alliance.BLACK, 15));
		// Team white 
		builder.setPiece(new Rook(Alliance.WHITE, 48)); 
		builder.setPiece(new Knight(Alliance.WHITE, 49));
		builder.setPiece(new Bishop(Alliance.WHITE, 50));
		builder.setPiece(new Queen(Alliance.WHITE, 51));
		builder.setPiece(new King(Alliance.WHITE, 52));
		builder.setPiece(new Bishop(Alliance.WHITE, 53));
		builder.setPiece(new Knight(Alliance.WHITE, 54));
		builder.setPiece(new Rook(Alliance.WHITE, 55));
		builder.setPiece(new Pawn(Alliance.WHITE, 56));
		builder.setPiece(new Pawn(Alliance.WHITE, 57));
		builder.setPiece(new Pawn(Alliance.WHITE, 58));
		builder.setPiece(new Pawn(Alliance.WHITE, 59));
		builder.setPiece(new Pawn(Alliance.WHITE, 60));
		builder.setPiece(new Pawn(Alliance.WHITE, 61));
		builder.setPiece(new Pawn(Alliance.WHITE, 62));
		builder.setPiece(new Pawn(Alliance.WHITE, 63));
		// White makes first move 
		
		builder.setMoveMaker(Alliance.WHITE); 
		
		return builder.build();
		
	}
	
	public static class Builder {
		
		// Mapping tile ID to given piece on the tile ID 
		Map<Integer, Piece> boardConfig; 
		Alliance nextMoveMaker; 
		
		// exposing builder as public 
		public Builder() {
			
		}
		
		// setting properties of builder and returning builder 
		public Builder setPiece(final Piece piece) {
			this.boardConfig.put(piece.getPiecePosition(), piece); 
				return this; 
				
			}
		
		public Builder setMoveMaker(final Alliance nextMoveMaker) {
			this.nextMoveMaker = nextMoveMaker; 
			return this; 
		}
		
		public Board build(){
			return new Board(this); 
			
		}
	}

}
