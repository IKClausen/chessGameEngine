package engine.board;

import java.util.HashMap;
import java.util.Map;
import com.google.common.collect.ImmutableMap;

import engine.pieces.Piece;

import java.util.Collections;

    
    
    
	public abstract class Tile {
	    
	  
	  // Declaring variable - protected and final 
	  protected final int tileCoordinate; 
	  
	  private static final Map<Integer, EmptyTile> Empty_Tiles_Cache= createAllPossibleEmptyTiles(); 
		
	  
	  
	  protected static Map<Integer, EmptyTile> createAllPossibleEmptyTiles(){
		  
	    // Map data structure that holds all tiles on the board.  
		  final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>(); 
		  for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
			  
			  emptyTileMap.put(i, new EmptyTile(i)); 
			  
		  }
		  
		  Collections.unmodifiableMap(emptyTileMap); 
          return ImmutableMap.copyOf(emptyTileMap); // Guava's unmodifiable map - so changes can't be made in map. 
	          
	  }
	  
	  
	  // Only tile that can create an empty - otherwise from cache or an occupied tile. 
	  
	   public static Tile createTile(final int tileCoordinate, final Piece piece) {
		      return piece != null ? new OccupiedTile(tileCoordinate, piece) : Empty_Tiles_Cache.get(tileCoordinate); 
	   }
	  
	  // Take in tile coordinate as a parameter 
		  private Tile(final int tileCoordinate){
			  this.tileCoordinate = tileCoordinate;   
		  }
		  
		
		  

		// Key methods to check if file is occupied and to retrieve the piece 
		  public abstract boolean isTileOccupied(); 
		  public abstract Piece getPiece(); 
		  
		  
	      // Subclasses that implement behaviors  
		  public static final class EmptyTile extends Tile {
			  
			  
			  private EmptyTile(final int coordinate){
				  super(coordinate); 
			  }
			  
			  @Override
			  public String toString() {
				  return "-"; 
			  }
			  
			  @Override
			  public boolean isTileOccupied() {
				  return false; 
			  }
			  
			  @Override 
			  public Piece getPiece() {
				  return null; 
			  }
		  }
		  
		  public static final class OccupiedTile extends Tile {
			  
			  private final Piece pieceOnTile; 
			  private OccupiedTile(int tileCoordinate,final Piece pieceOnTile){
				  super(tileCoordinate); 
				  this.pieceOnTile = pieceOnTile;
			  }
			  
			  @Override
			  public String toString() {
				  return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() : 	
						getPiece().toString(); // black pieces show up as lower case white as upper case 
				}
			  
			  @Override 
			  public boolean isTileOccupied() {
				  return true; 
				  
			  }
			  
			  @Override 
			  public Piece getPiece() {
				  return this.pieceOnTile; 
			  }
		  }
	
	}

