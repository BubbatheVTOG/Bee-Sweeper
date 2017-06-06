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
				Tile tile = new Tile(counter);
				tile.setPreferredSize(new Dimension(64,64));
				tiles[i][j]=tile;
				jpGrid.add(tile);
				//TODO: ASSIGN button ID's
				tile.addActionListener(new TileListener(tile));
				counter++;
			}
		}
		add(jpGrid,BorderLayout.CENTER);

		//ASSIGN BEES
		this.assignBees(0,beeAmount);

		//ASSIGN BEE COUNT
		for( int i = 0; i<gridSize-1; i++){
			for( int j = 0; j<gridSize-1; j++){
				tiles[i][j].setBeeCount(this.calcBees(i,j));
			}
		}

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
				//tiles[beeLocation%gridSize][(int)Math.floor(beeLocation/gridSize)].setBee(true);
				tiles[(int)Math.floor(beeLocation/gridSize)][beeLocation%gridSize].setBee(true);
			}else{
				this.assignBees(b,beeAmount-b);
				break;
			}
			System.out.println(""+beeLocation);
		}
	}

	public int calcBees(int x, int y){
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
		return beeCount;
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
		JOptionPane.showMessageDialog(null,"You Lose","You Lose",JOptionPane.ERROR_MESSAGE);
	}

	class TileListener implements ActionListener{
		Tile tile;

		public TileListener(Tile tile){
			this.tile = tile;
		}

		public void actionPerformed(ActionEvent ae){
			Tile tile = (Tile)ae.getSource();
			int counter = 0;
			for(int i =0; i< gridSize; i++){
				for(int j =0; j<gridSize; j++){
					if(tile.getTileId() == counter){
						System.out.println("Tile ID: "+tile.getTileId());
						System.out.println("Counter: "+counter);
						if(tile.isBee()){
							BeeSweeper.this.gameOver();
						}else{
							tile.hasBeenPressed(true);
							BeeSweeper.this.calcBees(i,j);
							if(!tile.isPressed()){
								tile.setText(""+tile.getBeeCount());
							}
						}
					}
					counter++;
				}
			}
		}
	}

	class Tile extends JButton{
		private boolean isBee;
		private boolean beenPressed=false;
		private int beeCount;
		private int id;

		public Tile(int id){
			this.id = id;
		}

		public void setBee(boolean isBee){
			this.isBee = isBee;
		}

		public boolean isBee(){return isBee;}

		public void hasBeenPressed(boolean beenPressed){
			this.beenPressed = beenPressed;
		}

		public boolean isPressed(){return beenPressed;}

		public void setTileId(int id){this.id = id;}

		public void setBeeCount(int beeCount){
			this.beeCount = beeCount;
		}

		public int getBeeCount(){return beeCount;}

		public int getTileId(){return id;}
	}
}
