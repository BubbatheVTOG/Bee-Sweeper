import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class BeeSweeper extends JFrame{

	private int gridSize;
	private int beeAmount;
	private Tile[][] tiles;
	private Random rand = new Random();

	public static void main(String[] args){
		new BeeSweeper();
	}

	public BeeSweeper(){
		//TODO:
		//dialog box for difficulty.
		gridSize=10;
		beeAmount=10;

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
		tiles = new Tile[gridSize][gridSize];
		JPanel jpGrid = new JPanel(new GridLayout(gridSize,gridSize));
		for(int i = 0; i<gridSize; i++){
			for(int j =0; j<gridSize; j++){
				Tile tile = new Tile();
				tile.setPreferredSize(new Dimension(64,64));
				tiles[i][j]=tile;
				jpGrid.add(tile);
				//TODO: ASSIGN button ID's
			}
		}
		add(jpGrid,BorderLayout.CENTER);

		//TODO: ASSIGN BEES


		//ACTION LISTENERS
		jmiNew.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				new BeeSweeper();
			}
		});

		jmiQuit.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				System.exit(0);
				}
		});

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
			if(tiles[y][x+1].isBee()){
				beeCount++;
			}
		}
		if( (x > 0) && (y > 0) ){
			if(tiles[x-1][y-1].isBee()){
				beeCount++;
			}
		}
		if( (x < gridSize) && (y < gridSize) ){
			if(tiles[x+1][y+1].isBee()){
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
		tiles[x][y].setBeeCount(beeCount);
	}

	public void floodFill(){
		//TODO:LOGIC
	}

	public void gameOver(){
		for(int i =0; i< gridSize; i++){
			for(int j =0; j<gridSize; j++){
				tiles[i][j].hasBeenPressed();
			}
		}
	}

	class Tile extends JButton implements MouseListener{
		private boolean pressed = false;
		private boolean bee = false;

		public Tile(){
			addMouseListener(this);
		}

		public boolean isBee(){return bee;}

		public void setBeeCount(int i){
			this.setText(""+i);
		}

		public void hasBeenPressed(){
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
