package gui;

 
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.google.common.collect.Lists;

import engine.board.Board;
import engine.board.BoardUtils;
import engine.board.Move;
import engine.board.Tile;
import engine.pieces.Piece;
import engine.player.MoveTransition;

public class Table {
	
	private final JFrame gameFrame; 
	private final BoardPanel boardPanel; 
	private Board chessBoard; 
	
	private Tile sourceTile; 
	private Tile destinationTile; 
	private Piece humanMovedPiece; 
	private BoardDirection boardDirection; 
	
	private boolean highlightLegalMoves; 
	
	private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600,600); 
	private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400,350);
	private final static Dimension TILE_PANEL_DIMENSION = new Dimension(10,10);
	private static String defaultPieceImagesPath = "art/pieces/simple/";
	private final Color lightTileColor = Color.decode(null); 
	private final Color darkTileColor = Color.decode(null); 
	
	public Table() {
		this.gameFrame = new JFrame("JChess"); 
		this.gameFrame.setLayout(new BorderLayout());
		final JMenuBar tableMenuBar = createTableMenuBar(); 
		this.gameFrame.setJMenuBar(tableMenuBar);
		this.gameFrame.setSize(OUTER_FRAME_DIMENSION); 
		
		this.chessBoard = Board.createStandardBoard();
		this.boardPanel = new BoardPanel(); 
		this.boardDirection = BoardDirection.NORMAL; 
		this.highlightLegalMoves = false; 
		this.gameFrame.add(this.boardPanel, BorderLayout.CENTER); 
		
		this.gameFrame.setVisible(true); 
	}

	private JMenuBar createTableMenuBar() {
		final JMenuBar tableMenuBar = new JMenuBar();
		tableMenuBar.add(createFileMenu()); 
		tableMenuBar.add(createPreferencesMenu());
		return tableMenuBar; 
	}
	private JMenu createFileMenu() {
		final JMenu fileMenu = new JMenu("File"); 
		//TODO implement a way of uploading and viewing pre-played games using PGN(portable game notation) files 
		final JMenuItem openPGN = new JMenuItem("Load PGN File"); 
		openPGN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Open PGN!"); 
			}
		}); 
		fileMenu.add(openPGN); 
		
		final JMenuItem exitMenuItem = new JMenuItem("Exit"); 
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		}); 
		fileMenu.add(exitMenuItem); 
		return fileMenu; 
	}
	
	private JMenu createPreferencesMenu() {
		final JMenu preferencesMenu = new JMenu("Preferences"); 
		final JMenuItem flipBoardMenuItem = new JMenuItem("Flip Board"); 
		flipBoardMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				boardDirection = boardDirection.opposite(); 
				boardPanel.drawBoard(chessBoard);
			}
		}); 
		preferencesMenu.add(flipBoardMenuItem); 
		
		preferencesMenu.addSeparator();
        final JCheckBoxMenuItem legalMoveHighlighterCheckbox = new JCheckBoxMenuItem("Highlight Legal Moves", false); 
        legalMoveHighlighterCheckbox.addActionListener(new ActionListener (){
        	@Override
        	public void actionPerformed(ActionEvent e) {
        	highlightLegalMoves = legalMoveHighlighterCheckbox.isSelected();
        }
	 }); 
        preferencesMenu.add(legalMoveHighlighterCheckbox);
		return preferencesMenu; 
	}
	
	public enum BoardDirection {
		
		NORMAL {
			@Override
			List<TilePanel> traverse(final List<TilePanel> boardTiles){
				return boardTiles; 
			}
			@Override
			BoardDirection opposite() {
				return FLIPPED; 
			}
		},
		FLIPPED {
			@Override 
			List<TilePanel> traverse(final List<TilePanel> boardTiles){
				return Lists.reverse(boardTiles); 
			}
			@Override 
			BoardDirection opposite() {
				return NORMAL; 
			}
		};
		abstract List<TilePanel> traverse(final List<TilePanel> boardTiles); 
		abstract BoardDirection opposite();
	}
	
	// 8x8 Board panel class that adds 64 tiles to list - each tile is also added to board panel extends from Jpanel 
	private class BoardPanel extends JPanel{
		final List<TilePanel> boardTiles;
		
		BoardPanel(){
			super(new GridLayout(8,8));
			this.boardTiles = new ArrayList<>(); 
			for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
				final TilePanel tilePanel = new TilePanel(this, i); 
				this.boardTiles.add(tilePanel);
				add(tilePanel); 
			}
			setPreferredSize(BOARD_PANEL_DIMENSION); 
			validate(); 
		}
		
		public void drawBoard(final Board board) {
			removeAll(); 
			for(final TilePanel tilePanel : boardDirection.traverse(boardTiles)) {
				tilePanel.drawTile(board); 
				add(tilePanel);
			}
			validate(); 
			repaint();
		}
	}
	
	// Maps to tile in chess game - visual component 
	private class TilePanel extends JPanel {
		// tile coordinate 
		private final int tileId; 
		
		TilePanel(final BoardPanel boardPanel,
				  final int tileId){
			super(new GridBagLayout()); 
			this.tileId = tileId; 
			setPreferredSize(TILE_PANEL_DIMENSION); 
			assignTileColor();
			assignTilePieceIcon(chessBoard); 
			
			addMouseListener(new MouseListener() {
            //Adding listener for each tile that listens for click event - if discovered then get ID+Tile and assign it to source tile
				@Override
				public void mouseClicked(final MouseEvent e) {
				 if(SwingUtilities.isRightMouseButton(e)) {
					 //Undo - canceling selections 
					 sourceTile = null; 
					 destinationTile = null; 
					 humanMovedPiece = null; 	
				 } else if(SwingUtilities.isLeftMouseButton(e)) {
	
					 if(sourceTile == null) {
					    sourceTile = chessBoard.getTile(tileId);
					    humanMovedPiece = sourceTile.getPiece();
					 if(humanMovedPiece == null) {
						  sourceTile = null; 
					    } 
					 } else { 
						 // places tile on new destination 
						 destinationTile = chessBoard.getTile(tileId); 
						 final Move move = Move.MoveFactory.createMove(chessBoard, 
								           sourceTile.getTileCoordinate(), 
								           destinationTile.getTileCoordinate()); 
					     final MoveTransition transition = chessBoard.currentPlayer().makeMove(move); 
					     if(transition.getMoveStatus().isDone()) {
					    	               chessBoard = transition.getTransitionBoard(); 
					     //TODO add move to move log 
					     }
					     sourceTile = null; 
					     destinationTile = null; 
					     humanMovedPiece = null; 
					     
					 }
					 SwingUtilities.invokeLater(new Runnable() {
						@Override 
						public void run() {
							boardPanel.drawBoard(chessBoard);
						}
					 });
                  }
			
			    }

				@Override
				public void mousePressed(final MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseReleased(final MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseEntered(final MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(final MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
			}); 
			validate();
		}
		public void drawTile(final Board board) {
			assignTileColor(); 
			assignTilePieceIcon(board); 
			validate(); 
			repaint(); 
		}
		
		private void assignTilePieceIcon(final Board board) {
			this.removeAll();
			if(board.getTile(this.tileId).isTileOccupied()) {
				try {
					final BufferedImage image = 
						  ImageIO.read(new File(defaultPieceImagesPath + board.getTile(this.tileId).getPiece().getPieceAlliance().toString().substring(0, 1)+
						  board.getTile(this.tileId).getPiece().toString() + ".gif"));
					add(new JLabel(new ImageIcon(image)));
				} catch (IOException e) {
					e.printStackTrace();
				}
		     }
		  }
		private void highlightLegals(final Board board) {
			if(highlightLegalMoves) {
				for(final Move move : pieceLegalMoves(board)) {
					if(move.getDestinationCoordinate() == this.tileId) {
						try {
							add(new JLabel(new ImageIcon(ImageIO.read(new File("art/misc/green_dot.png")))));
						} catch(Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		private Collection<Move> pieceLegalMoves(final Board board){
			if(humanMovedPiece != null && humanMovedPiece.getPieceAlliance() == board.currentPlayer().getAlliance()) {
				return humanMovedPiece.calculateLegalMoves(board);
			}
			return Collections.emptyList();
		}
		//If you are on first, third, fifth or seventh row check if even = light if not even color it dark based flips for even rows
		private void assignTileColor() {
			if(BoardUtils.FOURTH_RANK[this.tileId] ||
			   BoardUtils.SIXTH_RANK[this.tileId] ||
			   BoardUtils.FOURTH_RANK[this.tileId] ||
			   BoardUtils.SECOND_RANK[this.tileId]) {
			setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor); 
		} else if(BoardUtils.SEVENTH_RANK[this.tileId] ||
			   BoardUtils.FOURTH_RANK[this.tileId] ||
			   BoardUtils.THIRD_RANK[this.tileId] ||
			   BoardUtils.FIRST_RANK[this.tileId]) {
            setBackground(this.tileId % 2 != 0 ? lightTileColor : darkTileColor); 
		}
			
     }
	
  }
	
}
	
  
	
	


