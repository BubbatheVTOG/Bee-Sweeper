import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class BeeSweeper extends JFrame{

	private int gridSize = 10;
	private int beeAmount = 10;
	private int buttonSize = 50;
	private int[] beeLocations;
	private Tile[][] tiles;
	private Random rand = new Random();

	public static void main(String[] args){
		new BeeSweeper();
	}

	public BeeSweeper(){
		//TODO:
		//dialog box for difficulty.
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
				Tile tile = new Tile(i,j);
				tile.setPreferredSize(new Dimension(buttonSize,buttonSize));
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
		for( int i = 0; i<gridSize; i++){
			for( int j = 0; j<gridSize; j++){
				Tile tile = tiles[i][j];
				tile.setBeeCount(this.calcBees(i,j));
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

		//DEBUG: PRINT GRID TO CONSOLE
		this.printGrid();
	}

	public void assignBees(int b, int beeAmount){
		for(; b< beeAmount; b++){
			int beeLocation = rand.nextInt(gridSize*gridSize);
			if(!Arrays.asList(beeLocations).contains(beeLocation)){
				beeLocations[b]=beeLocation;
				tiles[((int)Math.floor(beeLocation/gridSize))][(beeLocation%gridSize)].setBee(true);
			}else{
				this.assignBees(b,beeAmount-b);
				break;
			}
			System.out.println(""+beeLocation);
		}
	}

	public void printGrid(){
		for( int i = 0; i<gridSize; i++){
			for( int j = 0; j<gridSize; j++){
				Tile tile = tiles[i][j];
				if(tile.isBee()){
					System.out.print("B ");
				}else{
					System.out.print(""+tile.getBeeCount()+" ");
				}
			}
			System.out.println();
		}
	}


	public int calcBees(int y, int x){
		int beeCount=0;
		//LEFT
		if(x > 0 ){
			if(tiles[y][x-1].isBee()){
				beeCount++;
			}
		}
		//RIGHT
		if(x < gridSize-1){
			if(tiles[y][x+1].isBee()){
				beeCount++;
			}
		}
		//UP
		if(y > 0){
			if(tiles[y-1][x].isBee()){
				beeCount++;
			}
		}
		//BOTTOM
		if(y < gridSize-1){
			if(tiles[y+1][x].isBee()){
				beeCount++;
			}
		}
		//TOP-LEFT
		if( (x > 0) && (y > 0) ){
			if(tiles[y-1][x-1].isBee()){
				beeCount++;
			}
		}
		//TOP-RIGHT
		if( (x < gridSize-1) && (y < gridSize-1) ){
			if(tiles[y+1][x+1].isBee()){
				beeCount++;
			}
		}
		//BOTTOM-LEFT
		if( (x > 0) && (y < gridSize-1) ){
			if(tiles[y+1][x-1].isBee()){
				beeCount++;
			}
		}
		//TOP-RIGHT
		if( (x < gridSize-1) && (y > 0) ){
			if(tiles[y-1][x+1].isBee()){
				beeCount++;
			}
		}
		return beeCount;
	}

	public void floodFill(Tile tile){
		int x = tile.getTileX();
		int y = tile.getTileY();
		ArrayList<Tile> floodFillTiles = new ArrayList<Tile>();
		//LEFT x-1
		while(x > 0 && !tile.isBee()){
			Tile tempTile = tiles[x][y];
			floodFillTiles.add(tempTile);
			if(0 < tempTile.getBeeCount()){
				x = 1;
			}
			x--;
		}

		//RIGHT x+1
		x = tile.getTileX();
		y = tile.getTileY();
		while(x < gridSize && !tile.isBee() && 0 == tile.getBeeCount()){
			Tile tempTile = tiles[x][y];
			floodFillTiles.add(tempTile);
			if( 0 < tempTile.getBeeCount()){
				x = gridSize-1;
			}
			x++;
		}

		//UP y-1
		x = tile.getTileX();
		y = tile.getTileY();
		while(y > 0 && !tile.isBee() && 0 == tile.getBeeCount()){
			Tile tempTile = tiles[x][y];
			floodFillTiles.add(tempTile);
			if( 0 < tempTile.getBeeCount()){
				y = 1;
			}
			y--;
		}

		//BOTTOM y+1
		x = tile.getTileX();
		y = tile.getTileY();
		while(y < gridSize && !tile.isBee() && 0 == tile.getBeeCount()){
			Tile tempTile = tiles[x][y];
			floodFillTiles.add(tempTile);
			if( 0 < tempTile.getBeeCount()){
				y = gridSize-1;
			}
			y++;
		}

		//TOP-LEFT y-1 x-1
		x = tile.getTileX();
		y = tile.getTileY();
		while(y > 0 && x > 0 && !tile.isBee() && 0 == tile.getBeeCount()){
			Tile tempTile = tiles[x][y];
			floodFillTiles.add(tempTile);
			if( 0 < tempTile.getBeeCount()){
				y = 1;
				x = 1;
			}
			y--;
			x--;
		}

		//TOP-RIGHT y+1 x+1
		x = tile.getTileX();
		y = tile.getTileY();
		while(y < gridSize && x < gridSize && !tile.isBee() && 0 == tile.getBeeCount()){
			Tile tempTile = tiles[x][y];
			floodFillTiles.add(tempTile);
			if( 0 < tempTile.getBeeCount()){
				y = gridSize-1;
				x = gridSize-1;
			}
			x++;
			y++;
		}

		//BOTTOM-LEFT y+1 x-1
		x = tile.getTileX();
		y = tile.getTileY();
		while(y < gridSize && x > 0 && !tile.isBee() && 0 == tile.getBeeCount()){
			Tile tempTile = tiles[x][y];
			floodFillTiles.add(tempTile);
			if( 0 < tempTile.getBeeCount()){
				y = gridSize-1;
				x = 1;
			}
			x--;
			y++;
		}

		//TOP-RIGHT y-1 x+1
		x = tile.getTileX();
		y = tile.getTileY();
		while(y > 0 && x < gridSize && !tile.isBee() && 0 == tile.getBeeCount()){
			Tile tempTile = tiles[x][y];
			floodFillTiles.add(tempTile);
			if( 0 < tempTile.getBeeCount()){
				y = 1;
				x = gridSize-1;
			}
			x++;
			y--;
		}

		for(Tile tempTile : floodFillTiles){
			tempTile.setText(""+tempTile.getBeeCount());
			tempTile.hasBeenPressed(true);
		}
	}

	public void gameOver(){
		for(int i =0; i< gridSize; i++){
			for(int j =0; j<gridSize; j++){
				Tile tile = tiles[i][j];
				if(tile.isBee()){
					tile.setText("BEE!");
				}else{
					tile.setText(""+tile.getBeeCount());
				}
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
			if(tile.isBee()){
				BeeSweeper.this.gameOver();
			}else{
				tile.setText(""+tile.getBeeCount());
				tile.hasBeenPressed(true);
				if(tile.getBeeCount() == 0){
					BeeSweeper.this.floodFill(tile);
				}
			}
			tile.removeActionListener(this);
		}
	}

	class Tile extends JButton{
		private boolean isBee;
		private boolean beenPressed=false;
		private int beeCount;
		private int x;
		private int y;

		public Tile(int y, int x){
			this.x = y;
			this.y = x;
			super.setBackground(Color.LIGHT_GRAY);
			super.setForeground(Color.WHITE);
			super.setFocusPainted(false);
		}

		public void setBee(boolean isBee){
			this.isBee = isBee;
		}

		public boolean isBee(){return isBee;}

		public void hasBeenPressed(boolean beenPressed){
			if(beenPressed){
				super.setBackground(Color.DARK_GRAY);
				this.beenPressed = beenPressed;
			}else{
				super.setBackground(Color.LIGHT_GRAY);
				this.beenPressed = beenPressed;
			}
		}

		public boolean isPressed(){return beenPressed;}

		public void setBeeCount(int beeCount){
			this.beeCount = beeCount;
		}

		public int getBeeCount(){return beeCount;}

		public int getTileX(){return x;}

		public int getTileY(){return y;}
	}
}
