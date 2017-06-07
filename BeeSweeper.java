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
				Tile tile = new Tile(i,j);
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
		//LEFT x-1
		if(x > 0 ){

		}
		//RIGHT x+1
		if(x < gridSize-1){
		}
		//UP y-1
		if(y > 0){
		}
		//BOTTOM y+1
		if(y < gridSize-1){
		}
		//TOP-LEFT y-1 x-1
		if( (x > 0) && (y > 0) ){
		}
		//TOP-RIGHT y+1 x+1
		if( (x < gridSize-1) && (y < gridSize-1) ){
		}
		//BOTTOM-LEFT y+1 x-1
		if( (x > 0) && (y < gridSize-1) ){
		}
		//TOP-RIGHT y-1 x+1
		if( (x < gridSize-1) && (y > 0) ){
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
				tile.hasBeenPressed(true);
				tile.setText(""+tile.getBeeCount());
				if(tile.getBeeCount() == 0)i{
					BeeSweeper.this.floodFill(tile);
				}
			}
		}
	}

	class Tile extends JButton{
		private boolean isBee;
		private boolean beenPressed=false;
		private int beeCount;
		private int x;
		private int y;

		public Tile(int y, int x){
			this.y = y;
			this.x = x;
		}

		public void setBee(boolean isBee){
			this.isBee = isBee;
		}

		public boolean isBee(){return isBee;}

		public void hasBeenPressed(boolean beenPressed){
			this.beenPressed = beenPressed;
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
