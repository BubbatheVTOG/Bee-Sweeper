import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import java.util.*;

public class BeeSweeper extends JFrame{

	private int gridSize;
	private int bombAmount;
	private JButton[][] tiles;
	private Random rand = new Random();

	public static void main(String[] args){
		new BeeSweeper();
	}

	public BeeSweeper(){
		//TODO:
		//dialog box for difficulty.
		gridSize=10;
		bombAmount=10;

		//MenuBar
		JMenuBar jmb = new JMenuBar();

		JMenu jmFile = new JMenu("File");
			JMenuItem jmiNew = new JMenuItem("New");
			JMenuItem jmiQuit = new JMenuItem ("Quit");
			jmFile.add(jmiNew);
			jmFile.add(jmiQuit);
		jmb.add(jmFile);
		setJMenuBar(jmb);

		//Center
		tiles = new JButton[gridSize][gridSize];
		JPanel jpGrid = new JPanel(new GridLayout(gridSize,gridSize));
		for(int i = 0; i<gridSize; i++){
			for(int j =0; j<gridSize; j++){
				Tile tile = new Tile();
				tile.setPreferredSize(new Dimension(64,64));
				tiles[i][j]=tile;
				jpGrid.add(tile);
			}
		}
		add(jpGrid,BorderLayout.CENTER);

		//TODO: ASSIGN BEES

		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void calcBees(int x, int y){
		int beeCount=0;
		if(x > 0 ){
			if(tiles[x-1][y].isBee()){
				beeCount++;
		}
		if(x < gridSize){
			if(tiles[x+1][y].isBee()){
				beeCount++;
			}
		}
		if(y > 0){
			if(tiles[x][y-1].isBee()){
				beeCount++;
			}
		}
		if(y < gridSize){
			if(tiles[y][x+1].isBee(){
				beeCount++;
			}
		}
		if( (x > 0) && (y > 0) ){
			if(tiles[x-1][y-1].isBee()){
				beeCount++;
			}
		}
		if( (x < gridSize) && (y < gridSize) ){
			if(tiles[x+1][y+1].isBee(){
				beeCount++;
			}
		}
		if( (x > 0) && (y < gridSize) ){
			if(tiles[x-1][y+1].isBee()){
				beeCount++;
			}
		}
		if( (x < gridSize) && (y > 0) ){
			if(tiles[x+1][y-1].isBee()){
				beeCount++;
			}
		}
		tiles.[x][y].setBeeCount(beeCount);
	}

	public void floodFill(){
		//TODO:LOGIC
	}

	public void gameOver(){
		for(int i =0; i< gridSize; i++){
			for(int j =0; j<gridSize; j++){
				tile[i][j].show();
			}
		}
	}

	class Tile extends JButton implements MouseListener{
		private boolean pressed = false;
		private boolean bee;

		public Tile(){
			addMouseListener(this);
		}

		public boolean isBee(){return bee;}

		public void seBeeCount(int i){
			this.setText(i);
		}

		public void show(){
			pressed = true;
		}

		//Overrides for abstract MouseListener
		public void mouseExited(MouseEvent me){}
		public void mouseEntered(MouseEvent me){}
		public void mouseReleased(MouseEvent me){}
		public void mousePressed(MouseEvent me){}
		public void mouseClicked(MouseEvent me){}
	}
}
