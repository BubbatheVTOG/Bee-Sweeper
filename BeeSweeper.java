import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class BeeSweeper extends JFrame{

	private int gridSize;
	private int beeAmount;
	private int[] beeLocations;
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
		beeLocations = new int[beeAmount];

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
		int counter = 0;
		for(int i = 0; i<gridSize; i++){
			for(int j =0; j<gridSize; j++){
				Tile tile = new Tile();
				tile.setPreferredSize(new Dimension(64,64));
				tiles[i][j]=tile;
				jpGrid.add(tile);
				//TODO: ASSIGN button ID's
				tile.addActionListener(new TileListener(tile));
				tile.putClientProperty("id",Integer.valueOf(counter));
				counter++;
			}
		}
		add(jpGrid,BorderLayout.CENTER);

		//ASSIGN BEES
		this.assignBees(0,beeAmount);

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

	public void assignBees(int b, int beeAmount){
		for(; b< beeAmount; b++){
			int beeLocation = rand.nextInt(gridSize*gridSize);
			if(!Arrays.asList(beeLocations).contains(beeLocation)){
				beeLocations[b]=beeLocation;
				tiles[beeLocation%gridSize][(int)Math.floor(beeLocation/gridSize)].setBee(true);
			}else{
				this.assignBees(b,beeAmount-b);
				break;
			}
			System.out.println(""+beeLocation);
		}
	}

	public void calcBees(int x, int y){
		int beeCount=0;
		if(x > 0 ){
			if(tiles[x-1][y].isBee()){
				beeCount++;
			}
		}
		if(x < gridSize-1){
			if(tiles[1][y].isBee()){
				beeCount++;
			}
		}
		if(y > 0){
			if(tiles[x][y-1].isBee()){
				beeCount++;
			}
		}
		if(y < gridSize-1){
			if(tiles[x][y].isBee()){
				beeCount++;
			}
		}
		if( (x > 0) && (y > 0) ){
			if(tiles[x-1][y-1].isBee()){
				beeCount++;
			}
		}
		if( (x < gridSize-1) && (y < gridSize-1) ){
			if(tiles[x][y].isBee()){
				beeCount++;
			}
		}
		if( (x > 0) && (y < gridSize-1) ){
			if(tiles[x-1][y].isBee()){
				beeCount++;
			}
		}
		if( (x < gridSize-1) && (y > 0) ){
			if(tiles[x][y-1].isBee()){
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
				tiles[i][j].hasBeenPressed(true);
			}
		}
	}

	class TileListener implements ActionListener{
		JButton tile;

		public TileListener(JButton tile){
			this.tile = tile;
		}

		public void actionPerformed(ActionEvent ae){
			Tile tile = (Tile)ae.getSource();
			int counter = 0;
			for(int i =0; i< gridSize; i++){
				for(int j =0; j<gridSize; j++){
					if(tile.getTileId() == counter){
						tile.hasBeenPressed(true);
						if(tile.isBee()==true){
							BeeSweeper.this.gameOver();
						}else{
							tile.hasBeenPressed(true);
							BeeSweeper.this.calcBees(i,j);
						}
					}
				}
			}
		}
	}

	class Tile extends JButton{
		private boolean isBee;
		private boolean beenPressed;
		private int beeCount;
		private int id;

		public void setBee(boolean isBee){
			this.isBee = isBee;
		}

		public boolean isBee(){return isBee;}

		public void hasBeenPressed(boolean beenPressed){
			this.beenPressed = beenPressed;
		}

		public void setBeeCount(int beeCount){this.beeCount = beeCount;}

		public void setTileId(int id){this.id = id;}

		public int getTileId(){return id;}
	}
}
