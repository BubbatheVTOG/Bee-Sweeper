import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

//FLAG UNICODE
//"\u2691"
//BEE UNICODE
//"\uD83D\uDC1D"

public class BeeSweeper extends JFrame{

	private int gridSize = 10;
	private int beeAmount = 10;
	private int buttonSize = 64;
	private int beeCounter;
	private int tileCounter;
	private static final String beeSymbol = "\uD83D\uDC1D";
	private static final String flagSymbol = "\u2713";

	private int[] beeLocations;
	private Tile[][] tiles;
	private Random rand = new Random();
	private JLabel beeCounterLabel,tileCounterLabel;

	public static void main(String[] args){
		new BeeSweeper();
	}

	public BeeSweeper(){
		//TODO:
		//dialog box for difficulty.
		beeLocations = new int[beeAmount];
		beeCounter = beeAmount;
		tileCounter = gridSize*gridSize;

		//MenuBar
		JMenuBar jmb = new JMenuBar();

		JMenu jmFile = new JMenu("File");
			JMenuItem jmiNew = new JMenuItem("New");
			JMenuItem jmiQuit = new JMenuItem ("Quit");
			jmFile.add(jmiNew);
			jmFile.add(jmiQuit);
		jmb.add(jmFile);
		setJMenuBar(jmb);

		//NORTH
		JPanel jpNorth = new JPanel( new FlowLayout());
			beeCounterLabel = new JLabel(beeSymbol+": "+String.valueOf(beeCounter));
				beeCounterLabel.setHorizontalAlignment(SwingConstants.LEFT);
			tileCounterLabel = new JLabel("\u2713"+": "+String.valueOf(tileCounter));
				tileCounterLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			jpNorth.add(beeCounterLabel);
			jpNorth.add(tileCounterLabel);
		add(jpNorth, BorderLayout.NORTH);

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
				tile.addMouseListener(new TileListener(tile));
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
				return;
			}
			//System.out.println(""+beeLocation);
		}
	}

	public void printGrid(){
		for( int i = 0; i<gridSize; i++){
			System.out.print("--");
		}
		System.out.println();
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
		for( int i = 0; i<gridSize; i++){
			System.out.print("--");
		}
		System.out.println();
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
		if(tile.isBee()){
			return;
		}
		if(tile.isPressed()){
			return;
		}
		tile.hasBeenPressed(true);
		if((x > 0) && (x < gridSize-1) && (y > 0) && (y < gridSize-1)){
			tile.hasBeenPressed(true);
			this.floodFill( tiles[x+1][y] );
			this.floodFill( tiles[x-1][y] );
			this.floodFill( tiles[x][y-1] );
			this.floodFill( tiles[x][y+1] );
		}
		if((x >= 0) && (x < gridSize-1) && (y > 0) && (y < gridSize-1)){
			tile.hasBeenPressed(true);
			this.floodFill( tiles[x+1][y] );
			this.floodFill( tiles[x][y-1] );
			this.floodFill( tiles[x][y+1] );
		}
		if((x > 0) && (x <= gridSize-1) && (y > 0) && (y < gridSize-1)){
			tile.hasBeenPressed(true);
			this.floodFill( tiles[x-1][y] );
			this.floodFill( tiles[x][y-1] );
			this.floodFill( tiles[x][y+1] );
		}
		if((x > 0) && (x < gridSize-1) && (y >= 0) && (y < gridSize-1)){
			tile.hasBeenPressed(true);
			this.floodFill( tiles[x+1][y] );
			this.floodFill( tiles[x-1][y] );
			this.floodFill( tiles[x][y+1] );
		}
		if((x > 0) && (x < gridSize-1) && (y > 0) && (y <= gridSize-1)){
			tile.hasBeenPressed(true);
			this.floodFill( tiles[x+1][y] );
			this.floodFill( tiles[x-1][y] );
			this.floodFill( tiles[x][y-1] );
		}
	}

	public void gameOver(){
		for(int i =0; i< gridSize; i++){
			for(int j =0; j<gridSize; j++){
				Tile tile = tiles[i][j];
				tile.setBackground(Color.DARK_GRAY);
				if(tile.isBee()){
					tile.setText(beeSymbol);
				}else{
					tile.setText(String.valueOf(tile.getBeeCount()));
				}
			}
		}
		JOptionPane.showMessageDialog(null,"You Lose","You Lose",JOptionPane.ERROR_MESSAGE);
		BeeSweeper.this.dispose();
		System.gc();
		new BeeSweeper();
	}

	public void winGame(){
		boolean won = false;
		for(int i =0; i< gridSize; i++){
			for(int j =0; j<gridSize; j++){
				Tile tile = tiles[i][j];
				if(!tile.isPressed() && !tile.isBee()){
					return;
				}else{
					won = true;
				}
			}
		}
		if(won){
			JOptionPane.showMessageDialog(null,"You WIN!","You WIN!",JOptionPane.ERROR_MESSAGE);
			BeeSweeper.this.dispose();
			System.gc();
			new BeeSweeper();
		}
	}

	public void decTileCounter(){
		this.tileCounter--;
		tileCounterLabel.setText(String.valueOf(tileCounter));
	}

	public void decBeeCounter(){
		this.beeCounter--;
		beeCounterLabel.setText(String.valueOf(beeCounter));
	}

	public void incBeeCounter(){
		this.beeCounter++;
		beeCounterLabel.setText(String.valueOf(beeCounter));
	}

	class TileListener implements MouseListener{
		Tile tile;

		public TileListener(Tile tile){
			this.tile = tile;
		}

		public void mouseClicked(MouseEvent me){
			Tile tile = (Tile)me.getSource();
			//System.out.println(me.toString());
			if(MouseEvent.BUTTON1 == me.getButton()){
				if(tile.isBee()){
					tile.hasBeenPressed(true);
					BeeSweeper.this.gameOver();
				}else{
					tile.setText(""+tile.getBeeCount());
					if(tile.getBeeCount() == 0){
						BeeSweeper.this.floodFill(tile);
					}
					tile.hasBeenPressed(true);
					BeeSweeper.this.winGame();
				}
			}
			if(MouseEvent.BUTTON3 == me.getButton()){
				tile.flippedFlagged();
			}
		}

		public void mouseExited(MouseEvent me){}
		public void mouseEntered(MouseEvent me){}
		public void mousePressed(MouseEvent me){}
		public void mouseReleased(MouseEvent me){}
	}

	class Tile extends JButton{
		private boolean isBee;
		private boolean isFlagged = false;
		private boolean beenPressed;
		private int beeCount;
		private int x;
		private int y;

		public Tile(int x, int y){
			this.y = y;
			this.x = x;
			this.beenPressed = false;
			super.setBackground(Color.GRAY);
			super.setForeground(Color.WHITE);
			super.setFocusPainted(false);
		}

		public void setBee(boolean isBee){
			this.isBee = isBee;
		}

		public boolean isBee(){return isBee;}

		public void hasBeenPressed(boolean beenPressed){
			if(beenPressed){
				this.beenPressed = beenPressed;
				super.setBackground(Color.DARK_GRAY);
				if(this.isBee()){
					super.setText(beeSymbol);
				}else{
					super.setText(String.valueOf(this.getBeeCount()));
				}
			}else{
				super.setBackground(Color.GRAY);
				super.setText("");
				this.beenPressed = beenPressed;
			}
		}

		public boolean isPressed(){return beenPressed;}

		public void flippedFlagged(){
			if(!this.isFlagged && !this.beenPressed){
				isFlagged = true;
				super.setText(flagSymbol);
				BeeSweeper.this.decBeeCounter();
			}else{
				if(!this.beenPressed){
					isFlagged = false;
					super.setText("");
					BeeSweeper.this.incBeeCounter();
				}else{
					return;
				}
			}
		}

		public void setBeeCount(int beeCount){
			this.beeCount = beeCount;
		}

		public int getBeeCount(){return beeCount;}

		public int getTileX(){return x;}

		public int getTileY(){return y;}
	}
}
