package gui;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class Table {
	
	private final JFrame gameFrame; 
	private static Dimension OUTER_FRAME_DIMENSION = new Dimension(600,600); 
	
	public Table() {
		this.gameFrame = new JFrame("JChess"); 
		final JMenuBar tableMenuBar = new JMenuBar(); 
		populateMenuBar(tableMenuBar); 
		this.gameFrame.setSize(OUTER_FRAME_DIMENSION); 
		this.gameFrame.setVisible(true); 
	}

	private void populateMenuBar(JMenuBar tableMenuBar) {
		tableMenuBar.add(createFileMenu()); 	
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
		return fileMenu; 
	}

}
