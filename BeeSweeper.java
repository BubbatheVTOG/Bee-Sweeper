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

		public void hasBeenPressed(boolean pressed){
			this.pressed = pressed;
		}

		public void setBee(boolean isBee){
			this.bee = isBee;
		}

		public void mouseClicked(MouseEvent me){
			Object button = me.getSource();
			for(int i =0; i< gridSize; i++){
				for(int j =0; j<gridSize; j++){
					if(button == this){
						this.hasBeenPressed(true);
						if(this.isBee()==true){
							BeeSweeper.this.gameOver();
						}else{
							this.hasBeenPressed(true);
							BeeSweeper.this.calcBees(i,j);
						}
					}
				}
			}
		}

		//Overrides for abstract MouseListener
		public void mouseExited(MouseEvent me){}
		public void mouseEntered(MouseEvent me){}
		public void mouseReleased(MouseEvent me){}
		public void mousePressed(MouseEvent me){}
	}
}
