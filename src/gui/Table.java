package gui;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;

import engine.board.BoardUtils;

public class Table {
	
	private final JFrame gameFrame; 
	private final BoardPanel boardPanel; 
	
	private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600,600); 
	private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400,350);
	private final static Dimension TILE_PANEL_DIMENSION = new Dimension(10,10);
	
	private final Color lightTileColor = Color.decode(null); 
	private final Color darkTileColor = Color.decode(null); 
	
	public Table() {
		this.gameFrame = new JFrame("JChess"); 
		this.gameFrame.setLayout(new BorderLayout());
		final JMenuBar tableMenuBar = createTableMenuBar(); 
		this.gameFrame.setJMenuBar(tableMenuBar);
		this.gameFrame.setSize(OUTER_FRAME_DIMENSION); 
		
		this.boardPanel = new BoardPanel(); 
		this.gameFrame.add(this.boardPanel, BorderLayout.CENTER); 
		
		this.gameFrame.setVisible(true); 
	}

	private JMenuBar createTableMenuBar() {
		final JMenuBar tableMenuBar = new JMenuBar();
		tableMenuBar.add(createFileMenu()); 	
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
	// 8x8 Board panel class that adds 64 tiles to list - each til is also added to board panel extends from Jpanel 
	private class BoardPanel extends JPanel{
		final List<TilePanel> boardTiles;
		
		BoardPanel(){
			super(new GridLayout(0,0));
			this.boardTiles = new ArrayList<>(); 
			for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
				final TilePanel tilePanel = new TilePanel(this, i); 
				this.boardTiles.add(tilePanel);
				add(tilePanel); 
			}
			setPreferredSize(BOARD_PANEL_DIMENSION); 
			validate(); 
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
			validate();
		}
		//If you are on first, third, fifth or seventh row check if even = light if not even color it dark based flips for even rows
		private void assignTileColor() {
			if(BoardUtils.FIFTH_ROW[this.tileId] ||
			   BoardUtils.THIRD_ROW[this.tileId] ||
			   BoardUtils.FIFTH_ROW[this.tileId] ||
			   BoardUtils.SEVENTH_ROW[this.tileId]) {
			setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor); 
		} else if(BoardUtils.SECOND_ROW[this.tileId] ||
			   BoardUtils.FIFTH_ROW[this.tileId] ||
			   BoardUtils.SIXTH_ROW[this.tileId] ||
			   BoardUtils.EIGTH_ROW[this.tileId]) {
            setBackground(this.tileId % 2 != 0 ? lightTileColor : darkTileColor); 
		}
     }
	
  }
	
}
	
  
	
	


