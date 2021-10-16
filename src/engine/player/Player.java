package engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import engine.Alliance;
import engine.board.Board;
import engine.pieces.King;
import engine.pieces.Piece;
import engine.board.Move;

public abstract class Player {
	
	protected final Board board; 
	protected final King playerKing; 
	protected final Collection<Move> legalMoves; 
	private final boolean isInCheck; 
	
	Player(final Board board, 
		   final Collection<Move> legalMoves, 
		   final Collection<Move> opponentMoves){
		this.board = board; 
		this.playerKing = establishKing(); 
		//Using Guava - concatenate to the end of the collection King castles - To do a castle you also need to know opponents prior moves(part of chess rule). 
		this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves, calculateKingCastles(legalMoves, opponentMoves)));  
		this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty(); // Pass kings positions and enemy moves - if there is an overlap (if list not empty) King is under attack.   
	}
	
	public King getPlayerKing() {
		return this.playerKing; 
	}
	
	public Collection<Move>getLegalMoves(){
		return this.legalMoves; 
	}

// Pass kings current tile location on board + enemies moves go through each one and check if any enemy move destination overlaps with King = attack on king 
	protected static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> moves){
		final List<Move> attackMoves = new ArrayList<>(); 
		for(final Move move : moves) {
			if(piecePosition == move.getDestinationCoordinate()) {
				attackMoves.add(move); 
			}
		}
		return ImmutableList.copyOf(attackMoves); 
	}

	private King establishKing() {
		for(final Piece piece : getActivePieces()) {
			if(piece.getPieceType().isKing()) {
				return (King) piece; 
			}
		}
		// if king can't be found chess board/state is wrong 
		throw new RuntimeException("NO KING - Board Not Valid"); 
		
	}
	// implementing helper routines - and polymorphic abstract methods 
	public boolean isMoveLegal(final Move move) {
		return this.legalMoves.contains(move); 
	}
	
	public boolean isInCheck() {
		return this.isInCheck; 
	}
	
	
	public boolean isInCheckMate() {
		return this.isInCheck && !hasEscapeMoves(); 
	}

	public boolean isInStaleMate() {
		return !this.isInCheck && !hasEscapeMoves(); 
	}
	// Check players legal moves and implement them in a separate "abstract" board - and see if any moves is successful return true otherwise false.   
	protected boolean hasEscapeMoves() {
		for(final Move move : this.legalMoves) {
			final MoveTransition transition = makeMove(move); 
			if(transition.getMoveStatus().isDone()) {
				return true; 
			}
		}
		return false;
	}

	
	// TODO implement logic in methods!!! 
	public boolean isCastled() {
		return false; 
	}
	
	public MoveTransition makeMove(final Move move) {
		// If move is illegal - then the move transition returns the same board as no move is made 
		if(!isMoveLegal(move)) {
			return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
		}
		// Use move to polymorphically execute the move and return a new board we transition to 
		final Board transitionBoard = move.execute(); 
		// Then we check if there are any attacks on king once a move is made 
		final Collection<Move> kingAttacks = Player.calculateAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
				transitionBoard.currentPlayer().getLegalMoves()); 
		// Return board and leave player in check 
		if(!kingAttacks.isEmpty()) {
			return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK); 
		}		
		// Otherwise return the move transition board in a new move transition 
		return new MoveTransition(transitionBoard, move, MoveStatus.DONE); 
	}
	
	public abstract Collection<Piece> getActivePieces(); 
	public abstract Alliance getAlliance(); 
	public abstract Player getOpponent(); 
	protected abstract Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentsLegals); // Player generates castling moves
	

}
